package cn.chinwin.demo.service.impl;

import cn.chinwin.demo.dao.IPrivilegeDao;
import cn.chinwin.demo.dao.IRoleDao;
import cn.chinwin.demo.dao.IUserDao;
import cn.chinwin.demo.pojo.Privilege;
import cn.chinwin.demo.pojo.Result;
import cn.chinwin.demo.pojo.Role;
import cn.chinwin.demo.pojo.Users;
import cn.chinwin.demo.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.PipedReader;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceImpl implements IUserService {

    @Resource(name = "userDaoImpl")
    private IUserDao userDao;

    @Resource(name = "privilegeDaoImpl")
    private IPrivilegeDao privilegeDao;

    @Resource(name = "roleDaoImpl")
    private IRoleDao roleDao;

    public Users islogin(Users user) {
        if (user == null) {
            return null;
        }
        Users u = userDao.isLogin(user);
        if (u != null && u.getRole() != null && u.getRole().getRoleid() == 1) {
            u.getRole().setPriList(privilegeDao.findAllPrivilege());
        }
        return u;
    }

    @Override
    public List<String> getPermissionsByUserName(String loginname) {
        List<String> priUrls = null;
        Users user = this.islogin(new Users(loginname));
        List<Privilege> priList = user.getRole().getPriList();
        if (priList != null && !priList.isEmpty()) {
            priUrls = new ArrayList<>();
            for (Privilege p : priList) {
                if (p == null) {
                    continue;
                }
                String[] split = p.getPriUrl().split(";");
                if (split.length > 0) {
                    for (int i = 0; i < split.length; i++) {
                        String url = split[i];
                        priUrls.add(url);
                    }
                }
            }
        }
        return priUrls;
    }

    @Override
    public List<Users> getUserSplit(int cp, int ps, int userid, int deptno) {

        return userDao.getUserSplit(cp, ps, userid, deptno);
    }

    @Override
    public int getCount(int userid, int deptno) {
        return userDao.getCount(userid, deptno);
    }

    @Override
    public Result getRolesByDept(Users user) {
        if (user.getUserId() == null || user.getDept() == null || user.getDept().getDeptno() == null) {
            return new Result<Role>(0, "no parameter enough !", null);
        }
        List<Role> roles = roleDao.findRoleByDeptno(user.getUserId(), user.getDept().getDeptno());
        return new Result<List<Role>>(1, "suc", roles);
    }

    @Override
    @Transactional
    public Result changeRole(Users user, Users changUser) {
        Result result = null;
        if (user.getUserId() == null || user.getDept() == null || user.getDept().getDeptno() == null) {
            return new Result<Role>(0, "user error !", null);
        }
        List<Role> roles = roleDao.findRoleByDeptno(user.getUserId(), user.getDept().getDeptno());
        if (!isContains(roles, changUser.getRoleId())) {
            return new Result<Role>(0, "no privilege enough !", null);
        }
        boolean flag = userDao.updataUser(changUser);
        if (flag) {
            result = new Result(1, "suc", null);
        } else {
            result = new Result(0, "服务器跑到火星啦!", null);
        }
        return result;
    }

    private boolean isContains(List<Role> list, Integer roleid) {
        for (Role role : list) {
            if (role == null) {
                continue;
            }
            if (roleid.equals(role.getRoleid())) {
                return true;
            }
        }
        return false;
    }


}
