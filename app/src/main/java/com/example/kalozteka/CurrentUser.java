package com.example.kalozteka;

import com.example.kalozteka.models.UserModel;

public class CurrentUser {
    private static UserModel user;

    public static void setUser(UserModel u) {
        user = u;
    }

    public static UserModel getUser() {
        return user;
    }

    public static void clear() {
        user = null;
    }
}
