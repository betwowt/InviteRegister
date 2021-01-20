package com.betwowt.inviteRegister.utils;

import org.apache.commons.lang.StringUtils;

public class EmailUtils {

    public static boolean validate(String email){
        if (StringUtils.isBlank(email)) {
            return false;
        }
        String pattern = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
        return email.matches(pattern);
    }

}
