package cn.chinwin.demo.controller;

import cn.chinwin.demo.pojo.*;
import cn.chinwin.demo.service.IPrivilegeService;
import cn.chinwin.demo.service.IRoleService;
import cn.chinwin.demo.utils.TreeViewUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
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


    @RequestMapping(value = "getRoleSplit", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getRoleSplit(Integer cp, Integer ps, HttpSession session) {
        if (cp == null) {
            cp = 0;
        }
        if (ps == null) {
            ps = 5;
        }
        Users user = (Users) session.getAttribute("users");
        if (user.getRole() == null || user.getRole().getRoleid() == null) {
            return JSON.toJSONString(new TableSplitResult(0, 0, null));
        }
        List<Role> roleSplit = roleService.getRoleSplit(cp, ps);
        int count = roleService.getCount();

        return JSON.toJSONString(new TableSplitResult<>(cp, count, roleSplit));
    }

    @RequestMapping(value = "preChangePri",produces = "application/json;charset=utf-8")
    @ResponseBody
    public String preChangePri( ){

        List<Privilege> privilegeList = privilegeService.getAllPrivilege();
        List<BootStrapTree> bsTrees = TreeViewUtil.translateToBootStrapTree(privilegeList);
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("nodes",bsTrees);
        String s = JSON.toJSONString(bsTrees);
        System.out.println(s);
        return s;
    }

}
