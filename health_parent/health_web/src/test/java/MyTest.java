import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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

    @Test
    public void test2(){
        List<Integer> list1 = new ArrayList<>();
        list1.add(1);
        list1.add(2);
        List<Integer> list2 = new LinkedList<>();
        list2.add(1);
        list2.add(2);
        System.out.println(list1.equals(list2));
//        System.out.println(list1.toString().equals(list2.toString()));
    }

}
