package br.com.instadev.aws_instadev02.service

import br.com.instadev.aws_instadev02.model.Envelope
import br.com.instadev.aws_instadev02.model.PostEvent
import br.com.instadev.aws_instadev02.model.PostEventLog
import br.com.instadev.aws_instadev02.model.SnsMessage
import br.com.instadev.aws_instadev02.repository.PostEventLogRepository
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.stereotype.Service
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jms.annotation.JmsListener
import java.io.IOException
import java.time.Duration
import javax.jms.JMSException
import javax.jms.TextMessage

@Service
class PostEventConsumer {

    private val logger = LoggerFactory.getLogger(javaClass)
    private var objectMapper: ObjectMapper
    private var postEventLogRepository: PostEventLogRepository

    @Autowired
    constructor(objectMapper: ObjectMapper, postEventLogRepository: PostEventLogRepository) {
        this.objectMapper = objectMapper
        this.postEventLogRepository = postEventLogRepository
    }


    //    Método que será invocado pelo jms quando alguma mensagem chegar na nossa fila (aws.sqs.queue.post.events.name).
    // e então coloque essa mensagem em textMessage
    @Throws(IOException::class, JMSException::class)
    @JmsListener(destination = "\${aws.sqs.queue.post.events.name}")
    fun receivePostEvent(textMessage: TextMessage) {
        var snsMessage = objectMapper.readValue<SnsMessage>(textMessage.text)

        var envelope = objectMapper.readValue<Envelope>(snsMessage.message)

        var postEvent = objectMapper.readValue<PostEvent>(envelope.data)

        logger.info("Post event received - Event {} - PostId {} - MessageId {} - ", envelope.eventType, postEvent.postId, snsMessage.messageId)

        var postEventLog = buildPostEventLog(envelope, postEvent)
        postEventLogRepository.save( postEventLog )
    }

    fun buildPostEventLog(envelope: Envelope, postEvent: PostEvent) : PostEventLog {
        val timeStamp = System.currentTimeMillis()

        var postEventLog = PostEventLog()
        postEventLog.id.pk = postEvent.postId.toString() + "_" + postEvent.userName
        postEventLog.id.sk = envelope.eventType.toString() + "_" + timeStamp
        postEventLog.eventType = envelope.eventType
        postEventLog.postId = postEvent.postId
        postEventLog.username = postEvent.userName
        postEventLog.timestamp = timeStamp
//      TTL é responsável por remover este objeto do dynamoDB após um tempo definido
//      então aqui a partir de 10 minutos ele será excluído
        postEventLog.ttl = timeStamp.plus(600000)

        return  postEventLog
    }
}