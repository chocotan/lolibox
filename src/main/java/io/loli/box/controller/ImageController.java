package io.loli.box.controller;

import io.loli.box.service.StorageService;
import io.loli.box.util.StatusBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.InputStream;

/**
 * @author choco
 */
@Controller
@RequestMapping("/image")
public class ImageController {
    @Autowired
    private StorageService service;

    @RequestMapping("/upload")
    @ResponseBody
    public StatusBean upload(@RequestParam(value = "image", required = true) MultipartFile imageFile) {

        String url;
        try (InputStream is = new BufferedInputStream(imageFile.getInputStream())) {
            url = service.upload(is, imageFile.getOriginalFilename(), imageFile.getContentType(), imageFile.getSize());
        } catch (Exception e) {
            e.printStackTrace();
            return new StatusBean("error", "Error:" + e.getMessage());
        }
        if (url != null) {
            return new StatusBean("success", "images/" + url);
        } else {
            return new StatusBean("error", "Failed to upload");
        }
    }
}
