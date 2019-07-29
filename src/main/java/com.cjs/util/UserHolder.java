package com.cjs.util;

import com.cjs.bean.User;

public class UserHolder {
    private static ThreadLocal<User> users = new ThreadLocal<>();

    public static void addCurrentUser(User user) {
        if (users.get() == null) {
            users.set(user);
        }
    }

    public static User getCurrentUser() {
        return users.get();
    }

    public static void removeCurrentUser() {
        users.remove();
    }
}
