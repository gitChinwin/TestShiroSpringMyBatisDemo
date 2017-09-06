package cn.chinwin.demo.service.impl;

import cn.chinwin.demo.dao.IUserDao;
import cn.chinwin.demo.pojo.Users;
import cn.chinwin.demo.service.ITestService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * 测试mysql行级悲观锁用！！！！
 */


@Service
public class TestServiceImpl implements ITestService {

    @Resource(name = "userDaoImpl")
    private IUserDao userDao;


    @Transactional
    @Override
    public void test1() {
        Users users = userDao.get(5);
        System.out.println(users);
        String temp = "获取行级悲观锁";


        HashMap<String, Object> map = new HashMap<>();
        map.put("userId",users.getUserId());
        map.put("loginName",temp);

        boolean b = userDao.updateLoginName(map);
        System.out.println(b);
    }

    @Transactional
    @Override
    public void test2() {
        Users users = userDao.get(5);
        System.out.println("test....");

        System.out.println(users);
        String temp = "线程2想抢先修改！";

        HashMap<String, Object> map = new HashMap<>();
        map.put("userId",users.getUserId());
        map.put("loginName",temp);
        boolean b = userDao.updateLoginName(map);
        System.out.println(b);


    }
}
