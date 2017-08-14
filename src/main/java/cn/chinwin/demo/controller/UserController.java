package cn.chinwin.demo.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import cn.chinwin.demo.pojo.Privilege;
import cn.chinwin.demo.pojo.Result;
import cn.chinwin.demo.pojo.TableSplitResult;
import cn.chinwin.demo.pojo.Users;
import cn.chinwin.demo.service.IUserService;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping("/uc")
public class UserController {

    @Resource(name = "userServiceImpl")
    private IUserService userService;

    @RequestMapping(value = "/checklogin", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String checklogin(Users user, HttpSession session) {
        System.out.println("checklogin...");
        Users u = userService.islogin(user);
        Result result = null;
        if (u == null) {
            result = new Result(0, "账号或密码不正确！", null);
        }
        session.setAttribute("users", u);
        u.setLoginPwd(null);
        result = new Result(1, "suc！", u);
        return JSON.toJSONString(result);
    }


    @RequestMapping(value = "/getUserSplit", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getUserSplit(Integer cp, Integer ps, HttpSession session) {
        if (cp == null) {
            cp = 0;
        }
        if (ps == null) {
            ps = 5;
        }
        Users user = (Users) session.getAttribute("users");
        List<Users> userlist = userService.getUserSplit(cp, ps, user.getUserId(), user.getDept().getDeptno());
        int count = userService.getCount(user.getUserId(), user.getDept().getDeptno());
        TableSplitResult<List<Users>> result = new TableSplitResult<>(cp, count, userlist);//
        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "preChangeRole", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String preChangeRole(HttpSession session) {

        Users user = (Users) session.getAttribute("users");
        Result result = userService.getRolesByDept(user);
        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "changeRole", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String changeRole(Users changUser, HttpSession session) {
        if (changUser.getUserId() == null) {
            return JSON.toJSONString(new Result(0, "no parameter enough !", null));
        }
        Users user = (Users) session.getAttribute("users");
        Result result = userService.changeRole(user, changUser);
        return JSON.toJSONString(result);
    }
}
