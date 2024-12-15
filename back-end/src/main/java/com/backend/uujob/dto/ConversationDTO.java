package com.backend.uujob.dto;

import lombok.Data;

@Data
public class ConversationDTO {
    private Integer id;
    private Integer contactId;
    private String contactName;
    private String lastMessage;
    private String lastMessageTime;
}
