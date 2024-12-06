import cn.hutool.core.util.IdUtil;
import com.wechat.pay.java.core.util.PemUtil;

/**
 * @author wxj
 * 2024/9/19
 */
public class MainTest {

    public static void main(String[] args) {
        System.out.println(IdUtil.getSnowflakeNextIdStr());

//        System.out.println(PemUtil.loadPrivateKeyFromPath("/Users/wxj/Downloads/1666913804_20240206_cert/apiclient_key.pem"));
    }
}
