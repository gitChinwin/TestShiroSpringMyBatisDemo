package cn.chinwin.demo.controller;

import cn.chinwin.demo.pojo.Role;
import cn.chinwin.demo.pojo.TableSplitResult;
import cn.chinwin.demo.pojo.Users;
import cn.chinwin.demo.service.IRoleService;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("rc")
public class RoleController {


    @Resource(name = "roleServiceImpl")
    private IRoleService roleService;


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


}
