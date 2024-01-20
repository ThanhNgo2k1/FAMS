package com.group3.fams.service;

import com.group3.fams.entity.EmailDetails;

public interface EmailService {
    public String sendMailtoNewPassword(EmailDetails details);
    public String sendMailtoChangePassword(EmailDetails details);
}
