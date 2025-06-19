package com.smartbalaram.emi.kafka;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.smartbalaram.emi.util.KafkaConstants.TOPIC_EMI_WARNING;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendEmiWarning(String userId, Object payload) {
        logger.info("🚀 Sending EMI warning to Kafka topic [{}] for user {}", TOPIC_EMI_WARNING, userId);
        kafkaTemplate.send(TOPIC_EMI_WARNING, payload);
    }
}
