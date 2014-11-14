package io.loli.box.startup;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by 叶 on 2014/11/14.
 */
public class LoliBoxConfig {
    public String address = "0.0.0.0";
    public String port = "8888";
    public String url = "http://" + address + ":" + port + "/";

    private static Logger logger = Logger.getLogger(LoliBoxConfig.class.getName());

    private Properties prop = null;

    // Init property
    {
        if (prop == null) {
            prop = new Properties();
        }
        try {
            prop.load(LoliBoxConfig.class.getResourceAsStream("/config.properties"));
            logger.info("Reading config.properties...");
            String addressInProperty = prop.getProperty("site.host");
            if (StringUtils.isNoneBlank(addressInProperty)) {
                address = addressInProperty;
            }

            String portInProperty = prop.getProperty("site.port");
            if (StringUtils.isNoneBlank(addressInProperty)) {
                port = portInProperty;
            }

            url = getURIString();

        } catch (IOException e) {
            logger.warning("config.properties not found in classpath. You need to create it in classpath\n"
                + "And now default properties will be used");
        }

    }

    // Get URL String using address and port
    private String getURIString() {
        return "http://" + address + ":" + port + "/";
    }

    // Get property value by its key
    public String getString(String key) {
        return prop.getProperty(key);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
