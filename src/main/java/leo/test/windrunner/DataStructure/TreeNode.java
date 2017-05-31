package leo.test.windrunner.DataStructure;
/**
 * Created by kuoyang.liang on 2017/5/25.
 */

import java.util.List;

/**
 * ClassName: TreeNode<br/>
 * Function: TreeNode. <br/>
 * Date:     2017/5/25 <br/>
 *
 * @author kuoyang.liang
 */
public class TreeNode {
    private Integer featureIndex;

    private Double splitValue;

    private Double value;

    private List<TreeNode> childNodes;

    private Boolean leaf;

    private Integer label;

    private TreeNode parentTreeNode;

    public TreeNode() {
    }

    public Integer getFeatureIndex() {
        return featureIndex;
    }

    public void setFeatureIndex(Integer featureIndex) {
        this.featureIndex = featureIndex;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
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

    public TreeNode getParentTreeNode() {
        return parentTreeNode;
    }

    public void setParentTreeNode(TreeNode parentTreeNode) {
        this.parentTreeNode = parentTreeNode;
    }
}
