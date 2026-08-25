package tliaswebmanagement.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tliaswebmanagement.pojo.JobOption;
import tliaswebmanagement.mapper.EmpMapper;

import tliaswebmanagement.service.ReportService;

import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private EmpMapper empMapper;

    @Override
    public JobOption getEmpJobData() {
        List<Map<String ,Object>>list =empMapper.countEmpJobData();
         List jobList =list.stream().map(dataMap->dataMap.get("pos")).toList();      //Map元素中的每个键值对中的键对应数据库查询返回的列值，值对应数据库查询返回的行值
         List dataList =list.stream().map(dataMap->dataMap.get("num")).toList();     //即list[1]={pos="班主任",num=10}
        return  new JobOption(jobList,dataList);
    }

    @Override
    public List<Map<String, Object>> getEmpGenderData() {
        return empMapper.countEmpGenderData();
    }
}
