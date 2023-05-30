package com.backend.uujob.controller;

import com.backend.uujob.result.Result;
import com.backend.uujob.utils.FTPUtil;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



/**
 * @ProjectName SecurityTest
 * @Package com.nwpusct.csal.controller.uploadfileserver
 * @Name NdeUploadFileServer
 * @Author HB
 * @Date 2020/5/7 0007 上午 11:07
 * @Version 1.0  上传文件到文件服务器（Ubuntu文件服务器）
 */
@RestController
@RequestMapping(value = "/files")
public class FileController {

    @PostMapping("/upload")
    public Result uploadFolder(@RequestBody MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            FTPUtil.sshSftp(bytes, file.getOriginalFilename());
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.error();
    }
}

