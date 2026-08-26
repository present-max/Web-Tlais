package tliaswebmanagement.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tliaswebmanagement.pojo.Dept;
import tliaswebmanagement.pojo.Result;
import tliaswebmanagement.anno.Log;

import tliaswebmanagement.service.Deptservice;

import java.util.List;

//@RequestMapping("/dept"),可将公共路径设置在类上，下面的请求方法就不用再写该公共路径了
@RestController    //相当于@Controller（提供ioc容器）+@ResponseBody（将方法的返回值直接作为响应数据返回给前端，
                      // 如果返回值是一个对象，则将对象封装成一个json数据，再返回给前端）
public class Deptcontroller {

    //定义日志记录器
    private static final Logger log = LoggerFactory.getLogger(Deptcontroller.class);//若引入了lombok依赖,则只需要在类的上面加@Slf4j
    @Autowired
    private Deptservice deptService;

    //@RequestMapping(value="/depts",method= RequestMethod.GET)//设置请求方式
    @GetMapping("/depts")
    public Result list() {
        log.info("查询所有部门信息");
           List<Dept> deptlist=deptService.findAll();
        return Result.success(deptlist);
    }

//    方法1：使用HttpServletRequest获得请求参数
//    @DeleteMapping("/depts")
//    public Result delete(HttpServletRequest request) {
//        String idStr = request.getParameter("id");
//        int id = Integer.parseInt(idStr);
//        System.out.println("删除部门信息:"+id);
//        return Result.success();
//    }
    //方法2：使用@RequestParam获得请求参数，作为方法形参，注：要用@RequestParam注解，默认前端一定要传参，若该参数可不传，
    // 则要将required设为false,当前端传的参数名和形参名一致时，可以省略@RequestParam注解
    @Log//添加该注解，则该方法会记录日志
    @DeleteMapping("/depts")//普通请求参数的写法，http://localhost:8080/depts?id=1
    public Result delete(@RequestParam("id") Integer id) {//这个注解中的参数名是与url请求中的参数名一致，若url请求中的参数名与形参名一致，则可以省略该参数
        log.info("删除部门信息:"+id);
        deptService.deleteById(id);
        return Result.success();
    }
    @Log
    @PostMapping("/depts")//json格式的请求参数的写法为请求体{"id":1,"name":"开发部"}
    public Result add(@RequestBody Dept dept) {  //dept对象会自动封装json请求体,但dept对象必须有属性名与请求体中的参数名一致，
         log.info("添加部门信息:"+dept) ;          //没传递的参数会为null
        deptService.add(dept);
        return Result.success();
    }
    @GetMapping("/depts/{id}")//{}里面的参数名与路径参数一致，路径参数：http://localhost:8080/depts/1里面的1
    public Result get(@PathVariable Integer id) {
        log.info("查询部门信息:"+id);
        return Result.success(deptService.getById(id));
    }
    @Log
    @PutMapping("/depts")
    public Result update(@RequestBody Dept dept) {
        log.info("修改部门信息:"+dept);
        deptService.update(dept);
        return Result.success();
    }
}
