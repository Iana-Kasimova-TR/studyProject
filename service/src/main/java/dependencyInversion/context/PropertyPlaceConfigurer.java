package dependencyInversion.context;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by anakasimova on 23/08/2018.
 */
public class PropertyPlaceConfigurer {

    private String properties;

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    public String getProperty(String property) throws IOException{
        File file = new File(properties);
        Properties properties = new Properties();
        properties.load(new FileInputStream(file));
        return properties.getProperty(property);
    }
}
