package cn.chinwin.demo.service.impl;

import cn.chinwin.demo.dao.IDeptDao;
import cn.chinwin.demo.pojo.Dept;
import cn.chinwin.demo.service.IDeptService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DeptServiceImpl implements IDeptService {

    @Resource(name = "deptDaoImpl")
    private IDeptDao deptDao;


    @Override
    public List<Dept> getDeptSplit(Integer start, Integer ps) {
        List<Dept> deptSplit = deptDao.getDeptSplit(start, ps);
        for (Dept dept : deptSplit) {
            if(dept.getParentid()!=null){
                Dept deptById = deptDao.findDeptById(dept.getParentid());
                dept.setParent(deptById);
            }
        }
        return deptSplit;
    }

    @Override
    public List<Dept> getAllDept() {
        return deptDao.findAllDept();
    }

    @Override
    @Transactional
    public boolean updateDept(Dept dept) {
        int update = deptDao.updateDept(dept);
        if(update<0){
            throw new RuntimeException();
        }
        return true;
    }

    @Override
    @Transactional
    public boolean addDept(Dept dept) {
        int add = deptDao.addDept(dept);
        if(add<0){
            throw new RuntimeException();
        }
        return true;
    }

    @Override
    public int getCount() {
        return deptDao.getCount();
    }
}
