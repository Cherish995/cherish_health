import com.aliyuncs.exceptions.ClientException;
import com.cherish.health.utils.SMSUtils;
import org.junit.Test;

/**
 * @author Cherish
 * @version 1.8.0_121
 * @date 2020/11/26
 */
public class MyTest {
    @Test
    public void test() throws ClientException {
        SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE, "13277312137", "666666");

    }
}
