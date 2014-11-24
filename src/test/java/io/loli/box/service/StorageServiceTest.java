package io.loli.box.service;

import static org.junit.Assert.assertNotNull;
import io.loli.box.service.impl.FileSystemStorageService;

import java.io.IOException;

import org.junit.Test;

public class StorageServiceTest {

    @Test
    public void testFileSystemUpload() throws IOException {
        StorageService service = new FileSystemStorageService();
        String url = service.upload(StorageServiceTest.class.getResourceAsStream("/test.jpg"), "test.jpg");
        assertNotNull(url);
    }
}
