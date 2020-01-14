package com.monitor.app.utils;

import com.monitor.app.entity.User;

public class UserUtils {

    private UserUtils(){}

    public static String concatFirstAndSecondNames(User user) {
        if (user == null ) {
            return "";
        }
        return user.getFirstName() + " " + user.getSecondName();
    }
}
