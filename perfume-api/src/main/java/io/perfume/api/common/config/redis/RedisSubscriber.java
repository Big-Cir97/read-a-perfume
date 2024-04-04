package io.perfume.api.common.config.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.perfume.api.notification.application.port.in.dto.NotificationResult;
import io.perfume.api.notification.application.port.in.dto.SendNotificationResult;
import io.perfume.api.notification.application.port.out.emitter.EmitterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class RedisSubscriber implements MessageListener {

    private final EmitterRepository emitterRepository;
    private final ObjectMapper objectMapper;

    // subscribe 해두었던 topic 에 publish 가 일어나면 메서드가 호출된다. 여기서 client 에게 이벤트를 전송한다.
    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String userId = new String(message.getChannel());
            var notificationResult
                    = objectMapper.readValue(message.getBody(), NotificationResult.class);

            emitterRepository
                    .findAllEmitterStartWithByUserId(userId)
                    .forEach(
                            (key, emitter) -> {
                                emitterRepository.saveEventCache(key, notificationResult);
                                try {
                                    emitter.send(SseEmitter.event().
                                            id(key)
                                            .data(SendNotificationResult.from(notificationResult),
                                                    MediaType.APPLICATION_JSON));
                                } catch (IOException e) {
                                    emitterRepository.deleteEmitterByEmitterId(key);
                                    emitter.completeWithError(e);
                                }
                            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
