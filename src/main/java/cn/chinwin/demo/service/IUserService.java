package cn.chinwin.demo.service;


import cn.chinwin.demo.pojo.Result;
import cn.chinwin.demo.pojo.Users;

import java.util.List;

public interface IUserService {

    Users islogin(Users user);

    List<String> getPermissionsByUserName(String loginname);

    List<Users> getUserSplit(int cp, int ps, int userid, int deptno);

    int getCount(int userid, int deptno);

    Result getRolesByDept(Users user);

    Result changeRole(Users user,Users changUser);
}
