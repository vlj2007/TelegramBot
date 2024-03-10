package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.service.NotificationTaskService;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot telegramBot;
    private String greetings = "hello";
    private NotificationTaskService notificationTaskService;

    private static final Pattern PATTERN = Pattern.compile("([0-9\\.\\:\\s]{16})(\\s)([\\W+]+)");

    public TelegramBotUpdatesListener(TelegramBot telegramBot, NotificationTaskService notificationTaskService) {
        this.telegramBot = telegramBot;
        this.notificationTaskService = notificationTaskService;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            String text = update.message().text();
            long chatId = update.message().chat().id();
            Matcher matcher = PATTERN.matcher(text);

            if (text.equals("/start")) {
                logger.info("Was invoked command /start");
                telegramBot.execute(new SendMessage(
                        chatId,
                        greetings
                                + " "
                                + update.message().chat().firstName()
                                + ". "
                                + "This is a bot."));

            } else if (matcher.matches()) {
                String messages = matcher.group(3);
                LocalDateTime localDateTime = LocalDateTime.parse(matcher.group(1),
                        DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
                if (messages == null) {
                    logger.info("Was check for null");
                    telegramBot.execute(new SendMessage(chatId, "It is not correspond established DateTime format."));
                    return;
                }

                NotificationTask task = new NotificationTask();
                task.setChatId(chatId);
                task.setText(messages);
                task.setLocalDateTime(localDateTime);
                NotificationTask saved = notificationTaskService.save(task);
                telegramBot.execute(new SendMessage(chatId, "Your task is scheduled"));
                logger.info("Notification task saved {}", saved);
            } else {
                telegramBot.execute(new SendMessage(chatId, "Task is not scheduled"));
                logger.info("Notification task saved {}", chatId);
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

}