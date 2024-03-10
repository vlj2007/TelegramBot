package pro.sky.telegrambot.service;

import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationTaskService {

    Logger logger = LoggerFactory.getLogger(NotificationTaskService.class);

    private final NotificationTaskRepository notificationTaskRepository;

    public NotificationTaskService(NotificationTaskRepository notificationTaskRepository) {
        this.notificationTaskRepository = notificationTaskRepository;
    }

    public List<NotificationTask> findByLocalDateTime(LocalDateTime localDateTime){
        logger.info("Was invoked method for findByLocalDateTime");
        return notificationTaskRepository.findAllByLocalDateTime(localDateTime);
    }

    public NotificationTask save(NotificationTask notificationTask){
        logger.info("Was invoked method for save notificationTask");
        return notificationTaskRepository.save(notificationTask);
    }

    public void delete(NotificationTask notificationTask){
        logger.info("Was invoked method for delete notificationTask");
        notificationTaskRepository.delete(notificationTask);
    }

}
