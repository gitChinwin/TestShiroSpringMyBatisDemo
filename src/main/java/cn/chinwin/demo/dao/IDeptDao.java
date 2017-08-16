package cn.chinwin.demo.dao;

import cn.chinwin.demo.pojo.Dept;
import org.omg.CORBA.INTERNAL;

import java.util.List;

/**
 * Created by chinwin on 2017/8/16.
 */
public interface IDeptDao {

    List<Dept> getDeptSplit(Integer start,Integer ps);

    List<Dept> findAllDept();

    int updateDept(Dept dept);

    Dept findDeptById(Integer deptno);

    int addDept(Dept dept);

    int getCount();

}
