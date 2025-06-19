package com.smartbalaram.emi.kafka;

import com.smartbalaram.emi.model.EmiResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class KafkaProducerServiceTest {

    @Mock
    private KafkaTemplate<String, EmiResponse> kafkaTemplate;

    @InjectMocks
    private KafkaProducerService kafkaProducerService;

    @Test
    public void testSendEmiWarning() {
        EmiResponse response = EmiResponse.builder()
            .emiPercentage(60.0)
            .riskLevel("HIGH")
            .warningMessage("🚨 High EMI burden!")
            .suggestedMaxEmi(20000.0)
            .build();

        kafkaProducerService.sendEmiWarning("user123", response);

        //verify(kafkaTemplate).send("emi-warning-topic", "user123", response);
        verify(kafkaTemplate).send("emi-warning-topic", response);

    }
}
