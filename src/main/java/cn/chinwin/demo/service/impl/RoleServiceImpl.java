package cn.chinwin.demo.service.impl;

import cn.chinwin.demo.dao.IPrivilegeDao;
import cn.chinwin.demo.dao.IRoleDao;
import cn.chinwin.demo.pojo.BootStrapTree;
import cn.chinwin.demo.pojo.Privilege;
import cn.chinwin.demo.pojo.Role;
import cn.chinwin.demo.service.IRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService {

    @Resource(name = "roleDaoImpl")
    private IRoleDao roleDao;


    @Override
    public List<Role> getRoleSplit(int start, int ps) {
        return roleDao.roleSplit(start, ps);
    }

    @Override
    public int getCount() {
        return roleDao.getCount();
    }

    @Override
    @Transactional
    public boolean updateRoleStatus(Role role) throws RuntimeException {
        int result = roleDao.updateRole(role);
        if (result < 1) {
            throw new RuntimeException();
        }
        return true;
    }

    @Override
    @Transactional
    public boolean updataRoleIncludePrivilege(Role role) throws RuntimeException {
//        int update = roleDao.updateRole(role);//先更新role本身的参数，不含权限
//        if (update < 1) {
//            throw new RuntimeException();
//        }
        Role dbRole = roleDao.findRoleById(role.getRoleid());
        if("1".equals(dbRole.getRoleStatus())){
            return false;//如果要更改的角色的状态为异常，不允许修改！要先启用角色才允许修改！
        }
        if (dbRole.getPriList() == null && role.getPriList() == null||(dbRole.getPriList().size() == 0 && role.getPriList().size() == 0)) {
            //如果前端传来的权限和后台查出的权限都为空，返回true
            return true;
        }
        int delete = roleDao.delPriList(role.getRoleid());//根据roleid删除关系映射表的所有与权限的关系
        if (delete != dbRole.getPriList().size()) {
            throw new RuntimeException();//如果删除的数据行数不等于他实际拥有的权限个数，抛异常回滚吧
        }
        List<Privilege> priList = role.getPriList();
        for (Privilege privilege : priList) {
            int add = roleDao.addPrivilege(privilege.getPriid(), role.getRoleid());//重新添加新的关系
            if (add < 1) {
                throw new RuntimeException();
            }
        }
        return true;
    }


}
