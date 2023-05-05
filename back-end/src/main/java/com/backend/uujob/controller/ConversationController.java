package com.backend.uujob.controller;

import com.backend.uujob.controller.dto.ConversationDTO;
import com.backend.uujob.entity.Conversation;
import com.backend.uujob.entity.Message;
import com.backend.uujob.enums.MessageStautsEnum;
import com.backend.uujob.result.Result;
import com.backend.uujob.service.IConversationService;
import com.backend.uujob.service.IMessageService;
import com.backend.uujob.service.IUserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.backend.uujob.utils.TimeUtils.timeTransfer;

@RestController
@RequestMapping("/conversations")
public class ConversationController {
    @Resource
    private IUserService userService;
    @Resource
    private IMessageService messageService;
    @Resource
    private IConversationService conversationService;

    @PostMapping("")
    public Result addConversation(@RequestBody Conversation conversation){
        conversationService.save(conversation);
        return Result.success(conversation.getId());
    }

    @GetMapping("/basis")
    public Result getConversationBasisList(@RequestParam int userId){
        List<Conversation> conversationList = conversationService.getByUserId(userId); //获取所有符合条件的聊天室

        List<ConversationDTO> resList = new ArrayList<>();

        for(Conversation c : conversationList){
            ConversationDTO conversationDTO = new ConversationDTO();
            List<Message> m = messageService.getListByConversationId(c.getId());  //获取该聊天室内的最新消息

            conversationDTO.setId(c.getId());
            conversationDTO.setSenderId(c.getSenderId());
            conversationDTO.setReceiverId(c.getReceiverId());
            conversationDTO.setSenderName(userService.getNameById(c.getSenderId()));
            conversationDTO.setReceiverName(userService.getNameById(c.getReceiverId()));
            if(!m.isEmpty()){ //如果最新消息存在，则进行赋值
                conversationDTO.setLastMessage(m.get(0).getContent());
                conversationDTO.setLastMessageTime(timeTransfer(m.get(0).getCreatedAt()));
                conversationDTO.setIsRead(m.get(0).getStatus());
            }

            resList.add(conversationDTO);
        }

        return Result.success(resList);
    }

    @GetMapping("/messages")
    public Result getMessageList(@RequestParam int conversationId){
        return Result.success(messageService.getListByConversationId(conversationId));
    }

    @PostMapping("/messages")
    public Result addMessage(@RequestBody Message message){
        //设置为未读和当前时间
        message.setStatus(MessageStautsEnum.MESSAGE_STAUTS_UNREAD.ordinal());
        message.setCreatedAt(new java.sql.Timestamp(System.currentTimeMillis()));

        //将所属聊天室的最新

        messageService.save(message);
        return Result.success(message.getId());
    }

    @PutMapping("/messages")
    public Result changeMessage(@RequestBody Message message){
        messageService.updateById(message);
        return Result.success();
    }



}
