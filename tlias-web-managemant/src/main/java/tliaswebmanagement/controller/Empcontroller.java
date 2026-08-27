package tliaswebmanagement.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import tliaswebmanagement.pojo.Emp;
import tliaswebmanagement.pojo.PageResult;
import tliaswebmanagement.pojo.Result;
import tliaswebmanagement.service.EmpService;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/emps")
@RestController//里面已经包含了@Controller
@Slf4j
public class Empcontroller {

    @Autowired
    private EmpService empService;
    @GetMapping                   //若前端没有给参数值，则给一个默认值
    //
    public Result page(@RequestParam( defaultValue = "1") Integer page,
                       @RequestParam( defaultValue = "10")Integer pageSize,
                       String name, Integer gender,          //没有使用RequestParam注解，所以前端不传参，不会报错，参数值设为null
                       @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate begin,
                       @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate  end){
        //由于参数过多，所以可将这些参数封装到一个EmpQueryParam对象中,再将实例对象作为page的参数，前端传参时，自动将参数封装到这个对象中
        log.info("分页查询");
        PageResult<Emp> pageResult = empService.page(page, pageSize,name, gender, begin, end);
        return Result.success(pageResult);
    }
    @PostMapping
    public  Result save(@RequestBody Emp emp){
        log.info("保存员工");
        empService.save(emp);
        return Result.success();
    }
    @DeleteMapping
    public Result delete(@RequestParam List<Integer> ids){
        log.info("批量删除员工");
        empService.delete(ids);
        return Result.success();
    }
    @GetMapping ("/{id}")
    public Result GetINfo(@PathVariable Integer id){
        log.info("查询员工信息");
        Emp emp = empService.getInfo(id);

        return Result.success(emp);
    }
    @PutMapping
    public Result update(@RequestBody Emp emp){
        log.info("更新员工信息");
        empService.update(emp);
        return Result.success();
    }
}
