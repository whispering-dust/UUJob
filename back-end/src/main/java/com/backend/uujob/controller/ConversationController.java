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
        Conversation c = conversationService.getByTwoUserId(conversation.getSenderId(), conversation.getReceiverId());  //先判断由此两人创建的对话是否存在
        if(c != null){  //若存在则直接返回其id，否则将其添加至数据库中
            return Result.success(c.getId());
        }
        conversationService.save(conversation);
        return Result.success(conversation.getId());
    }

    @PostMapping("")
    public Result getConversation(@RequestBody Conversation conversation){
        Conversation c = conversationService.getByTwoUserId(conversation.getSenderId(), conversation.getReceiverId());  //先判断由此两人创建的对话是否存在
        if(c == null){  //如果会话不存在，则返回error
            return Result.error(500,"信息不存在");
        }
        return Result.success(c.getId());
    }

    @GetMapping("/basis")
    public Result getConversationBasisList(@RequestParam int userId){
        List<Conversation> conversationList = conversationService.getByOneUserId(userId); //获取所有符合条件的聊天室

        List<ConversationDTO> resList = new ArrayList<>();

        for(Conversation c : conversationList){
            ConversationDTO conversationDTO = new ConversationDTO();

            //添加基本信息
            conversationDTO.setId(c.getId());
            if(c.getLastMessage() != null){  //仅当最新信息不为null，才进行处理
                conversationDTO.setLastMessage(c.getLastMessage());
                conversationDTO.setLastMessageTime(timeTransfer(c.getLastMessageTime()));
            }
            if(userId == c.getSenderId()){  //判断哪个id是聊天对象的id
                conversationDTO.setContactId(c.getReceiverId());
                conversationDTO.setContactName(userService.getNameById(c.getReceiverId()));
            }
            else {
                conversationDTO.setContactId(c.getSenderId());
                conversationDTO.setContactName(userService.getNameById(c.getSenderId()));
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

        //将所属聊天室的最新消息设置为当前消息
        Conversation conversation = conversationService.getById(message.getConversationId());
        conversation.setLastMessage(message.getContent());
        conversation.setLastMessageTime(message.getCreatedAt());
        conversationService.updateById(conversation);

        messageService.save(message);
        return Result.success(message.getId());
    }

    @PutMapping("/messages")
    public Result changeMessage(@RequestBody Message message){
        messageService.updateById(message);
        return Result.success();
    }



}
