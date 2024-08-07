package com.jxx.groupware.messaging.consumer;

import com.jxx.groupware.core.message.domain.MessageQ;
import com.jxx.groupware.messaging.application.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.ServiceActivators;
import org.springframework.messaging.Message;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// TODO : 예외 발생 시, 큰 문제가 생긴다.. 채널 수 만큼 입력됨

@Slf4j
@MessageEndpoint
@RequiredArgsConstructor
public class SimpleMessageConsumer {

    @Qualifier("vacationMessageService")
    private final MessageService messageService;
    @Transactional
    @ServiceActivators({
            @ServiceActivator(inputChannel = "sentQueueChannel1")
    })
    public void consumeSentMessage1(List<Message<MessageQ>> message) {
        processes(message, "1");
    }
    @Transactional
    @ServiceActivators({
            @ServiceActivator(inputChannel = "sentQueueChannel2")
    })
    public void consumeSentMessage2(List<Message<MessageQ>> message) {
        processes(message, "2");
    }

    public void processes(List<Message<MessageQ>> messages, String channelNum) {
        for (Message<MessageQ> message : messages) {
            messageService.process(message);
            log.info("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> HISTORY START >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" +
                    "\nChannel:sentQueueChannel{}" +
                    "\nMessage:{}" +
                    "\n<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< HISTORY  END  <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<", channelNum, message);

        }
    }


    @Transactional
    @ServiceActivator(inputChannel = "retryQueueChannel")
    public void consumeRetryMessage(Message<MessageQ> message) {
        messageService.retry(message);
            log.info("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> HISTORY START >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" +
                    "\nChannel:retryQueueChannel" +
                    "\nMessage:{}" +
                    "\n<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< HISTORY  END  <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<", message);
    }
}
