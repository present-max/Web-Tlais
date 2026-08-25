package tliaswebmanagement.service;


import tliaswebmanagement.pojo.Dept;

import java.util.List;

public interface Deptservice {
    List<Dept> findAll();

    void deleteById(Integer id);

    void add(Dept dept);

    Dept getById (Integer id);

    void update(Dept dept);
}
