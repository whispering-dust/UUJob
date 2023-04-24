package com.backend.uujob.controller.dto;

import com.backend.uujob.entity.VO.ProfileVO;
import lombok.Data;

import java.util.List;

@Data
public class ProfileCensorDTO {
    private List<ProfileVO> subList;
    private List<ProfileVO> passList;
}
