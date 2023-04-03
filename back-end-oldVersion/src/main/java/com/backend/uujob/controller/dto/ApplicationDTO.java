package com.backend.uujob.controller.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ApplicationDTO {
    private int resume_id;
    private int job_id;
    private Date date;

    public ApplicationDTO(int resume_id, int job_id, Date date) {
        this.resume_id = resume_id;
        this.job_id = job_id;
        this.date = date;
    }
}
