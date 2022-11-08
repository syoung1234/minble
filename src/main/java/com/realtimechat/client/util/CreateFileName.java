package com.realtimechat.client.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class CreateFileName {
    private String result;

    public CreateFileName() {
        result = new SimpleDateFormat("yyyyMMdd").format(new Date()).toString() + UUID.randomUUID().toString();

    }

    public String toString() {
        return result;
    }

}
