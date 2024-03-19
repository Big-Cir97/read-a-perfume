package io.perfume.api.common.config.circuitbreaker;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Service
public class CircuitBreakerSubscriber implements MessageListener {

    // 수신하고 있는 서버에서 CircuitBreaker 인스턴스를 조회해서 OPEN 상태 변경
    @Override
    public void onMessage(Message message, byte[] pattern) {

    }
}
