package br.com.instadev.aws_instadev02.config

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import com.amazonaws.regions.Regions
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import javax.enterprise.inject.Default

//Spring boot vai executar essa classe quando a aplicação subir
@Configuration
//Habilita a configuração do dynamodb para poder utilizar uma camada de acesso ao BD
@EnableDynamoDBRepositories(
    basePackageClasses = PostEventLogRepository.class)
//            As configurações dessa classe será aplicada em todas as tabelas que a nossa app tiver.
    class DynamoDBConfig {

    @Value("\${aws.region}")
    lateinit var awsRegion: String


    @Bean
    @Primary
    fun dynamoDBMapperConfig(): DynamoDBMapperConfig {

        return DynamoDBMapperConfig.DEFAULT
    }

    @Bean
    @Primary
    fun dynamoDBMapper(amazonDynamoDB: AmazonDynamoDB, config: DynamoDBMapperConfig): DynamoDBMapper {
//amazonDynamoDB é o cliente do dynamodb, para podermos acessar ele

//config é a configuração feita no método dynamoDBMapperConfig()
        return DynamoDBMapper(amazonDynamoDB, config)
    }

    //Bean responsável por fazer configurações mais complexas
    @Bean
    @Primary
    fun amazonDynamoDB(): AmazonDynamoDB {
        return AmazonDynamoDBClientBuilder.standard()
//                Define o que que o serviço pode fazer ou não.
            .withCredentials( DefaultAWSCredentialsProviderChain())
            .withRegion(Regions.fromName(awsRegion)).build()

    }

}