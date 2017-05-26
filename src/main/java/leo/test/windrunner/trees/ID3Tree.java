package leo.test.windrunner.trees;
/**
 * Created by kuoyang.liang on 2017/5/25.
 */

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.sun.corba.se.spi.legacy.connection.LegacyServerSocketEndPointInfo;
import leo.test.windrunner.DataStructure.DTFeature;
import leo.test.windrunner.DataStructure.DataItem;
import leo.test.windrunner.DataStructure.TreeNode;
import org.apache.commons.lang3.NotImplementedException;

import java.util.*;

/**
 * ClassName: ID3Tree<br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2017/5/25 <br/>
 *
 * @author kuoyang.liang
 */
public class ID3Tree {
    private TreeNode rootNode;

    private Double smoothness = 0.00001D;

    public ID3Tree() {
        this.rootNode = new TreeNode();
    }

    public void train(List<DataItem> trainList) {
        this.train(this.rootNode, trainList);
    }

    private void train(TreeNode node, List<DataItem> trainList) {
        if (node.getLeaf()!= null && node.getLeaf()) {
            return;
        }

        List<List<DataItem>> subNodesDataList = Lists.newArrayList();
        List<TreeNode> childTreeNodes = Lists.newArrayList();
        node.setChildNodes(childTreeNodes);
        this.splitDateByBestFeature(node, childTreeNodes, trainList, subNodesDataList);
        for (int i = 0; i < childTreeNodes.size(); i++) {
            this.train(childTreeNodes.get(i), subNodesDataList.get(i));
        }
    }

    public Integer predict(DataItem dataItem) {

        return this.predict(this.rootNode,dataItem);
    }

    private Integer predict(TreeNode treeNode,DataItem dataItem){
        if(treeNode.getLabel() != null){
            return treeNode.getLabel();
        }
        DTFeature dtFeature = dataItem.getFeatures().get(treeNode.getFeatureIndex());
        if(dtFeature.getFeatureType().equals(DTFeature.FeatureType.CONTINUOUS)){
            if ( dataItem.getFeatures().get(treeNode.getFeatureIndex()).getValue() < treeNode.getSplitValue() ){
                return this.predict(treeNode.getChildNodes().get(0),dataItem);
            }else{
                return this.predict(treeNode.getChildNodes().get(1),dataItem);
            }
        }else{
            for (TreeNode childNode : treeNode.getChildNodes()) {
                if(childNode.getValue().equals(dataItem.getFeatures().get(treeNode.getFeatureIndex()))){
                    return this.predict(childNode,dataItem);
                }
            }
        }

        throw new RuntimeException("error");
    }


    private TreeNode splitDateByBestFeature(TreeNode node, List<TreeNode> childTreeNodes, List<DataItem> totalDataList,
                                            List<List<DataItem>> subNodesDataList) {
        //满足停止条件
        if (this.satisfiedStopCondition(totalDataList)) {
            node.setChildNodes(null);
            node.setLeaf(true);

            Integer label = null;
            int posiCount = 0;
            int negaCount = 0;
            for (DataItem dataItem : totalDataList) {
                if (dataItem.getLabel().equals(1)) {
                    posiCount++;
                } else {
                    negaCount++;
                }
            }
            label = posiCount >= negaCount ? posiCount : negaCount;

            node.setLabel(label);
            return node;
        }

        //父路径所用特征
        Set<Integer> parentFeatureIndex = Sets.newHashSet();
        TreeNode parentNode = node.getParentTreeNode();
        while (parentNode != null) {
            parentFeatureIndex.add(parentNode.getFeatureIndex());
            parentNode = parentNode.getParentTreeNode();
        }
        int featureCount = totalDataList.get(0).getFeatures().size();

        //寻找信息增加最大特征，如果是连续值 同时寻找最有分割点
        Double maxInformationGain = 0.0;
        Integer bestFeatureIndex = 0;
        for (int i = 0; i < featureCount; i++) {
            if (totalDataList.get(0).getFeatures().get(i).getFeatureType().equals(
                    DTFeature.FeatureType.CONTINUOUS)) {//连续特征

                List<Double> cSegList = this.getCandidateSegmentValue(totalDataList, i);
                Double curMaxFeatureInfoGain = 0.0;
                for (Double segValue : cSegList) {
                    Double informationGain = this.getInformationGain(totalDataList, i, totalDataList.get(0).getFeatures().get(i).getFeatureType(), segValue);
                    if(informationGain > curMaxFeatureInfoGain){
                        curMaxFeatureInfoGain = informationGain;
                        node.setSplitValue(segValue);
                    }
                }

                if(maxInformationGain< curMaxFeatureInfoGain){
                    maxInformationGain = curMaxFeatureInfoGain;
                    bestFeatureIndex = i;
                }

            } else { //离散特征
                if (parentFeatureIndex.contains(i))
                    continue;

                node.setSplitValue(null);

                Double informationGain = this.getInformationGain(totalDataList, i, totalDataList.get(0).getFeatures().get(i).getFeatureType(), null);
                if (maxInformationGain < informationGain) {
                    maxInformationGain = informationGain;
                    bestFeatureIndex = i;
                }
            }
        }

        //根据找到的最优特征，构造当前节点和子节点
        if (totalDataList.get(0).getFeatures().get(bestFeatureIndex).getFeatureType().equals(//连续特征
                DTFeature.FeatureType.CONTINUOUS)) {
            TreeNode lessThanNode = new TreeNode();
            TreeNode greaterThanNode = new TreeNode();
            lessThanNode.setParentTreeNode(node);
            greaterThanNode.setParentTreeNode(node);
            List<DataItem> lessThanDataList = Lists.newArrayList();
            List<DataItem> greaterThanDataList = Lists.newArrayList();
            for (DataItem dataItem : totalDataList) {
                if(dataItem.getFeatures().get(bestFeatureIndex).getValue() <= node.getSplitValue()){
                    lessThanDataList.add(dataItem);
                }else{
                    greaterThanDataList.add(dataItem);
                }
            }

            childTreeNodes.add(lessThanNode);
            childTreeNodes.add(greaterThanNode);
            subNodesDataList.add(lessThanDataList);
            subNodesDataList.add(greaterThanDataList);
            node.setFeatureIndex(bestFeatureIndex);
        }else{//离散值
            Map<Double, List<DataItem>> doubleDataItemMap = Maps.newHashMap();
            for (DataItem dataItem : totalDataList) {
                if (doubleDataItemMap.containsKey(dataItem.getFeatures().get(bestFeatureIndex).getValue())) {
                    doubleDataItemMap.get(dataItem.getFeatures().get(bestFeatureIndex).getValue()).add(dataItem);
                } else {
                    List<DataItem> dataItemList = Lists.newArrayList();
                    doubleDataItemMap.put(dataItem.getFeatures().get(bestFeatureIndex).getValue(), dataItemList);
                }
            }

            for (Map.Entry<Double, List<DataItem>> entry : doubleDataItemMap.entrySet()) {
                TreeNode childTreeNode = new TreeNode();
                childTreeNode.setValue(entry.getKey());
                childTreeNode.setParentTreeNode(node);
                childTreeNodes.add(childTreeNode);
                subNodesDataList.add(entry.getValue());
            }
            node.setFeatureIndex(bestFeatureIndex);
        }

        return node;

    }

    private Double computeEntropy(List<DataItem> dataItemList) {
        int positiveSampleCount = 0;
        int negativeSampleCount = 0;
        for (DataItem dataItem : dataItemList) {
            if (dataItem.getLabel() == 1) {
                positiveSampleCount++;
            } else {
                negativeSampleCount++;
            }
        }
        double posiWeight = positiveSampleCount * 1.0 / dataItemList.size() * 1.0;
        double negaWeight = negativeSampleCount * 1.0 / dataItemList.size() * 1.0;
        Double entropy = Double.MAX_VALUE;
        if(posiWeight!=0.0 && negaWeight!=0.0) {
            entropy = posiWeight * (Math.log(posiWeight) / Math.log(2)) * (-1.0) + negaWeight * (Math.log(negaWeight) / Math.log(2)) * (-1.0);
        }else if(posiWeight!=0.0 && negaWeight == 0.0){
            entropy = posiWeight * (Math.log(posiWeight) / Math.log(2)) * (-1.0);
        }else if (posiWeight==0.0 && negaWeight != 0.0){
            entropy = negaWeight * (Math.log(negaWeight) / Math.log(2)) * (-1.0);
        }

        return entropy;

    }

    private boolean satisfiedStopCondition(List<DataItem> totalDataList) {
        if (totalDataList.size() < 5)
            return true;
        for (int i = 0; i < totalDataList.size() - 1; i++) {
            if (totalDataList.get(i).getLabel() != totalDataList.get(i + 1).getLabel()) {
                return false;
            }
        }
        return true;
    }

    private Double getInformationGain(List<DataItem> totalDataList, Integer featureIndex, DTFeature.FeatureType featureType, Double segValue) {
        Double parentEntropy = this.computeEntropy(totalDataList);

        Double sumOfEntropy = 0.0;
        if (featureType.equals(DTFeature.FeatureType.DISCRETE)) {
            Map<Double, List<DataItem>> doubleDataItemMap = Maps.newHashMap();
            for (DataItem dataItem : totalDataList) {
                if (doubleDataItemMap.containsKey(dataItem.getFeatures().get(featureIndex).getValue())) {
                    doubleDataItemMap.get(dataItem.getFeatures().get(featureIndex).getValue()).add(dataItem);
                } else {
                    List<DataItem> dataItemList = Lists.newArrayList();
                    doubleDataItemMap.put(dataItem.getFeatures().get(featureIndex).getValue(), dataItemList);
                }
            }

            for (Map.Entry<Double, List<DataItem>> entry : doubleDataItemMap.entrySet()) {
                double entropy = this.computeEntropy(entry.getValue());
                sumOfEntropy = (entry.getValue().size() * 1.0 / totalDataList.size() * 1.0) * entropy;
            }
        } else {
            List<DataItem> lessThanDataList = Lists.newArrayList();
            List<DataItem> greaterThanDataList = Lists.newArrayList();
            for (DataItem dataItem : totalDataList) {
                if(dataItem.getFeatures().get(featureIndex).getValue() <= segValue){
                    lessThanDataList.add(dataItem);
                }else{
                    greaterThanDataList.add(dataItem);
                }
            }
            double lessThanEntropy = this.computeEntropy(lessThanDataList);
            double greaterThanEntropy = this.computeEntropy(greaterThanDataList);
            sumOfEntropy = ((lessThanDataList.size()* 1.0 )/totalDataList.size() * 1.0)* lessThanEntropy +
                    ((greaterThanDataList.size()*1.0)/greaterThanDataList.size() * 1.0)* greaterThanEntropy;
        }

        Double informationGain = parentEntropy - sumOfEntropy;
        return informationGain;

    }

    private List<Double> getCandidateSegmentValue(List<DataItem> dataItemList, Integer featureIndex) {
        List<Double> segList = Lists.newArrayList();
        Collections.sort(dataItemList, new Comparator<DataItem>() {
            @Override
            public int compare(DataItem o1, DataItem o2) {
                return o1.getFeatures().get(featureIndex).getValue().compareTo(o2.getFeatures().get(featureIndex).getValue());
            }
        });

        for (int i = 0; i < dataItemList.size() - 1; i++) {
            if (Math.abs(dataItemList.get(i).getFeatures().get(featureIndex).getValue() -
                    dataItemList.get(i + 1).getFeatures().get(featureIndex).getValue()) > 0.001) {
                segList.add((dataItemList.get(i).getFeatures().get(featureIndex).getValue() +
                        dataItemList.get(i + 1).getFeatures().get(featureIndex).getValue()) / 2.0);
            }
        }
        return segList;
    }
}
