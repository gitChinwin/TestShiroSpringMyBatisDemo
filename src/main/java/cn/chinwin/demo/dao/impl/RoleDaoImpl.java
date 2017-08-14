package cn.chinwin.demo.dao.impl;

import cn.chinwin.demo.dao.IRoleDao;
import cn.chinwin.demo.pojo.Role;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Repository
public class RoleDaoImpl implements IRoleDao {

    @Resource(name = "sqlSessionTemplate")
    private SqlSessionTemplate session;

    @Override
    public List<Role> findRoleByDeptno(int userid, int deptno) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userid", userid);
        map.put("deptno", deptno);
        return session.selectList("cn.chinwin.demo.pojo.Role.findRoleByDeptno", map);
    }

    @Override
    public List<Role> roleSplit(int start, int ps) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("start", start);
        map.put("ps", ps);

        return session.selectList("cn.chinwin.demo.pojo.Role.roleSplit", map);
    }
}
