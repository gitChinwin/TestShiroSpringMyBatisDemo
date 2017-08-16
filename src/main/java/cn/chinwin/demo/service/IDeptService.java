package cn.chinwin.demo.service;

import cn.chinwin.demo.pojo.Dept;

import java.util.List;

/**
 * Created by chinwin on 2017/8/16.
 */
public interface IDeptService {

    List<Dept> getDeptSplit(Integer start ,Integer ps);

    List<Dept> getAllDept();

    boolean updateDept(Dept dept);

    boolean addDept(Dept dept);

    int getCount();

}
