package com.backend.uujob.controller.dto;

import lombok.Data;

@Data
public class ConversationDTO {
    private Integer id;
    private Integer senderId;
    private Integer receiverId;
    private String senderName;
    private String receiverName;
    private String lastMessage;
    private String lastMessageTime;
    private Integer isRead;
}
