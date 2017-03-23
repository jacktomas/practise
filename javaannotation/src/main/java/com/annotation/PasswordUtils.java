package com.annotation;

import java.util.List;

/**
 * Created by root on 17-3-23.
 */
public class PasswordUtils {
    @UseCase(id = 47, description = "password must contain at least one numeric")
    public boolean validatePassword(String password) {
        return (password.matches("\\w*\\d\\w*"));
    }

    @UseCase(id = 48)
    public String encryptPassword(String password) {
        return new StringBuilder(password).reverse().toString();
    }

    @UseCase(id = 49, description = "New passwords can't equal previously used ones")
    public boolean checkForNewPassword(List<String> prevPassword, String password) {
        return !prevPassword.contains(password);
    }
}
