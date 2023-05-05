package com.backend.uujob.service;

import com.backend.uujob.entity.Message;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IMessageService extends IService<Message> {
    List<Message> getListByConversationId(int conversationId);
}
