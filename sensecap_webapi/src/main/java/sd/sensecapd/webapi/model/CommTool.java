package sd.sensecapd.webapi.model;

import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;
import java.util.Random;

public class CommTool {
    /*
    * Gets a string of a specified length
    * */
    public static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /*
    * Read the configuration value of the specified key in the configuration file
    * */
    public static String readProperty(String key) {
        Properties properties = new Properties();
        try {
            properties = PropertiesLoaderUtils.loadAllProperties("application.properties");
            return  (new String(properties.getProperty(key).getBytes("iso-8859-1"), "gbk"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
