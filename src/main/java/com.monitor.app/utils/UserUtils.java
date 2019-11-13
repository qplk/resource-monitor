package com.monitor.app.utils;

import com.monitor.app.entity.User;

public class UserUtils {

    public static String concatFirstAndSecondNames(User user) {
        return user.getFirstName() + " " + user.getSecondName();
    }
}
