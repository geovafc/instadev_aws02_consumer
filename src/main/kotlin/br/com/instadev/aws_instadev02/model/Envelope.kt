package br.com.instadev.aws_instadev02.model

import br.com.instadev.aws_instadev02.enums.EventType

data class Envelope(private var eventType: EventType, private var data: String)  {
}