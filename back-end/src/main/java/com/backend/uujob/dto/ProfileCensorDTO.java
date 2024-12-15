package com.backend.uujob.dto;

import com.backend.uujob.vo.ProfileVO;
import lombok.Data;

import java.util.List;

@Data
public class ProfileCensorDTO {
    private List<ProfileVO> subList;
    private List<ProfileVO> passList;
}
