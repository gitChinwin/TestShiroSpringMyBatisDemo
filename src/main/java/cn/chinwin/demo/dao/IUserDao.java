package cn.chinwin.demo.dao;

import cn.chinwin.demo.pojo.Users;

import java.util.List;

/**
 * Created by chinwin on 2017/8/9.
 */
public interface IUserDao {

    Users isLogin(Users user);

    List<Users> getUserSplit(int start, int ps, int userid, int deptno);

    int getCount(int userid, int deptno);

    boolean changeRoleFormUsers(Integer userid,Integer roleid);

}
