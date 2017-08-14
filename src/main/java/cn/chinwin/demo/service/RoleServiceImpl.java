package cn.chinwin.demo.service;

import cn.chinwin.demo.dao.IRoleDao;
import cn.chinwin.demo.pojo.Role;
import cn.chinwin.demo.pojo.TableSplitResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService{

    @Resource(name = "roleDaoImpl")
    private IRoleDao roleDao;

    @Override
    public List<Role> getRoleSplit(int start, int ps) {
        return roleDao.roleSplit(start, ps);
    }
}
