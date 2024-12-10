package com.example.shuttlematch.service;

import com.example.shuttlematch.entity.User;

public interface IMailService {
    void sendUserForResetPassword(User user, String password);
}
