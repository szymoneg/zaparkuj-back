package com.zaparkuj.demo.dto;

import lombok.*;

import javax.validation.ValidationException;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MessageDTO {
    String message;

    public MessageDTO(ValidationException exception) {
        String messageTemplate = "messageTemplate='";
        int position = exception.getMessage().indexOf(messageTemplate);
        String message = exception.getMessage().substring(position + messageTemplate.length());
        position = message.indexOf('\'');
        message = message.substring(0, position);
        this.message = message;
    }
}
