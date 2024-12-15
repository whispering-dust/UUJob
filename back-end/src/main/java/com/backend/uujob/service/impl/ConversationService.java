package com.backend.uujob.service.impl;

import com.backend.uujob.entity.Conversation;
import com.backend.uujob.mapper.ConversationMapper;
import com.backend.uujob.service.IConversationService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class ConversationService extends ServiceImpl<ConversationMapper, Conversation> implements IConversationService {
    @Override
    public List<Conversation> getByOneUserId(int userId) {
        QueryWrapper<Conversation> wrapper = new QueryWrapper<>();
        wrapper.eq("sender_id", userId)
                .or()
                .eq("receiver_id", userId)
                .orderByDesc("last_message_time");
        
        return list(wrapper);
    }
    
    @Override
    public Conversation getByTwoUserId(int senderId, int receiverId) {
        QueryWrapper<Conversation> wrapper = new QueryWrapper<>();
        wrapper.eq("sender_id", senderId)
                .eq("receiver_id", receiverId)
                .or()
                .eq("sender_id", receiverId)
                .eq("receiver_id", senderId);
        
        return getOne(wrapper);
    }
}
