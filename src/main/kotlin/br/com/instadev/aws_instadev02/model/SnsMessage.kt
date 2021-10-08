package br.com.instadev.aws_instadev02.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

//Se tiver alguma outra propriedade no json e você não encontrar aqui no meu modelo, ignore.
//Não precisa tentar fazer o parse de algum campo desconhecido para o objeto SnsMessage
@JsonIgnoreProperties(ignoreUnknown = true)
data class SnsMessage(@JsonProperty("Message") private var message: String,
                 @JsonProperty("Type") private var type: String,
//                 Arn do tópico de quem publicou
                 @JsonProperty("TopicArn") private var topicArn: String,
                 @JsonProperty("Timestamp") private var timestamp: String,
                 @JsonProperty("MessageId") private var messageId: String,

                 ) {

}