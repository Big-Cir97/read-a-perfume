package io.perfume.api.common.config.circuitbreaker;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Service
public class CircuitBreakerSubscriber implements MessageListener {

    @Override
    public void onMessage(Message message, byte[] pattern) {

    }
}
