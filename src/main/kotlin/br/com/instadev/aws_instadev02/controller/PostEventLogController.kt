package br.com.instadev.aws_instadev02.controller

import br.com.instadev.aws_instadev02.repository.PostEventLogRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api")
class PostEventLogController {
   private val postEventLogRepository: PostEventLogRepository

    @Autowired
    constructor(postEventLogRepository: PostEventLogRepository) {
        this.postEventLogRepository = postEventLogRepository
    }
}