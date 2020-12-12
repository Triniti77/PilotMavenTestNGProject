package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReaderClassLoader {
    private static PropertiesReaderClassLoader loadProperties = null;
    private static Properties properties = null;
    private static InputStream inputStream = null;

    private PropertiesReaderClassLoader(String propertyFileName) {
        inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(propertyFileName);
    }

    public static PropertiesReaderClassLoader getInstance(String env) {
        if (loadProperties == null) {
            if (env == null) {
                loadProperties = new PropertiesReaderClassLoader("config.properties");
            } else {
                loadProperties = new PropertiesReaderClassLoader("env/config." + env + ".properties");
            }
        }
        return loadProperties;
    }

    public static PropertiesReaderClassLoader getInstance() {
        return getInstance(null);
    }

    public String getValueFromProperty(String key) {
        if (properties == null) {
            properties = new Properties();
            try {
                properties.load(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return properties.getProperty(key);
    }
}
