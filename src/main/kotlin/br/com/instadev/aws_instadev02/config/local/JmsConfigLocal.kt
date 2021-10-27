package br.com.instadev.aws_instadev02.config.local

import com.amazon.sqs.javamessaging.ProviderConfiguration
import com.amazon.sqs.javamessaging.SQSConnectionFactory
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.regions.Regions
import com.amazonaws.services.sqs.AmazonSQSClient
import com.amazonaws.services.sqs.AmazonSQSClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.jms.annotation.EnableJms
import org.springframework.jms.config.DefaultJmsListenerContainerFactory
import org.springframework.jms.support.destination.DynamicDestinationResolver
import javax.jms.Session

//Quando o spring boot subir a nossa aplicação, ele carrega essa classe
// de config
@Configuration
//Ligar o mecanismo do jms na aplicação
@EnableJms
@Profile("local")
class JmsConfigLocal() {

    @Value("\${aws.region}")
    lateinit var awsRegion: String

    lateinit var sqsConnectionFactory: SQSConnectionFactory

    //    Bean que será utilizado pelo jms
    @Bean
    fun jmsListenerContainerFactory() : DefaultJmsListenerContainerFactory {
//        Cria o connectionFactory,
        val sqsConnectionFactory = SQSConnectionFactory(
            ProviderConfiguration(),
           AmazonSQSClient.builder()
               .withEndpointConfiguration(AwsClientBuilder
//                       Endpoint do nosso local stack
                   .EndpointConfiguration("http://localhost:4566",
                       Regions.US_EAST_1.getName()))
               .withCredentials(DefaultAWSCredentialsProviderChain())
               .build())

//        Config do JMS para poder acessar a fila
        val factory = DefaultJmsListenerContainerFactory()
        factory.setConnectionFactory(sqsConnectionFactory)
        factory.setDestinationResolver(DynamicDestinationResolver())
//        Número de threads que eu quero ter consumindo as minhas mensagens por fila
        factory.setConcurrency("2")
//        Quando eu receber a mensagem quero que o JMS der o reconhecimento de que a mensagem já foi tratada
        factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE)

        return factory
    }
}