package com.example.shuttlematch.service.impl;

import com.example.shuttlematch.entity.User;
import com.example.shuttlematch.service.IMailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MailService implements IMailService {

    private final JavaMailSender mailSender;
    @Async
    @Override
    public void sendUserForResetPassword(User user, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("\"Shuttle-Match\" <shuttle.match@gmail.com>");
        message.setTo(user.getEmail());
        // Set a meaningful message
        message.setSubject("[Shuttle-Match] - Đăt lại mật khẩu");
        String body = "Kính gửi " + user.getFullName() + ",\n\n" +
                      "Mật khẩu tạm thời: " + password + "\n\n" +
                      "Để bảo mật tài khoản của bạn, vui lòng đăng nhập và đổi mật khẩu ngay sau khi nhận được email này.\n\n" +
                      "Trân trọng,\n\n" +
                      "Đội ngũ Shuttle-Match";
        message.setText(body);
        // Send the email (assuming you have a mailSender bean configured)
        mailSender.send(message);
    }
}
