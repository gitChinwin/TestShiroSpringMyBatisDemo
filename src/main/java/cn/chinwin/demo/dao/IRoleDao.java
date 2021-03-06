package cn.chinwin.demo.dao;

import cn.chinwin.demo.pojo.Role;

import java.util.List;

/**
 * Created by Chinwin on 2017/8/13.
 */
public interface IRoleDao {

    Role findRoleById(Integer roleid);

    List<Role> findRoleByDeptno(int userid,int deptno);

    List<Role> roleSplit(int start ,int ps);

    int getCount();

    int updateRole(Role role);

    int delPriList(Integer roleid);

    int addPrivilege(Integer priid,Integer roleid);
}
