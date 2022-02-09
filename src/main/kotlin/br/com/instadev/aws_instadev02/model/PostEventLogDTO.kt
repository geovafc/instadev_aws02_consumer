package br.com.instadev.aws_instadev02.model

import br.com.instadev.aws_instadev02.enums.EventType

data class PostEventLogDTO(
    var code: String?,

    var eventType: EventType?,

    var postId: Long?,

    var username: String?,

    var timestamp: Long? = null,
) {

    fun eventLogToDTO(postEventLog: PostEventLog){
        this.code = postEventLog.getPk()
        this.eventType = postEventLog.eventType
        this.postId = postEventLog.postId
        this.username = postEventLog.username
        this.timestamp = postEventLog.timestamp
    }
}