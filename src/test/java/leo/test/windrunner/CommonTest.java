package leo.test.windrunner;
/**
 * Created by kuoyang.liang on 2017/5/26.
 */

import org.junit.Test;

import javax.xml.bind.SchemaOutputResolver;

/**
 * ClassName: CommonTest<br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2017/5/26 <br/>
 *
 * @author kuoyang.liang
 */
public class CommonTest {
    @Test
    public void test(){
        Double a = 1.11;
        Double b = 2.22;
        double c  =  1.11;
        Double d = 1.11;
        System.out.println(a.hashCode());
        System.out.println(b.hashCode());
        System.out.println(Double.valueOf(c).hashCode());
        System.out.println(d.hashCode());
    }

}
