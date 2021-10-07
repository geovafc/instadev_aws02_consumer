package br.com.instadev.aws_instadev02.config

import com.amazon.sqs.javamessaging.ProviderConfiguration
import com.amazon.sqs.javamessaging.SQSConnectionFactory
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import com.amazonaws.services.sqs.AmazonSQSClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jms.annotation.EnableJms
import org.springframework.jms.config.DefaultJmsListenerContainerFactory
import org.springframework.jms.support.destination.DynamicDestinationResolver
import javax.jms.Session

//Quando o spring boot subir a nossa aplicação, ele carrega essa classe
// de config
@Configuration
//Ligar o mecanismo do jms na aplicação
@EnableJms
class JmsConfig(
    @Value("\${aws.region}") private val awsRegion: String,
    private val sqsConnectionFactory: SQSConnectionFactory
) {


    //    Bean que será utilizado pelo jms
    @Bean
    fun jmsListenerContainerFactory() : DefaultJmsListenerContainerFactory {
//        Cria o connectionFactory,
        val sqsConnectionFactory = SQSConnectionFactory(
            ProviderConfiguration(),
            AmazonSQSClientBuilder.standard()
//                    Qual a região que vou usar
                .withRegion(awsRegion)
//                    Onde estão as credenciais de acesso e os papéis que podem ser usados
                .withCredentials(DefaultAWSCredentialsProviderChain())
                .build()
        );

//        Config do JMS para poder acessar a fila
        val factory = DefaultJmsListenerContainerFactory()
        factory.setConnectionFactory(sqsConnectionFactory)
        factory.setDestinationResolver(DynamicDestinationResolver())
        factory.setConcurrency("2")
        factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE)

        return factory
    }
}