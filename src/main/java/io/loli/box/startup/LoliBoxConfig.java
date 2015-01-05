package io.loli.box.startup;

import io.loli.box.service.StorageService;
import io.loli.box.service.impl.FileSystemStorageService;

import java.io.File;
import java.util.Calendar;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import org.apache.commons.lang3.StringUtils;

/**
 * Config class of application
 * 
 * @author choco
 *
 */
public class LoliBoxConfig {
    /**
     * Where images saved
     */
    private String savePath;

    /**
     * Admin email
     */
    private String email;

    private StorageService service = null;

    /**
     * Default port
     */
    private String port = "8888";

    /**
     * Default host
     */
    private String address = "0.0.0.0";

    /**
     * Read config params from command line
     * 
     * @param args command line args
     */
    private void readParams(String[] args) {
        ArgumentParser parser = ArgumentParsers.newArgumentParser("").description("A simple image hosting software");

        parser.addArgument("-p", "--port").metavar("PORT").type(Integer.class).help("Http port to listen on")
            .dest("port");

        parser.addArgument("-a", "--address").metavar("ADDRESS").type(String.class).help("Addresses to listen on")
            .dest("address");

        parser.addArgument("-e", "--email").metavar("EMAIL").type(String.class)
            .help("Admin email to show on the bottom of page").dest("email");

        parser.addArgument("-s", "--save").metavar("SAVE").type(String.class).help("Where images saved").dest("save");

        try {
            Namespace res = parser.parseArgs(args);
            Integer cport = res.getInt("port");
            if (cport != null) {
                port = String.valueOf(cport);
            }

            String caddress = res.getString("address");
            if (caddress != null) {
                address = caddress;
            }

            String cemail = res.getString("email");
            if (cemail != null) {
                String email = String.valueOf(cemail);
                this.setEmail(email);
            }

            String cpath = res.getString("save");
            if (cpath != null) {
                String savePath = String.valueOf(cpath);
                this.setSavePath(savePath);
            }

        } catch (ArgumentParserException e) {
            parser.handleError(e);
        }

    }

    private LoliBoxConfig(String[] args) {
        readParams(args);

        if (StringUtils.isNotBlank(port)) {
        } else {
            port = "8888";
        }

        if (StringUtils.isNotBlank(address)) {
        } else {
            address = "0.0.0.0";
        }

        if (StringUtils.isNotBlank(email)) {
        } else {
        }

        if (StringUtils.isNotBlank(savePath)) {
            setSavePath(savePath);
        } else {
            setSavePath(System.getProperty("user.home") + File.separator + "lolibox");
        }
        service = new FileSystemStorageService();
    }

    public String getSavePath() {
        return savePath;
    }

    /**
     * Get folder for today and will create it if not exist
     * 
     * @return generated date folder
     */
    public String getCurrentSaveDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);

        // I don't known why calendar start with 0
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        String monthStr = String.valueOf(month);
        if(month < 10){
            monthStr = "0" + monthStr;
        }
        String dayStr = String.valueOf(day);
        if(day < 10){
            dayStr = "0" + dayStr;
        }
        String path = year + File.separator + monthStr + File.separator + dayStr;
        
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
        File file = new File(savePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        this.savePath = savePath;
    }

    private static LoliBoxConfig config = null;

    public static LoliBoxConfig getInstance(String[] args) {
        if (config == null) {
            config = new LoliBoxConfig(args);
        }
        return config;
    }

    public static LoliBoxConfig getInstance() {
        return getInstance(new String[0]);
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
