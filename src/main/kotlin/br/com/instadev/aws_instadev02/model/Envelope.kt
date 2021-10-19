package br.com.instadev.aws_instadev02.model

import br.com.instadev.aws_instadev02.enums.EventType

data class Envelope( var eventType: EventType, var data: String)  {
}