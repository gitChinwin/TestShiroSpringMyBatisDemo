package cn.chinwin.demo.service;


import cn.chinwin.demo.pojo.BootStrapTree;
import cn.chinwin.demo.pojo.Role;

import java.util.List;

/**
 * Created by chinwin on 2017/8/14.
 */
public interface IRoleService {


    List<Role> getRoleSplit(int start, int ps);

    int getCount();


}
