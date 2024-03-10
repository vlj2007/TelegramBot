package pro.sky.telegrambot.cron;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.service.NotificationTaskService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class NotificationCron {

    private final NotificationTaskService notificationTaskService;
    private final TelegramBot telegramBot;

    public NotificationCron(NotificationTaskService notificationTaskService, TelegramBot telegramBot) {
        this.notificationTaskService = notificationTaskService;
        this.telegramBot = telegramBot;
    }

    @Scheduled(cron = "0 0/1 * * * *")
    public void task() {
        notificationTaskService.findByLocalDateTime(
                        LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES))
                .forEach(notificationTask -> {
                    telegramBot.execute(new SendMessage(notificationTask.getChatId(),
                            notificationTask.getText()));
                    notificationTaskService.delete(notificationTask);
                });
    }

}
















