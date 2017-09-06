package cn.chinwin.demo.dao;

import cn.chinwin.demo.pojo.Users;

import java.util.List;
import java.util.Map;

/**
 * Created by chinwin on 2017/8/9.
 */
public interface IUserDao {

    Users isLogin(Users user);

    List<Users> getUserSplit(int start, int ps, int userid, int deptno);

    int getCount(int userid, int deptno);

    boolean updataUser(Users user);


/*****************    测试用       ****************/
    Users get(Integer userId);
    Users getWithoutLock(Integer userId);
    boolean updateLoginName(Map map);
/*****************    测试用       ****************/
}
