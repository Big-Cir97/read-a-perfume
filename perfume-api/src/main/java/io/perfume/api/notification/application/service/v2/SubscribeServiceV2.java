package io.perfume.api.notification.application.service.v2;

import io.perfume.api.common.config.redis.RedisSubscriber;
import io.perfume.api.notification.application.port.in.SubscribeUseCase;
import io.perfume.api.notification.application.port.out.emitter.EmitterRepository;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
public class SubscribeServiceV2 implements SubscribeUseCase {

  private final EmitterRepository emitterRepository;
  private final RedisSubscriber subscriber;
  private final RedisMessageListenerContainer container;

  public SubscribeServiceV2(EmitterRepository emitterRepository, RedisSubscriber subscriber, RedisMessageListenerContainer container) {
    this.emitterRepository = emitterRepository;
    this.subscriber = subscriber;
    this.container = container;
  }

  @Override
  public SseEmitter subscribe(final String emitterId, final String lastEventId) {
    final Long DEFAULT_TIMEOUT = 60L * 1000L * 60L;
    var emitter = emitterRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));

    ChannelTopic topic = new ChannelTopic(emitterId.split("_")[0]);
    container.addMessageListener(this.subscriber, topic);

    emitter.onCompletion(() -> emitterRepository.deleteEmitterByEmitterId(emitterId));
    emitter.onTimeout(() -> emitterRepository.deleteEmitterByEmitterId(emitterId));
    emitter.onError((ec) -> emitterRepository.deleteEmitterByEmitterId(emitterId));
    return emitter;
  }
}
