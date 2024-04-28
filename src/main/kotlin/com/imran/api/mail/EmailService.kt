package com.imran.api.mail

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class EmailService(private val mailSender: JavaMailSender) {
    fun sendEmail(to: String, subject: String, body: String) {
        mailSender.send(
            SimpleMailMessage().apply {
                setTo(to)
                setSubject(subject)
                text = body
            }
        )
    }
}