package com.backend.uujob.service.impl;

import com.backend.uujob.entity.Conversation;
import com.backend.uujob.mapper.ConversationMapper;
import com.backend.uujob.service.IConversationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class ConversationService extends ServiceImpl<ConversationMapper, Conversation> implements IConversationService {
    @Resource
    private ConversationMapper conversationMapper;
}
