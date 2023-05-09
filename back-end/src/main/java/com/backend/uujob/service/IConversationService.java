package com.backend.uujob.service;

import com.backend.uujob.entity.Conversation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IConversationService extends IService<Conversation> {
    List<Conversation> getByOneUserId(int userId);

    Conversation getByTwoUserId(int senderId, int receiverId);
}
