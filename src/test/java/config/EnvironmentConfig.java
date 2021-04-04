package config;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:${platform}.properties",
                 "classpath:environment.properties"})
@Config.LoadPolicy(Config.LoadType.MERGE)
public interface EnvironmentConfig extends Config {

    @Key("browser.size")
    String getBrowserSize();

    @Key("browser")
    String getBrowser();

    @Key("platform")
    @DefaultValue("local")
    String getPlatform();

    @Key("selenoid.url.remote")
    String getSelenoidRemote();

    @Key("selenoid.url.video")
    String getSelenoidVideo();

    @Key("selenoid.login")
    String getSelenoidLogin();

    @Key("selenoid.password")
    String getSelenoidPassword();

    @Key("base.url")
    @DefaultValue("https://qa.guru/")
    String getBaseUrl();

}
