package io.loli.box.startup;

import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 * Config class of config.properties.
 * 
 * <p>
 * Reload the latest properties from config file
 * 
 * <pre>
 * <code>
 * LoliBoxConfig config = LoliBoxConfig.newInstance();
 * </code>
 * </pre>
 * 
 * Get last used config object if you do not want to reload from config file
 * 
 * <pre>
 * <code>
 * LoliBoxConfig config = LoliBoxConfig.getInstance();
 * </code>
 * </pre>
 */
public class LoliBoxConfig {
    public String address = "0.0.0.0";
    public String port = "8888";
    public String url = "http://" + address + ":" + port + "/";

    private String savePath;

    private Properties prop = null;

    // Get host and port from property file
    {
        prop = ConfigLoader.getProp();
        String addressInProperty = prop.getProperty("site.host");
        if (StringUtils.isNoneBlank(addressInProperty)) {
            address = addressInProperty;
        }

        String portInProperty = prop.getProperty("site.port");
        if (StringUtils.isNoneBlank(addressInProperty)) {
            port = portInProperty;
        }

        String savePathProperty = prop.getProperty("file.folder");
        if (StringUtils.isNoneBlank(savePathProperty)) {
            savePath = savePathProperty;
        }

        url = getURIString();

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

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    private static LoliBoxConfig config = new LoliBoxConfig();

    public static LoliBoxConfig getInstance() {
        return config;
    }

    public static LoliBoxConfig newInstance() {
        config = new LoliBoxConfig();
        return config;
    }
}
