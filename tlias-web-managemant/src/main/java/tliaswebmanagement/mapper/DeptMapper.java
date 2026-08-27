package tliaswebmanagement.mapper;

import org.apache.ibatis.annotations.*;
import tliaswebmanagement.pojo.Dept;


import java.util.List;

@Mapper//告诉MyBatis这是一个需要代理的类，生成DeptMapper的代理对象，并交给Spring做ioc容器管理，到时候注入的是代理对象
       //这个注解不需要被spring扫描到，应为这里的代理对象是mybatis生成的
public interface DeptMapper {

    //当数据库字段名与实体类属性名不一致时，可使用@Results注解映射或在sql语句中指定字段对应的属性别名或在配置文件中开启驼峰命名映射
    //因为sql语句的返回结果要封装到Dept对象中，所以字段名与实体类属性名不一致的话就会出错
    @Results({
            @Result(column = "create_time",property = "createTime"),
            @Result(column = "update_time",property = "updateTime")
    })
    @Select("select id ,name,create_time,update_time from dept order by update_time desc ")
    List<Dept> findAll();

    @Delete("delete from dept where id=#{id}")
    void deleteById(Integer id);

    @Insert("insert into dept(name,create_time,update_time) values(#{name},#{createTime},#{updateTime})")
    void add(Dept dept);//#{}就是占位符，表示这里要传入一个参数，参数名与Dept对象的属性名一致，从中取值


    @Select("select id ,name,create_time as createTime,update_time as updateTime from dept where id=#{id}")
    Dept getById(Integer id);

    @Update("update dept set name=#{name},update_time=#{updateTime} where id=#{id}")
    void update(Dept dept);
}
