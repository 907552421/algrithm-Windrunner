package leo.test.windrunner.DataStructure;
/**
 * Created by kuoyang.liang on 2017/5/25.
 */

import java.util.List;

/**
 * ClassName: DataItem<br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2017/5/25 <br/>
 *
 * @author kuoyang.liang
 */
public class DataItem {
    private List<Double> features;

    private Integer label;

    public List<Double> getFeatures() {
        return features;
    }

    public void setFeatures(List<Double> features) {
        this.features = features;
    }

    public Integer getLabel() {
        return label;
    }

    public void setLabel(Integer label) {
        this.label = label;
    }
}
