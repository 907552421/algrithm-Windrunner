package leo.test.windrunner.trees;
/**
 * Created by kuoyang.liang on 2017/5/26.
 */

import leo.test.windrunner.DataStructure.DataItem;
import leo.test.windrunner.utils.DataUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * ClassName: ID3TreeTest<br/>
 * Function: ID3TreeTest. <br/>
 * Date:     2017/5/26 <br/>
 *
 * @author kuoyang.liang
 */
public class ID3TreeTest {

    @Test
    public void test() throws IOException {
        String trainFilePath = "D:\\Documents\\IdeaProjects\\algrithm-Windrunner\\data\\hw3_train.dat";
        List<DataItem> trainDataList = DataUtils.loadData(trainFilePath);

        String testFilePath = "D:\\Documents\\IdeaProjects\\algrithm-Windrunner\\data\\hw3_test.dat";
        List<DataItem> testDataList = DataUtils.loadData(testFilePath);
        ID3Tree id3Tree = new ID3Tree();
        id3Tree.train(trainDataList);
        Integer predict = id3Tree.predict(trainDataList.get(0));
        System.out.println(predict);

        Double evaluate = id3Tree.evaluate(testDataList);
        System.out.println(evaluate);

    }
}
