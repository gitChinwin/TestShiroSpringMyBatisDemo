package cn.chinwin.demo.service.impl;

import cn.chinwin.demo.dao.IPrivilegeDao;
import cn.chinwin.demo.pojo.Privilege;
import cn.chinwin.demo.service.IPrivilegeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PrivilegeServiceImpl implements IPrivilegeService{

    @Resource(name = "privilegeDaoImpl")
    private IPrivilegeDao privilegeDao;


    @Override
    public List<Privilege> getAllPrivilege() {
        return privilegeDao.findAllPrivilege();
    }
}
