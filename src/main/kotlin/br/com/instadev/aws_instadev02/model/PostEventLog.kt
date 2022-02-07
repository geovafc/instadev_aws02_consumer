package br.com.instadev.aws_instadev02.model

import br.com.instadev.aws_instadev02.enums.EventType
import com.amazonaws.services.dynamodbv2.datamodeling.*
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id

@DynamoDBTable(tableName = "post-events")
data class PostEventLog(
    @field:Id
    @DynamoDBIgnore
    @JsonIgnore
    val id: PostEventLogId = PostEventLogId()
){
    @DynamoDBHashKey
    fun getPk() = id?.pk

    fun setPk(pk: String) {
        id.pk = pk
    }

    @DynamoDBRangeKey
    fun getSk() = id.sk

    fun setSk(sk: String) {
        id.sk = sk
    }


    //    No dynamodb não tem enum, então preciso converter o ENUM para string. Você pode criar seus conversores
    @DynamoDBTypeConvertedEnum
    @DynamoDBAttribute(attributeName = "eventType")
    var eventType: EventType? = null

    @DynamoDBAttribute(attributeName = "postId")
    var postId: Long? = null

    @DynamoDBAttribute(attributeName = "username")
    var username: String? = null

    @DynamoDBAttribute(attributeName = "timestamp")
    var timestamp: Long? = null

    @DynamoDBAttribute(attributeName = "ttl")
    var ttl: Long? = null
}