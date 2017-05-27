package leo.test.windrunner.trees;
/**
 * Created by kuoyang.liang on 2017/5/27.
 */

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import leo.test.windrunner.DataStructure.DataItem;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * ClassName: ID3RandomForest<br/>
 * Function: ID3 Random Forest. <br/>
 * Date:     2017/5/27 <br/>
 *
 * @author kuoyang.liang
 */
public class ID3RandomForest {

    private Integer numOfTree;

    private List<ID3Tree> id3TreeList;

    private Double eachSampleRate;

    public ID3RandomForest(Integer numOfTree,Double eachSampleRate) {
        this.numOfTree = numOfTree;
        this.eachSampleRate = eachSampleRate;

        this.id3TreeList = Lists.newArrayList();
    }

    public void train(List<DataItem> trainDataList){
        this.id3TreeList.clear();
        for (int i = 0; i < numOfTree; i++) {
            List<DataItem> eachTreeSampleData = this.sampleData(trainDataList);
            ID3Tree id3Tree = new ID3Tree();
            id3Tree.train(eachTreeSampleData);
            this.id3TreeList.add(id3Tree);
        }
    }

    public Integer predict(DataItem dataItem){
        Map<Integer,Integer> resultMapCount = Maps.newHashMap();
        for (ID3Tree id3Tree : this.id3TreeList) {
            Integer predict = id3Tree.predict(dataItem);
            if(resultMapCount.containsKey(predict)){
                resultMapCount.put(predict,resultMapCount.get(predict) + 1);
            }else{
                resultMapCount.put(predict,1);
            }
        }

        int result = Integer.MAX_VALUE;
        int maxCount = 0;
        for (Map.Entry<Integer,Integer> entry : resultMapCount.entrySet()){
            if( entry.getValue() > maxCount){
                maxCount = entry.getValue();
                result = entry.getKey();
            }
        }

        return result;
    }

    public Double evaluate(List<DataItem> dataItemList){
        Double precision = 0.0;
        Integer correctCount = 0;
        for (DataItem dataItem : dataItemList) {
            Integer predict = this.predict(dataItem);
            if(predict.equals(dataItem.getLabel())){
                correctCount ++;
            }
        }
        precision = correctCount*1.0/dataItemList.size()*1.0;
        return precision;
    }



    // ---------------------------------------   private method   -------------------------------------------
    private List<DataItem> sampleData(List<DataItem> totalSampleData){
        int totalNum = totalSampleData.size();
        int resultNum = (int) Math.round(totalNum * this.eachSampleRate);
        List<DataItem> sampleResultData  = Lists.newArrayList();

        for (int i = 0; i < resultNum; i++) {
            Random random = new Random();
            int index = random.nextInt(totalNum);
            sampleResultData.add(totalSampleData.get(index));
        }

        return sampleResultData;
    }

    // ---------------------------------------   Getter and Setter   -------------------------------------------


    public Integer getNumOfTree() {
        return numOfTree;
    }

    public void setNumOfTree(Integer numOfTree) {
        this.numOfTree = numOfTree;
    }

    public List<ID3Tree> getId3TreeList() {
        return id3TreeList;
    }

    public void setId3TreeList(List<ID3Tree> id3TreeList) {
        this.id3TreeList = id3TreeList;
    }
}
