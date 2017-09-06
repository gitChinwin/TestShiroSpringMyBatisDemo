package cn.chinwin.demo.dao.impl;

import cn.chinwin.demo.dao.IUserDao;
import cn.chinwin.demo.pojo.Users;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class UserDaoImpl implements IUserDao {

    @Resource(name = "sqlSessionTemplate")
    private SqlSessionTemplate session;

    @Override
    public Users isLogin(Users user) {
        return session.selectOne("cn.chinwin.demo.pojo.Users.isLogin", user);
    }

    @Override
    public List<Users> getUserSplit(int start, int ps, int userid, int deptno) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userid", userid);
        map.put("start", start);
        map.put("deptno", deptno);
        map.put("ps", ps);
        return session.selectList("cn.chinwin.demo.pojo.Users.getUserSplit", map);
    }

    @Override
    public int getCount(int userid, int deptno) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userid", userid);
        map.put("deptno", deptno);
        return session.selectOne("cn.chinwin.demo.pojo.Users.getCount", map);
    }

    @Override
    public boolean updataUser(Users user) {
        int update = session.update("cn.chinwin.demo.pojo.Users.changeRole", user);
        return update > 0 ;
    }

    /*****************    测试用       ****************/
    @Override
    public Users get(Integer userId) {
        return session.selectOne("cn.chinwin.demo.pojo.Users.getUserById", userId);
    }

    @Override
    public Users getWithoutLock(Integer userId) {
        return session.selectOne("cn.chinwin.demo.pojo.Users.getUserByIdWithoutLock", userId);
    }

    @Override
    public boolean updateLoginName(Map map) {
        int update = session.update("cn.chinwin.demo.pojo.Users.updateLoginName", map);
        return update > 0 ;
    }
    /*****************    测试用       ****************/

}
