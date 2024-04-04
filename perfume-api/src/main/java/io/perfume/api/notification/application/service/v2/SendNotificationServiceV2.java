package io.perfume.api.notification.application.service.v2;

import io.perfume.api.common.config.redis.RedisPublisher;
import io.perfume.api.notification.application.port.in.SendNotificationUseCase;
import io.perfume.api.notification.application.port.in.dto.NotificationResult;
import io.perfume.api.notification.application.port.out.emitter.EmitterRepository;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
public class SendNotificationServiceV2 implements SendNotificationUseCase {

  private final EmitterRepository emitterRepository;
  private final RedisPublisher redisPublisher;

  public SendNotificationServiceV2(EmitterRepository emitterRepository, RedisPublisher redisPublisher) {
    this.emitterRepository = emitterRepository;
    this.redisPublisher = redisPublisher;
  }

  @Override
  public void send(NotificationResult notificationResult) {
    ChannelTopic topic = new ChannelTopic(notificationResult.receiveUserId().toString());
    redisPublisher.publish(topic, notificationResult);
  }

  @Override
  public void sendLostData(String lastEventId, Long userId, String emitterId, SseEmitter emitter) {
    if (hasLostData(lastEventId)) {
      emitterRepository
          .findAllEventCacheStartWithByUserId(String.valueOf(userId))
          .entrySet()
          .stream()
          .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
          .forEach(entry -> sendNotification(emitter, entry.getKey(), entry.getValue()));
    }
  }

  @Override
  public void connectNotification(SseEmitter emitter, String emitterId, Long userId) {
    sendNotification(emitter, emitterId, "Connected to Server. [userId=" + userId + "]");
  }

  private void sendNotification(SseEmitter emitter, String emitterId, Object data) {
    try {
      emitter.send(SseEmitter.event().id(emitterId).data(data, MediaType.APPLICATION_JSON));
    } catch (Exception ex) {
      emitterRepository.deleteEmitterByEmitterId(emitterId);
      emitter.completeWithError(ex);
    }
  }

  private boolean hasLostData(String lastEventId) {
    return !lastEventId.isEmpty();
  }
}
