package me.issueservice.web

import me.issueservice.config.AuthUser
import me.issueservice.model.CommentRequest
import me.issueservice.model.CommentResponse
import me.issueservice.service.CommentService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/issues/{issueId}/comments")
class CommentController (
    private val commentService: CommentService,
) {

    @PostMapping
    fun create(
        authUser: AuthUser,
        @PathVariable issueId: Long,
        @RequestBody request: CommentRequest,
    ): CommentResponse = commentService.create(issueId, authUser.userId, authUser.username, request)

    @PutMapping("{commentId}")
    fun edit(
        authUser: AuthUser,
        @PathVariable commentId: Long,
        @RequestBody request: CommentRequest,
    ): CommentResponse? = commentService.edit(commentId , authUser.userId, request)

    @DeleteMapping("{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(
        authUser: AuthUser,
        @PathVariable issueId: Long,
        @PathVariable commentId: Long,
    ) = commentService.delete(issueId, commentId , authUser.userId)


}