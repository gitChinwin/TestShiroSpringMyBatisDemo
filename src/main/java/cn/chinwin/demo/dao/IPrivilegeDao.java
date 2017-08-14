package cn.chinwin.demo.dao;

import cn.chinwin.demo.pojo.Privilege;

import java.util.List;

/**
 * Created by chinwin on 2017/8/9.
 */
public interface IPrivilegeDao {

    List<Privilege> findAllPrivilege();

    List<String> findAllUrl();

    List<Privilege> findPriByRoleid(int roleid);
}
