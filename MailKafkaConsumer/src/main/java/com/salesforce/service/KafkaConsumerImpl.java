package com.salesforce.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.salesforce.bean.NotificationMessage;

import jakarta.mail.MessagingException;

@Service
public class KafkaConsumerImpl {

	@Autowired
	private MailSenderService mailSenderService;

	@KafkaListener(topics = "emailTopics", groupId = "Campaign", containerFactory = "actionListener")
	public void listen(NotificationMessage message) throws MessagingException, IOException {
		System.out.println("message : " + message);
		mailSenderService.sendHtmlEmail(message.getSendTo(), "Campaign-Advisor-Pro");
	}

}
