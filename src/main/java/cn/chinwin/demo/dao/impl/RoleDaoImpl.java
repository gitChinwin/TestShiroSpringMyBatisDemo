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
    public Role findRoleById(Integer roleid) {
        return session.selectOne("cn.chinwin.demo.pojo.Role.findRoleByRoleId",roleid);
    }

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

    @Override
    public int getCount() {
        return session.selectOne("cn.chinwin.demo.pojo.Role.getCount");
    }

    @Override
    public int updateRole(Role role) {
        return session.update("cn.chinwin.demo.pojo.Role.updateRoleCondition",role);
    }

    @Override
    public int delPriList(Integer roleid) {
        return session.delete("cn.chinwin.demo.pojo.Role.delPriList",roleid);

    }

    @Override
    public int addPrivilege(Integer priid, Integer roleid) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("priid",priid);
        map.put("roleid",roleid);
        return session.insert("cn.chinwin.demo.pojo.Role.addPriList",map);
    }
}
