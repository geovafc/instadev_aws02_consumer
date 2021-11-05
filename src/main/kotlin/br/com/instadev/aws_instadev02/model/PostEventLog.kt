package br.com.instadev.aws_instadev02.model

import br.com.instadev.aws_instadev02.enums.EventType
import com.amazonaws.services.dynamodbv2.datamodeling.*

@DynamoDBTable(tableName = "post-events")
data class PostEventLog(
    //pk = partition key
    @DynamoDBHashKey(attributeName = "pk")
    var pk: String?,
//    sort key - define o range de valores que podemos colocar dentro da partition key
    @DynamoDBRangeKey(attributeName = "sk")
    var sk: String?,

//    No dynamodb não tem enum, então preciso converter o ENUM para string. Você pode criar seus conversores
    @DynamoDBTypeConvertedEnum
    @DynamoDBAttribute(attributeName = "eventType")
    var eventType: EventType?,

    @DynamoDBAttribute(attributeName = "postId")
    var postId: Long?,

    @DynamoDBAttribute(attributeName = "username")
    var username: String?,

    @DynamoDBAttribute(attributeName = "timestamp")
    var timestamp: Long?,

    @DynamoDBAttribute(attributeName = "ttl")
    var ttl: Long?
) {
    constructor() : this(null, null, null, null, null, null, null)
}