package com.minble.client.util;

import java.util.Random;

public class CreateNickname {

    public String randomNickname() {
        int leftLimit = 48; // 숫자 0
        int rightLimit = 122; // 문 'z'
        int length = 8;
        Random random = new Random();
        String randomNickname = random.ints(leftLimit, rightLimit + 1)
                                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >=97))
                                .limit(length)
                                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                                .toString();

        return randomNickname;
    }
    
}
