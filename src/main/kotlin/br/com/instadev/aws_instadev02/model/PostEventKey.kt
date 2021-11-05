package br.com.instadev.aws_instadev02.model

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey

data class PostEventKey(
    //pk = partition key
    @DynamoDBHashKey(attributeName = "pk")
    var pk: String,
//    sort key - define o range de valores que podemos colocar dentro da partition key
    @DynamoDBRangeKey(attributeName = "sk")
    var sk: String
) {
    constructor() : this("", "")

}