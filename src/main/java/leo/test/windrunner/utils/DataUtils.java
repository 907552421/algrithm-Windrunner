package leo.test.windrunner.utils;
/**
 * Created by kuoyang.liang on 2017/5/26.
 */

import com.google.common.collect.Lists;
import com.sun.org.apache.xalan.internal.utils.FeatureManager;
import leo.test.windrunner.DataStructure.DTFeature;
import leo.test.windrunner.DataStructure.DataItem;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * ClassName: DataUtils<br/>
 * Function: DataUtils. <br/>
 * Date:     2017/5/26 <br/>
 *
 * @author kuoyang.liang
 */
public class DataUtils {

    public static List<DataItem> loadData(String dataFilePath) throws IOException {
        List<DataItem> dataItemList = Lists.newArrayList();

        try(BufferedReader reader = new BufferedReader(new FileReader(dataFilePath))){
            String line;
            while ( (line=reader.readLine())!= null){
                if(StringUtils.isBlank(StringUtils.trim(line)))
                    continue;
                DataItem dataItem = new DataItem();
                List<DTFeature> featureList = Lists.newArrayList();
                String[] strs = line.split(" ");
                for (int i = 0; i < strs.length - 1; i++) {
                    DTFeature dtFeature = new DTFeature();
                    dtFeature.setFeatuerIndex(i);
                    dtFeature.setFeatureType(DTFeature.FeatureType.CONTINUOUS);
                    dtFeature.setValue(Double.valueOf(strs[i]));
                    featureList.add(dtFeature);
                }
                dataItem.setFeatures(featureList);
                dataItem.setLabel(Integer.valueOf(strs[strs.length -1]));
                dataItemList.add(dataItem);
            }
        }
        return dataItemList;
    }
}
