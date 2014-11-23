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

    private String email;

    private Properties prop = null;

    private StorageService service = null;

    // Get host and port from property file
    {
        prop = ConfigLoader.getProp();

        email = prop.getProperty("admin.email");
        if (StringUtils.isNotBlank(email)) {
        } else {
            email = System.getenv("LOLIBOX_ADMIN_EMAIL");
            if (StringUtils.isNotBlank(email)) {
            } else {
                log.warning("你还没有设置你的邮箱");
            }
        }

        String savePathProperty = prop.getProperty("file.folder");
        if (StringUtils.isNotBlank(savePathProperty)) {
            savePath = savePathProperty;
        } else {
            String savePathEnv = System.getenv("LOLIBOX_SAVE_PATH");
            if (StringUtils.isNotBlank(savePathEnv)) {
                savePath = savePathEnv;
            } else {
                savePath = System.getProperty("user.home") + File.separator + "lolibox";
                log.warning("你没有指定一个图片保存路径，将使用" + savePath + "作为图片保存路径");
            }

            File pathFile = new File(savePath);
            if (!pathFile.exists()) {
                pathFile.mkdir();
            } else {
                if (pathFile.isFile()) {
                    log.warning(savePath + " 不是一个正确的文件夹，请检查");
                } else {
                    if (!pathFile.canWrite()) {
                        log.warning(savePath + "不可写");
                    }
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

    public String getEmail() {
        if (email == null)
            return "";
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
