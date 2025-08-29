package com.mashinakomplekt.mysystem.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadUtil {

    public static String saveFile(String fileName, MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get("PDFs");

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        //generate unque string
        String fileCode = RandomStringUtils.randomAlphanumeric(16);
        Path filePath = null;

        try(InputStream inputStream = multipartFile.getInputStream()) {
            filePath = uploadPath.resolve(fileCode + "-" + fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new IOException("File could not be saved");
        }

        return filePath.toString();
    }
}
