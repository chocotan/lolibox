package io.loli.box.controller;

import io.loli.box.dao.IdSeqRepository;
import io.loli.box.dao.ImgFileRepository;
import io.loli.box.dao.ImgFolderRepository;
import io.loli.box.entity.IdSeq;
import io.loli.box.entity.ImgFile;
import io.loli.box.entity.ImgFolder;
import io.loli.box.service.StorageService;
import io.loli.box.util.FileUtil;
import io.loli.box.util.StatusBean;
import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Date;

/**
 * @author choco
 */
@Controller
@RequestMapping("/image")
public class ImageController {
    @Autowired
    private StorageService service;

    @Autowired
    private IdSeqRepository idSeqRepository;

    @Autowired
    private ImgFileRepository imgFileRepository;

    @Autowired
    private ImgFolderRepository imgFolderRepository;

    @Autowired
    private Hashids hashids;

    @Autowired
    private JmsTemplate jmsTemplate;

    @RequestMapping("/upload")
    @ResponseBody
    public StatusBean upload(@RequestParam(value = "image", required = true) MultipartFile imageFile) {

        String url;
        String originName = imageFile.getOriginalFilename();
        String suffix = FileUtil.getSuffix(originName);

        try (InputStream is = new BufferedInputStream(imageFile.getInputStream())) {
            Long id = idSeqRepository.save(new IdSeq()).getId();
            String name = hashids.encode(id) + ".".concat(suffix);
            url = service.upload(is, name, imageFile.getContentType(), imageFile.getSize());
            ImgFile file = new ImgFile();
            file.setCreateDate(new Date());
            file.setId(id);
            file.setOriginName(originName);
            file.setShortName(url);
            ImgFolder folder = imgFolderRepository.getCurrentFolder();
            folder.setImgCount(folder.getImgCount() + 1);
            imgFolderRepository.save(folder);
            file.setFolder(folder);
            file.setSize(imageFile.getSize());
            imgFileRepository.save(file);
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
