package com.supermarket.backend.Service;

import com.supermarket.backend.Payload.Dto.EmailDetails;

public interface EmailService {
    String sendSimpleMail(EmailDetails details);

//    String sendMailWithAttachment(EmailDetails details);
}
