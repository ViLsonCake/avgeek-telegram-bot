package project.vilsoncake.telegrambot.service;

public interface MailService {
    boolean sendMessage(String recipient, String subject, String text);
}
