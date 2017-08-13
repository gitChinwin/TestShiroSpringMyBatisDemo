package cn.chinwin.demo.dao.impl;

import cn.chinwin.demo.dao.IUserDao;
import cn.chinwin.demo.pojo.Users;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;


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
    public boolean changeRoleFormUsers(Integer userid, Integer roleid) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userid", userid);
        map.put("roleid", roleid);
        int update = session.update("cn.chinwin.demo.pojo.Users.changeRole", map);
        return update > 0 ;
    }
}
