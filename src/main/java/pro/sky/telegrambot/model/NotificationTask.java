package pro.sky.telegrambot.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tasks")
public class NotificationTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "chat_id")
    private Long chatId;
    @Column(name = "text")
    private String text;
    @Column(name = "date_and_time")
    private LocalDateTime localDateTime;

    public NotificationTask() {
    }

    public NotificationTask(Long id, Long chatId, String text, LocalDateTime localDateTime) {
        this.id = id;
        this.chatId = chatId;
        this.text = text;
        this.localDateTime = localDateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationTask that = (NotificationTask) o;
        return Objects.equals(id, that.id) && Objects.equals(chatId, that.chatId) && Objects.equals(text, that.text) && Objects.equals(localDateTime, that.localDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, text, localDateTime);
    }

    @Override
    public String toString() {
        return "NotificationTask{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", text='" + text + '\'' +
                ", localDateTime=" + localDateTime +
                '}';
    }
}
