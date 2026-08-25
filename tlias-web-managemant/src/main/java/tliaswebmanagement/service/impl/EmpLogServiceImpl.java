package tliaswebmanagement.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tliaswebmanagement.pojo.EmpLog;
import tliaswebmanagement.mapper.EmpLogMapper;
import tliaswebmanagement.service.EmpLogService;

@Transactional(propagation = Propagation.REQUIRES_NEW)//当调用insertLog方法时，会开启新的事务
@Service
public class EmpLogServiceImpl implements EmpLogService {

    @Autowired
    private EmpLogMapper empLogMapper;
    
    @Override
    public void insertLog(EmpLog empLog) {
        empLogMapper.insert(empLog);
    }
}
