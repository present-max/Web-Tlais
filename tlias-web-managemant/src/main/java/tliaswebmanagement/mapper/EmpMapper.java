package tliaswebmanagement.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import tliaswebmanagement.pojo.Emp;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Mapper
public interface EmpMapper {
//    ------------------------------普通的分页查询方法---------------------------------------
//    @Select("select count(*) from emp e left join dept d on e.dept_id=d.id")
//    public long count();
//
//    @Select("select e.*,d.name as deptName from emp e left join dept d on e.dept_id=d.id order by e.update_time desc limit #{start},#{pageSize}")
//    public List<Emp> list(Integer start,Integer pageSize);

//------------------------------使用MyBatis(要引入依赖)中PageHelper插件的分页查询方法---------------------------------------
    public List<Emp> list(String name, Integer gender, LocalDate begin, LocalDate end);

    @Options(useGeneratedKeys = true,keyProperty = "id")//告诉MyBatis，插入成功后，将主键值回填到emp对象的id属性值中
    @Insert("insert into emp(username,name,gender,phone,job,salary,image,entry_date,dept_id,create_time,update_time) values(#{username},#{name},#{gender},#{phone},#{job},#{salary},#{image},#{entryDate},#{deptId},#{createTime},#{updateTime})")
    void insert(Emp emp);

    void deleteByIds(List<Integer> ids);

    Emp getById(Integer id);


    void updateById(Emp emp);

    List<Map<String,Object>> countEmpJobData();

    List<Map<String, Object>> countEmpGenderData();
    @Select("select * from emp where username=#{username} and password=#{password}")
    Emp selectByUsernameAndPassword(Emp emp);
}
