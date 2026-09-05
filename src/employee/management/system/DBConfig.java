package employee.management.system;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class DBConfig {
    private static final Config config = ConfigFactory.load();

    public static String getUrl() {
        return config.getString("db.url");
    }

    public static String getUsername() {
        return config.getString("db.username");
    }

    public static String getPassword() {
        return config.getString("db.password");
    }
}
