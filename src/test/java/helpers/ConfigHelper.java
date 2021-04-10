package helpers;

import config.EnvironmentConfig;
import config.StudentConfig;
import org.aeonbits.owner.ConfigFactory;

public class ConfigHelper {

    public static String getBrowserSize() {
        return getEnvironmentConfig().getBrowserSize();
    }

    public static String getBrowser() {
        return getEnvironmentConfig().getBrowser();
    }

    public static String getBaseUrl() {
        return getEnvironmentConfig().getBaseUrl();
    }

    public static String getPlatform() {
        return getEnvironmentConfig().getPlatform();
    }

    public static String getSelenoidVideo() {
        return getEnvironmentConfig().getSelenoidVideo();
    }
    public static String getStudentPassword() {
        return getStudentConfig().getStudentPassword();
    }

    public static String getStudentEmail() {
        return getStudentConfig().getStudentEmail();
    }

    public static String getWebRemoteDriver() {
        // https://%s:%s@selenoid.autotests.cloud/wd/hub/
        return String.format(getEnvironmentConfig().getSelenoidRemote(),
                getEnvironmentConfig().getSelenoidLogin(),
                getEnvironmentConfig().getSelenoidPassword());
    }

//    private static AuthorizationConfig getAuthorizationConfig() {
//        return ConfigFactory.newInstance().create(
//                AuthorizationConfig.class, System.getProperties());
//    }
//
//    private static WebConfig getWebConfig() {
//        return ConfigFactory.newInstance().create(WebConfig.class, System.getProperties());
//    }

    private static EnvironmentConfig getEnvironmentConfig() {
        return ConfigFactory.newInstance().create(EnvironmentConfig.class);
    }

    private static StudentConfig getStudentConfig() {
        return ConfigFactory.newInstance().create(StudentConfig.class);
    }
}
