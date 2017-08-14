package cn.chinwin.demo.dao.impl;

import cn.chinwin.demo.dao.IPrivilegeDao;
import cn.chinwin.demo.pojo.Privilege;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class PrivilegeDaoImpl implements IPrivilegeDao{

    @Resource(name="sqlSessionTemplate")
    private SqlSessionTemplate session;

    @Override
    public List<Privilege> findAllPrivilege() {
        return session.selectList("cn.chinwin.demo.pojo.Privilege.findAllPrivilege");
    }

    @Override
    public List<String> findAllUrl() {
        return session.selectList("cn.chinwin.demo.pojo.Privilege.findAllUrl");
    }

    @Override
    public List<Privilege> findPriByRoleid(int roleid) {
        return session.selectList("cn.chinwin.demo.pojo.Privilege.findPrivilegeByRoleId",roleid);
    }
}
