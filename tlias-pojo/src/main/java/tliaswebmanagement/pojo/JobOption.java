package tliaswebmanagement.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor//创建无参构造方法
@AllArgsConstructor//创建有参构造方法
public class JobOption {

    private List jobList;   //职位列表
    private List dataList;  //数据列表
}
