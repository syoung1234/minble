package com.minble.client.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SubscriberRequestDto {
    private String customer_uid;
    private String nickname;

    public SubscriberRequestDto(String customer_uid, String nickname) {
        this.customer_uid = customer_uid;
        this.nickname = nickname;
    }
    
}
