package io.loli.box.startup;

import io.loli.box.service.StorageService;
import io.loli.box.service.impl.FileSystemStorageService;

import java.io.File;
import java.util.Properties;
import java.util.logging.Logger;

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
    private static Logger log = Logger.getLogger(LoliBoxConfig.class.getName());

    private String savePath;

    private Properties prop = null;

    private StorageService service = null;

    // Get host and port from property file
    {
        prop = ConfigLoader.getProp();

        String savePathProperty = prop.getProperty("file.folder");
        if (StringUtils.isNoneBlank(savePathProperty)) {
            savePath = savePathProperty;
        } else {
            savePath = System.getProperty("user.home") + File.separator + "lolibox";
            File pathFile = new File(savePath);
            if (!pathFile.exists()) {
                pathFile.mkdir();
            } else {
                if (pathFile.isFile()) {
                    log.warning(savePath
                        + " is a file instead of a dir, you must use a dir in config.properties. lolibox will exit.");
                }
            }
        }

        service = new FileSystemStorageService();
    }

    // Get property value by its key
    public String getString(String key) {
        return prop.getProperty(key);
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

    public StorageService getService() {
        return service;
    }

    public void setService(StorageService service) {
        this.service = service;
    }
}
