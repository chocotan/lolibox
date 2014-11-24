package io.loli.box.startup;

import io.loli.box.service.StorageService;
import io.loli.box.service.impl.FileSystemStorageService;

import java.io.File;
import java.util.Calendar;
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

    private String port = "8888";
    private String address = "0.0.0.0";

    // Get host and port from property file
    {
        prop = ConfigLoader.getProp();

        port = prop.getProperty("port");
        if (StringUtils.isNotBlank(port)) {
        } else {
            port = "8888";
        }

        address = prop.getProperty("address");
        if (StringUtils.isNotBlank(address)) {
        } else {
            address = "0.0.0.0";
        }

        email = prop.getProperty("admin.email");
        if (StringUtils.isNotBlank(email)) {
        } else {
            email = System.getenv("LOLIBOX_ADMIN_EMAIL");
            if (StringUtils.isNotBlank(email)) {
            } else {
                log.warning("你还没有设置邮箱");
            }
        }

        String savePathProperty = prop.getProperty("file.folder");
        if (StringUtils.isNotBlank(savePathProperty)) {
            savePath = savePathProperty;
        } else {
            savePath = System.getProperty("user.home") + File.separator + "lolibox";
            log.warning("你没有指定一个图片保存路径，将使用" + savePath + "作为图片保存路径");

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

    public String getCurrentSaveDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        String path = year + File.separator + month + File.separator + day;
        String savePath = getSavePath();
        if (savePath.endsWith(File.separator)) {
        } else {
            savePath += File.separator;
        }
        savePath += path;
        File file = new File(savePath);
        if (!file.exists()) {
            file.mkdirs();
        }

        return path;
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

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
