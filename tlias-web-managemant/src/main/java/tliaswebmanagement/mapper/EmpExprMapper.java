package tliaswebmanagement.mapper;

import org.apache.ibatis.annotations.Mapper;
import tliaswebmanagement.pojo.EmpExpr;

import java.util.List;

@Mapper
public interface EmpExprMapper {
    void insertBatch(List<EmpExpr> exprList);

    void deleteByEmpIds(List<Integer> ids);
}
