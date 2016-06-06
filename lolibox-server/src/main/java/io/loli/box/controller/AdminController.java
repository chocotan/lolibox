package io.loli.box.controller;

import io.loli.box.service.StorageService;
import io.loli.box.util.FileBean;
import io.loli.box.util.StatusBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author choco
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private StorageService ss;

    private static Comparator<FileBean> fileComparator = new Comparator<FileBean>() {

        @Override
        public int compare(FileBean o1, FileBean o2) {
            try {
                if (o1.getLastModified().equals(o2.getLastModified())) {
                    return 0;
                }
                return o1.getLastModified().after(o2.getLastModified()) ? -1 : 1;
            } catch (Exception e) {
                return -1;
            }
        }





    };

    @RequestMapping("/list")
    @ResponseBody
    public List<FileBean> list(@RequestParam(value = "year", required = false) String year,
                               @RequestParam(value = "month", required = false) String month,
                               @RequestParam(value = "day", required = false) String day) {
        List<FileBean> files = ss.getFilesByDay(year, month, day);
        Collections.sort(files, fileComparator);
        return files;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public StatusBean delete(@RequestParam(value = "year") String year,
                             @RequestParam(value = "month") String month,
                             @RequestParam(value = "day") String day,
                             @RequestParam(value = "name") String name) {
        try {
            ss.deleteFile(name);
        } catch (Exception e) {
            return new StatusBean("error", e.getMessage());
        }
        return new StatusBean("success", "成功删除文件");

    }
}
