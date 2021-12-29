package br.com.instadev.aws_instadev02.repository

import br.com.instadev.aws_instadev02.model.PostEventKey
import br.com.instadev.aws_instadev02.model.PostEventLog
import org.socialsignin.spring.data.dynamodb.repository.EnableScan
import org.springframework.data.repository.CrudRepository

//Habilita esta classe para ser visível pela classe de configuração do DynamoDB
@EnableScan
interface PostEventLogRepository : CrudRepository <PostEventLog, PostEventKey >{

    fun findAllByPk(code: String): List<PostEventLog>

    fun findAllByPkAndSkStartsWith(code: String, eventType: String): List<PostEventLog>

}