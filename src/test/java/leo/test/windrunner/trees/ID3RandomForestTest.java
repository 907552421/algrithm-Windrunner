package leo.test.windrunner.trees;
/**
 * Created by kuoyang.liang on 2017/5/27.
 */

import leo.test.windrunner.DataStructure.DataItem;
import leo.test.windrunner.utils.DataUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * ClassName: ID3RandomForestTest<br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2017/5/27 <br/>
 *
 * @author kuoyang.liang
 */
public class ID3RandomForestTest {

    @Test
    public void test() throws IOException {
        String trainFilePath = "D:\\Documents\\IdeaProjects\\algrithm-Windrunner\\data\\hw3_train.dat";
        List<DataItem> trainDataList = DataUtils.loadData(trainFilePath);

        String testFilePath = "D:\\Documents\\IdeaProjects\\algrithm-Windrunner\\data\\hw3_test.dat";
        List<DataItem> testDataList = DataUtils.loadData(testFilePath);
        ID3RandomForest id3RandomForest = new ID3RandomForest(3,0.6D);
        id3RandomForest.train(trainDataList);
        Integer predict = id3RandomForest.predict(trainDataList.get(0));
        System.out.println(predict);

        Double evaluate = id3RandomForest.evaluate(testDataList);
        System.out.println(evaluate);

    }
}
