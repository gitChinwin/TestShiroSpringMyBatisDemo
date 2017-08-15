package cn.chinwin.demo.utils;

import cn.chinwin.demo.pojo.BootStrapTree;
import cn.chinwin.demo.pojo.Privilege;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chinwin on 2017/8/14.
 */
public class TreeViewUtil {


    public static List<BootStrapTree> translateToBootStrapTree(List<Privilege> src) {
        List<BootStrapTree> treeList = new ArrayList<>();
        BootStrapTree bst = null;
        for (Privilege p : src) {
            if (p.getParentid() == 0) {
                bst = new BootStrapTree();
                bst.setText(p.getPriName());
                bst.setPriid(p.getPriid());
                bst.setNodes(getNodes(p, src));
                treeList.add(bst);
            }
        }

        return treeList;
    }

    public static List<BootStrapTree> getNodes(Privilege p, List<Privilege> src) {
        List<BootStrapTree> treeList = new ArrayList<>();
        BootStrapTree bst = null;
        for (Privilege pp : src) {
            if (pp.getParentid().equals(p.getPriid())) {
                bst = new BootStrapTree();
                bst.setPriid(pp.getPriid());
                bst.setText(pp.getPriName());
                bst.setNodes(null);
                treeList.add(bst);
            }

        }
        return treeList;
    }
}
