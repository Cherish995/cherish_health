import org.junit.Test;

/**
 * @author Cherish
 * @version 1.8.0_121
 * @date 2020/11/21
 */
public class MyTest {

    @Test
    public void test(){
        String s = "00012344";
        System.out.println(s.matches("[0-9]+"));
    }
}
