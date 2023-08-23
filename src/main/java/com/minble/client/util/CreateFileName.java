package com.minble.client.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class CreateFileName {
    private String result;

    public CreateFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        result = new SimpleDateFormat("yyyyMMdd").format(new Date()).toString() + "_" + UUID.randomUUID().toString() + "." + ext;

    }

    private String extractExt(String originalFilename) {
		int pos = originalFilename.lastIndexOf(".");
		return originalFilename.substring(pos + 1);
	} 

    public String toString() {
        return result;
    }
    

}
