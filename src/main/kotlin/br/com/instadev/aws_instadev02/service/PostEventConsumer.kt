package br.com.instadev.aws_instadev02.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jms.annotation.JmsListener
import java.io.IOException
import javax.jms.JMSException
import javax.jms.TextMessage

@Service
class PostEventConsumer {

    private val logger = LoggerFactory.getLogger(javaClass)

    private lateinit var objectMapper: ObjectMapper

    @Autowired
    constructor(objectMapper: ObjectMapper) {
        this.objectMapper = objectMapper
    }


    //    Método que será invocado pelo jms quando alguma mensagem chegar na nossa fila (aws.sqs.queue.post.events.name).
    // e então coloque essa mensagem em textMessage
    @Throws(IOException::class, JMSException::class)
    @JmsListener(destination = "\${aws.sqs.queue.post.events.name}")
    fun receivePostEvent(textMessage: TextMessage): Void  {

    }
}