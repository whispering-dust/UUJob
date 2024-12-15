package com.backend.uujob.service.impl;

import com.backend.uujob.entity.Message;
import com.backend.uujob.mapper.MessageMapper;
import com.backend.uujob.service.IMessageService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class MessageService extends ServiceImpl<MessageMapper, Message> implements IMessageService {
    @Override
    public List<Message> getListByConversationId(int conversationId){
        QueryWrapper<Message> wrapper = new QueryWrapper<>();
        wrapper.eq("conversation_id",conversationId)
                .orderByAsc("created_at");
        return list(wrapper);
    }
}
