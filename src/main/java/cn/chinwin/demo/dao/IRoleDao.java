package cn.chinwin.demo.dao;

import cn.chinwin.demo.pojo.Role;

import java.util.List;

/**
 * Created by Chinwin on 2017/8/13.
 */
public interface IRoleDao {

    List<Role> findRoleByDeptno(int userid,int deptno);


}
