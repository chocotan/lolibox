package io.loli.box.service.impl;

import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.model.ListObjectsRequest;
import com.aliyun.openservices.oss.model.ObjectListing;
import com.aliyun.openservices.oss.model.ObjectMetadata;
import io.loli.box.service.AbstractStorageService;
import io.loli.box.util.FileBean;
import io.loli.box.util.FileUtil;
import io.loli.box.util.SuffixBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author choco
 */
public class AliStorageService extends AbstractStorageService {

    @Value("${storage.aliyun.url}")
    private String url;
    @Value("${storage.aliyun.key}")
    private String key;
    @Value("${storage.aliyun.secret}")
    private String secret;
    @Value("${storage.aliyun.name}")
    private String name;

    private String seperator = "/";
    private OSSClient client = null;

    @PostConstruct
    public void init() {
        client = new OSSClient(url, key,
                secret);
    }

    private static List<SuffixBean> suffixes = new ArrayList<SuffixBean>();

    static {
        suffixes.add(new SuffixBean("jpg"));
        suffixes.add(new SuffixBean("jpeg"));
        suffixes.add(new SuffixBean("gif"));
        suffixes.add(new SuffixBean("png"));
        suffixes.add(new SuffixBean("bmp"));
        suffixes.add(new SuffixBean("JPG"));
        suffixes.add(new SuffixBean("JPEG"));
        suffixes.add(new SuffixBean("GIF"));
        suffixes.add(new SuffixBean("PNG"));
        suffixes.add(new SuffixBean("BMP"));
    }

    @Override
    public String upload(InputStream is, String filename, String contentType, long length) throws IOException {
        String suffix = FileUtil.getSuffix(filename);
        if (!suffixes.contains(new SuffixBean(suffix))) {
            throw new IllegalArgumentException("File you uploaded is not an image.");
        }
        filename = FileUtil.getFileName() + (suffix.equals("") ? "" : "." + suffix);

        String folder = this.getCurrentFolder();
        filename = folder + seperator + filename;
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentLength(length);
        meta.setContentType(contentType);
        meta.setCacheControl("max-age: " + 3600 * 1000 * 48);
        meta.setContentEncoding("UTF-8");
        meta.setLastModified(new Date());
        client.putObject(name, filename, new BufferedInputStream(is), meta);
        return filename;
    }

    private String getCurrentFolder() {

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);

        // I don't known why calendar start with 0
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        String monthStr = String.valueOf(month);
        if (month < 10) {
            monthStr = "0" + monthStr;
        }
        String dayStr = String.valueOf(day);
        if (day < 10) {
            dayStr = "0" + dayStr;
        }
        String path = year + seperator + monthStr + seperator
                + dayStr;
        return path;
    }


    @Override
    public List<FileBean> getYears() {
        return getFilesByPrefix("/");
    }

    @Override
    public List<FileBean> getMonthsByYear(String year) {
        return getFilesByPrefix(year);
    }


    private List<FileBean> getFilesByPrefix(String filename) {
        if (StringUtils.isBlank(filename)) {
            filename = "";
        }
        ListObjectsRequest request = new ListObjectsRequest(name);
        request.setDelimiter(seperator);
        request.setPrefix(filename);
        ObjectListing list = client.listObjects(request);
        list.setMaxKeys(5000);
        List<FileBean> beans = new ArrayList<>();
        beans.addAll(list.getObjectSummaries().stream().map(ossObjectSummary -> {
            FileBean bean = new FileBean();
            bean.setFile(true);
            bean.setSize(ossObjectSummary.getSize());
            String name = ossObjectSummary.getKey();
            name = name.substring(name.lastIndexOf(seperator) + 1);
            bean.setName(name);
            bean.setLastModified(ossObjectSummary.getLastModified());
            return bean;
        }).collect(Collectors.toList()));
        beans.addAll(list.getCommonPrefixes().stream().map(prefix -> {
            FileBean bean = new FileBean();
            bean.setFile(false);
            prefix = prefix.substring(0, prefix.lastIndexOf(seperator));
            prefix = prefix.substring(prefix.lastIndexOf(seperator) + 1);
            bean.setName(prefix);
            return bean;
        }).collect(Collectors.toList()));
        return beans;
    }

    @Override
    public List<FileBean> getDaysByMonth(String year, String month) {
        String name = year + seperator + month;
        return this.getFilesByPrefix(name);
    }

    @Override
    public List<FileBean> getFilesByDay(String year, String month, String day) {
        String name = "";
        if (StringUtils.isNotBlank(year)) {
            name = year + "/";
        }
        if (StringUtils.isNotBlank(month)) {
            name = name + month + "/";
        }
        if (StringUtils.isNotBlank(day)) {
            name = name + day + "/";
        }

        return this.getFilesByPrefix(name);
    }

    @Override
    public void deleteFile(String year, String month, String day, String name) {
        String key = year + seperator + month + seperator + day + seperator + name;
        client.deleteObject(name, key);
    }
}
