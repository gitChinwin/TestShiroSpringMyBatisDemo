package cn.chinwin.demo.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Chinwin on 2017/8/14.
 */
public class BootStrapTree implements Serializable{

    private String text;

    private List<BootStrapTree> nodes;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<BootStrapTree> getNodes() {
        return nodes;
    }

    public void setNodes(List<BootStrapTree> nodes) {
        this.nodes = nodes;
    }
}
