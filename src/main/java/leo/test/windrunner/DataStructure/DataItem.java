package leo.test.windrunner.DataStructure;
/**
 * Created by kuoyang.liang on 2017/5/25.
 */

import java.util.List;

/**
 * ClassName: DataItem<br/>
 * Function: DataItem. <br/>
 * Date:     2017/5/25 <br/>
 *
 * @author kuoyang.liang
 */
public class DataItem {
    private List<DTFeature> features;

    private Integer label;

    public List<DTFeature> getFeatures() {
        return features;
    }

    public void setFeatures(List<DTFeature> features) {
        this.features = features;
    }

    public Integer getLabel() {
        return label;
    }

    public void setLabel(Integer label) {
        this.label = label;
    }
}
