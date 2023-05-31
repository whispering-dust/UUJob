package com.backend.uujob.controller.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileUploadDTO {
    private Integer id;
    private MultipartFile file;
}
