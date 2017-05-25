package leo.test.windrunner.DataStructure;
/**
 * Created by kuoyang.liang on 2017/5/25.
 */

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.List;

/**
 * ClassName: TreeNode<br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2017/5/25 <br/>
 *
 * @author kuoyang.liang
 */
public class TreeNode {
    private Integer featureIndex;

    private Double splitValue;

    private List<TreeNode> childNodes;

    private Boolean leaf;

    private Integer label;

    public TreeNode() {
    }

    public Integer getFeatureIndex() {
        return featureIndex;
    }

    public void setFeatureIndex(Integer featureIndex) {
        this.featureIndex = featureIndex;
    }

    public Double getSplitValue() {
        return splitValue;
    }

    public void setSplitValue(Double splitValue) {
        this.splitValue = splitValue;
    }

    public List<TreeNode> getChildNodes() {
        return childNodes;
    }

    public Boolean getLeaf() {
        return leaf;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }

    public void setChildNodes(List<TreeNode> childNodes) {
        this.childNodes = childNodes;
    }

    public Integer getLabel() {
        return label;
    }

    public void setLabel(Integer label) {
        this.label = label;
    }
}
