package cn.chinwin.demo.controller;

import cn.chinwin.demo.pojo.*;
import cn.chinwin.demo.service.IPrivilegeService;
import cn.chinwin.demo.service.IRoleService;
import cn.chinwin.demo.utils.TreeViewUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("rc")
public class RoleController {


    @Resource(name = "roleServiceImpl")
    private IRoleService roleService;

    @Resource(name = "privilegeServiceImpl")
    private IPrivilegeService privilegeService;


    @RequestMapping(value = "getRoleSplit")
    @ResponseBody
    public TableSplitResult getRoleSplit(Integer cp, Integer ps, HttpSession session) {
        if (cp == null) {
            cp = 0;
        }
        if (ps == null) {
            ps = 5;
        }
        Users user = (Users) session.getAttribute("users");
        if (user.getRole() == null || user.getRole().getRoleid() == null) {
            return new TableSplitResult(0, 0, null);
        }
        List<Role> roleSplit = roleService.getRoleSplit(cp, ps);
        int count = roleService.getCount();

        return new TableSplitResult<>(cp, count, roleSplit);
    }

    @RequestMapping(value = "preChangePri")
    @ResponseBody
    public List<BootStrapTree> preChangePri() {

        List<Privilege> privilegeList = privilegeService.getAllPrivilege();
        List<BootStrapTree> bsTrees = TreeViewUtil.translateToBootStrapTree(privilegeList);
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("nodes",bsTrees);

        return bsTrees;
    }

    @RequestMapping(value = "updateRole")
    @ResponseBody
    public Result updateRole(@RequestBody Role role) {
        Result result = null;
        if (role.getRoleid() == null) {
            result = new Result(0, "参数不足", null);
        }
        try {
            boolean flag = roleService.updataRoleIncludePrivilege(role);
            if (flag) {

                result = new Result(1, "suc", null);
            } else {
                result = new Result(0, "要先启用角色才允许修改", null);
            }
        } catch (RuntimeException e) {
            result = new Result(0, "error", null);
        }
        return result;
    }




    @RequestMapping(value = "changeRoleStatus")
    @ResponseBody
    public Result changeRoleStatus(@RequestBody Role role) {
        Result result;
        if (role.getRoleid() == null) {
            result = new Result(0, "参数不足！", null);
        }
        boolean flag = roleService.updateRoleStatus(role);
        if (flag) {
            result = new Result(1, "suc", null);
        } else {
            result = new Result(0, "nullPointException", null);
        }
        return result;
    }
}
