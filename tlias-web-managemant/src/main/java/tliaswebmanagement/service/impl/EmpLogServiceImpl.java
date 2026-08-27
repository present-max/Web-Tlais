package tliaswebmanagement.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tliaswebmanagement.pojo.EmpLog;
import tliaswebmanagement.mapper.EmpLogMapper;
import tliaswebmanagement.service.EmpLogService;

@Transactional(propagation = Propagation.REQUIRES_NEW)//当调用insertLog方法时，会开启新的事务
@Service
@RequiredArgsConstructor//自动生成一个构造方法，该构造方法包含所有final属性和@NonNull属性
public class EmpLogServiceImpl implements EmpLogService {
    //依赖注入：方法一
    //@Autowired
    //private EmpLogMapper empLogMapper;
    //方法二:加RequiredArgsConstructor注解：注：该方法只能在类中只有一个构造方法是才可以生效，因为自动生成的构造方法中的参数没加@Autowired,
    //只有一个构造方法时，spring会自动将需要的bean注入到对应的参数中，若出现多个构造方法，spring就不知道要往哪一个构造方法中注入bean
    private final EmpLogMapper empLogMapper;
    
    @Override
    public void insertLog(EmpLog empLog) {
        empLogMapper.insert(empLog);
    }
}
