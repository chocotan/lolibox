package io.loli.box.startup;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

public class ConfigLoader {

    private static Logger logger = Logger.getLogger(ConfigLoader.class.getName());

    private static Properties prop = null;

    public static Properties getProp() {
        return prop;
    }

    static {
        reload();
    }

    public static Properties reload() {
        if (prop == null) {
            prop = new Properties();
        }
        logger.info("Ready to load config.properties");
        try {
            prop.load(LoliBoxConfig.class.getResourceAsStream("/config.properties"));
        } catch (IOException e) {
            logger.warning("config.properties not found in classpath. You need to create it in classpath\n"
                + "And now default properties will be used");
        }
        return getProp();
    }

}
