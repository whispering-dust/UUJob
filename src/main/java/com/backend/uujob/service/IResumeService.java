package com.backend.uujob.service;

import com.backend.uujob.controller.dto.ResumeDTO;
import com.backend.uujob.entity.Application;
import com.backend.uujob.entity.Resume;
import com.baomidou.mybatisplus.extension.service.IService;

import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IResumeService extends IService<Resume> {
    Resume postResume(@RequestBody ResumeDTO resumeDTO);
    Resume getResume(int id);

    List<Resume> getJobResume(List<Application> applicationList);
}
