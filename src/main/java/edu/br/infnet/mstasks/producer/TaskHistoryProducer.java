package edu.br.infnet.mstasks.producer;

import edu.br.infnet.mstasks.dto.TaskHistoryDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TaskHistoryProducer {

    final RabbitTemplate rabbitTemplate;
    public TaskHistoryProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value(value = "${broker.queue.history.name}")
    private String routingKey;

    public void publish(TaskHistoryDTO taskHistory) {
        rabbitTemplate.convertAndSend(routingKey, taskHistory);

    }

    public void logCreateAction(Long taskId, Long userId, String newValue) {
        TaskHistoryDTO history = new TaskHistoryDTO();
        history.setTaskId(taskId);
        history.setUserId(userId);
        history.setChangeType("CREATE");
        history.setNewValue(newValue);
        try {
            rabbitTemplate.convertAndSend(routingKey, history);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void logUpdateAction(Long taskId, Long userId, String oldValue, String newValue) {
        TaskHistoryDTO history = new TaskHistoryDTO();
        history.setTaskId(taskId);
        history.setUserId(userId);
        history.setChangeType("UPDATE");
        history.setOldValue(oldValue);
        history.setNewValue(newValue);

        rabbitTemplate.convertAndSend(routingKey, history);
    }


    public void logDeleteAction(Long taskId,Long userId, String oldValue) {
        TaskHistoryDTO history = new TaskHistoryDTO();
        history.setTaskId(taskId);
        history.setUserId(userId);
        history.setChangeType("DELETE");
        history.setOldValue(oldValue);

        rabbitTemplate.convertAndSend(routingKey, history);
    }


}
