package br.com.instadev.aws_instadev02.model

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey
import java.io.Serializable

@DynamoDBDocument
class PostEventLogId(
    //pk = partition key
    @field:DynamoDBHashKey
    var pk: String? = "",

//    sort key - define o range de valores que podemos colocar dentro da partition key
    @field:DynamoDBRangeKey
    var sk: String? = null

) : Serializable