package com.backend.uujob.service;

import com.backend.uujob.entity.Conversation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IConversationService extends IService<Conversation> {
    List<Conversation> getByUserId(int userId);
}
