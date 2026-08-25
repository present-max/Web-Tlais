package tliaswebmanagement.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tliaswebmanagement.pojo.*;
import tliaswebmanagement.mapper.EmpExprMapper;
import tliaswebmanagement.mapper.EmpMapper;
import tliaswebmanagement.service.EmpLogService;
import tliaswebmanagement.service.EmpService;
import tliaswebmanagement.util.JwtUtil;

import java.time.LocalDateTime;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    private EmpMapper EmpMapper;
    @Autowired
    private EmpExprMapper EmpExprMapper;
    @Autowired
    private EmpLogService EmpLogService;

    @Override
    public PageResult<Emp> page(Integer page, Integer pageSize, String name, Integer gender, LocalDate begin, LocalDate end) {
//       ------------------------------- 普通的分页查询-------------------------------
//        //记录总数
//        long total=EmpMapper.count();
//        //计算开始索引
//        Integer start=(page-1)*pageSize;
//        List<Emp> rows=EmpMapper.list(start,pageSize);
//        //封装PageResult对象并返回
//        PageResult<Emp> pageResult=new PageResult<>();
//        pageResult.setTotal(total);
//        pageResult.setRows(rows);
//
//        return pageResult;
        //------------------------------- 优化的分页查询------------------------------
        PageHelper.startPage(page,pageSize);//只有紧跟着的查询才会被分页
        //查询当前页数据，Page<T>继承了List<T>
        List<Emp> rows=EmpMapper.list(name,gender,begin,end);
        //Page对象可获取当页的各项属性
        Page<Emp> p=(Page<Emp>)rows;
        return new PageResult<>(p.getTotal(),p.getResult());
     }

     @Transactional//添加事务管理-默认只回滚运行时异常，不回滚编译时异常，若需要回滚编译时异常，则需要添加@Transactional(rollbackFor = Exception.class)
    @Override
    public void save(Emp emp) {
         try {
             //保存员工的基本信息
             emp.setCreateTime(LocalDateTime.now());
             ;
             emp.setUpdateTime(LocalDateTime.now());
             EmpMapper.insert(emp);
             //保存员工的工作经历
             List<EmpExpr> exprList = emp.getEmprList();
             if (!CollectionUtils.isEmpty(exprList)) {
                 //遍历集合，设置员工id
                 for (EmpExpr expr : exprList) {
                     expr.setEmpId(emp.getId());
                 }
                 EmpExprMapper.insertBatch(exprList);
             }
         } finally {//无论添加员工成功与否，都添加员工日志为防止该方法回滚，需单独给insertLog方法添加事务
                    EmpLog empLog = new EmpLog(null, LocalDateTime.now(), "添加员工:"+emp);
                    EmpLogService.insertLog(empLog);
         }
     }

     @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<Integer> ids) {
        //删除员工的基本信息
        EmpMapper.deleteByIds(ids);
        //删除员工对应的工作经历
        EmpExprMapper.deleteByEmpIds(ids);
    }

    @Override
    public Emp getInfo(Integer id) {
        return EmpMapper.getById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(Emp emp) {
        //更新员工基本信息
        emp.setUpdateTime(LocalDateTime.now());
        EmpMapper.updateById(emp);

        //更新员工对应的工作经历:先删除，再添加
        EmpExprMapper.deleteByEmpIds(Arrays.asList(emp.getId()));//将单个的id封装成List
        List<EmpExpr> exprList = emp.getEmprList();//获取员工老的（或者更改后的）和新增的工作经历
        if(!CollectionUtils.isEmpty(exprList)) {
            //遍历集合，设置员工id
            for (EmpExpr expr : exprList) {
                expr.setEmpId(emp.getId());
            }
            EmpExprMapper.insertBatch(exprList);
        }
    }

    @Override
    public LoginInfo login(Emp emp) {
        Emp e=EmpMapper.selectByUsernameAndPassword(emp);
        if(e!=null){
            log.info("员工登录成功");
            //生成Jwt令牌
            Map<String ,Object>claims=new HashMap();
            claims.put("id",e.getId());
            claims.put("username",e.getUsername());
            String token=JwtUtil.generateToken(claims);
            log.info("生成的令牌为:"+token);
            return  new LoginInfo(e.getId(),e.getUsername(),e.getName(),token);
        }
        log.info("员工登录失败");
        return null;
    }
}
