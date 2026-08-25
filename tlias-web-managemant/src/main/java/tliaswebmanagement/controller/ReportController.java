package tliaswebmanagement.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tliaswebmanagement.pojo.JobOption;
import tliaswebmanagement.pojo.Result;
import tliaswebmanagement.service.ReportService;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/empJobData")
    public Result getEmpJobData(){
        log.info("员工职位分布数据");
        JobOption jobOption =reportService.getEmpJobData();
        return  Result.success(jobOption);
    }
    @GetMapping("/empGenderData")
    public Result getEmpGenderData(){
         log.info("员工性别分布数据");
         List<Map<String,Object>> genderlist=reportService.getEmpGenderData();
        return  Result.success(genderlist);
    }
}
