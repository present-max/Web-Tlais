package tliaswebmanagement.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tliaswebmanagement.pojo.Dept;
import tliaswebmanagement.mapper.DeptMapper;
import tliaswebmanagement.service.Deptservice;

import java.time.LocalDateTime;
import java.util.List;

@Service//创建ioc容器，将该类的bean 对象放入容器中，默认bean对象的作用域是singleton，即单例模式(所有请求即所有线程）中只有一个该类的实例对象)
        //若需要将该bean对象作用域设置为prototype，即多例模式（ioc容器中有多个实例对象，每次要获取对象时就注入），则将该类声明为bean对象，并添加@Scope("prototype")注解
        //若为单例模式，则在程序启动时，就会创建该类的对象，并放入ioc容器中，当程序结束，该对象销毁，若想延迟创建对象，则将该类声明为bean对象，并添加@Lazy注解
        //这样就只会在第一次用到时创建对象
public class DeptServiceImpl implements Deptservice {

    @Autowired
    private DeptMapper deptMapper;

    @Override
    public List<Dept> findAll() {
        return deptMapper.findAll();
    }
    @Override
    public void deleteById(Integer id) {deptMapper.deleteById(id);}
    @Override
    public void add( Dept dept) {
        dept.setCreateTime( LocalDateTime.now());
        dept.setUpdateTime( LocalDateTime.now());
        deptMapper.add(dept);
    }
    @Override
    public Dept getById(Integer id) {return deptMapper.getById(id);}
    @Override
    public void update(Dept dept) {
        dept.setUpdateTime( LocalDateTime.now());
        deptMapper.update(dept);
    }
}
