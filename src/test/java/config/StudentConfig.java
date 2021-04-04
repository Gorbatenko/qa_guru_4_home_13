package config;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:secret.properties"})
public interface StudentConfig extends Config {
    @Key("student.email")
    String getStudentEmail();

    @Key("student.password")
    String getStudentPassword();
}
