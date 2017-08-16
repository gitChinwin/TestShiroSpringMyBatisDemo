package cn.chinwin.demo.dao.impl;

import cn.chinwin.demo.dao.IDeptDao;
import cn.chinwin.demo.pojo.Dept;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Repository
public class DeptDaoImpl implements IDeptDao{

    @Resource(name = "sqlSessionTemplate")
    private SqlSessionTemplate session;

    @Override
    public List<Dept> getDeptSplit(Integer start, Integer ps) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("start",start);
        map.put("ps",ps);
        return session.selectList("cn.chinwin.demo.pojo.Dept.deptSplit",map);
    }

    @Override
    public List<Dept> findAllDept() {
        return session.selectList("cn.chinwin.demo.pojo.Dept.findAllDept");
    }

    @Override
    public int updateDept(Dept dept) {
        return session.update("cn.chinwin.demo.pojo.Dept.deptSplit.updateDeptInfo",dept);
    }

    @Override
    public Dept findDeptById(Integer deptno) {
        return session.selectOne("cn.chinwin.demo.pojo.Dept.findDeptByDeptno",deptno);
    }

    @Override
    public int addDept(Dept dept) {
        return session.insert("cn.chinwin.demo.pojo.Dept.addDeptInfo",dept);
    }

    @Override
    public int getCount() {
        return session.selectOne("cn.chinwin.demo.pojo.Dept.getCount");
    }
}
