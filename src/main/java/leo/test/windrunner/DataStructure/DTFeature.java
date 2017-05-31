package leo.test.windrunner.DataStructure;
/**
 * Created by kuoyang.liang on 2017/5/26.
 */

/**
 * ClassName: DTFeature<br/>
 * Function: DTFeature. <br/>
 * Date:     2017/5/26 <br/>
 *
 * @author kuoyang.liang
 */
public class DTFeature {
    private Integer featuerIndex;
    private FeatureType featureType;
    private Integer valueCount;
    private Double value;

    public DTFeature() {
    }

    public Integer getFeatuerIndex() {
        return featuerIndex;
    }

    public void setFeatuerIndex(Integer featuerIndex) {
        this.featuerIndex = featuerIndex;
    }

    public FeatureType getFeatureType() {
        return featureType;
    }

    public void setFeatureType(FeatureType featureType) {
        this.featureType = featureType;
    }

    public Integer getValueCount() {
        return valueCount;
    }

    public void setValueCount(Integer valueCount) {
        this.valueCount = valueCount;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public enum FeatureType{
        CONTINUOUS,DISCRETE;
    }
}

