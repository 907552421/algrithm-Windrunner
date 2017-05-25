package leo.test.windrunner.trees;
/**
 * Created by kuoyang.liang on 2017/5/25.
 */

import com.google.common.collect.Lists;
import leo.test.windrunner.DataStructure.DataItem;
import leo.test.windrunner.DataStructure.TreeNode;
import org.apache.commons.lang3.NotImplementedException;
import org.omg.CosNaming.NamingContextPackage.NotEmpty;

import java.util.List;

/**
 * ClassName: ID3Tree<br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2017/5/25 <br/>
 *
 * @author kuoyang.liang
 */
public class ID3Tree {
    private TreeNode rootNode;

    public ID3Tree() {
    }

    public void train(List<DataItem> trainList){
        this.train(this.rootNode,trainList);
    }

    public void train(TreeNode node, List<DataItem> trainList){
        List<List<DataItem>> subNodesDataList = Lists.newArrayList();
        List<TreeNode> childTreeNodes = Lists.newArrayList();
        node.setChildNodes(childTreeNodes);
        this.splitDateByBestFeature(node,childTreeNodes,trainList,subNodesDataList);
        for (int i = 0; i < childTreeNodes.size(); i++) {
            this.train(childTreeNodes.get(i),subNodesDataList.get(i));
        }
    }

    public Integer predict(){
        throw new NotImplementedException("not impl");
    }


    private TreeNode splitDateByBestFeature(TreeNode node,List<TreeNode> childTreeNodes,List<DataItem> totalDataList,
                                            List<List<DataItem>> subNodesDataList){
        //满足停止条件
        if(this.satisfiedStopCondition(totalDataList)){
            node.setChildNodes(null);
            node.setLeaf(true);
            node.setLabel(totalDataList.get(0).getLabel());
            return node;
        }

        throw new NotImplementedException("not impl");
    }

    private boolean satisfiedStopCondition(List<DataItem> totalDataList){
        throw new  NotImplementedException("not impl");
    }
}
