package cn.chinwin.demo.controller;

import cn.chinwin.demo.pojo.Dept;
import cn.chinwin.demo.pojo.Result;
import cn.chinwin.demo.pojo.TableSplitResult;
import cn.chinwin.demo.service.IDeptService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller("dc")
public class DeptController {


    @Resource(name = "deptServiceImpl")
    private IDeptService deptService;


    @RequestMapping("getDeptSplit")
    @ResponseBody
    public TableSplitResult getDeptSplit(Integer cp,Integer ps){
        if (cp == null) {
            cp = 0;
        }
        if (ps == null) {
            ps = 5;
        }
        List<Dept> deptSplit = deptService.getDeptSplit(cp, ps);
        int total = deptService.getCount();
        TableSplitResult<List<Dept>> result = new TableSplitResult<>(cp, total, deptSplit);
        return result;
    }


    @RequestMapping("preChangeDept")
    @ResponseBody
    public Result preChangeDept(){
        List<Dept> deptList = deptService.getAllDept();
        return new Result(1,"suc",deptList);
    }

    @RequestMapping("updateDept")
    @ResponseBody
    public Result updateDept(Dept dept){
        Result result;
        if(dept.getDeptno()==null){
            result = new Result(0,"id不能为空！",null);
        }
        boolean flag = deptService.updateDept(dept);
        if(flag){
            result = new Result(1,"suc！",null);
        }else{
            result = new Result(0,"id不能为空！",null);
        }
        return result;
    }


    @RequestMapping("addDept")
    @ResponseBody
    public Result addDept(Dept dept){
        Result result;
        boolean flag = deptService.addDept(dept);
        if(flag){
            result = new Result(1,"suc！",null);
        }else{
            result = new Result(0,"id不能为空！",null);
        }
        return result;
    }

}
