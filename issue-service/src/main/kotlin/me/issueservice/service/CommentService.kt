package me.issueservice.service

import me.issueservice.domain.Comment
import me.issueservice.domain.CommentRepository
import me.issueservice.domain.Issue
import me.issueservice.domain.IssueRepository
import me.issueservice.exception.NotFoundException
import me.issueservice.model.CommentRequest
import me.issueservice.model.CommentResponse
import me.issueservice.model.toResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentService (
    private val commentRepository: CommentRepository,
    private val issueRepository: IssueRepository,
) {
    @Transactional
    fun create(issueId: Long, userId: Long, username: String, request: CommentRequest): CommentResponse {
        val issue: Issue = issueRepository.findByIdOrNull(issueId)
            ?: throw NotFoundException("이슈가 존재하지 않습니다.")

        val comment = Comment(
            issue = issue,
            userId = userId,
            username = username,
            body = request.body,
        )

        issue.comments.add(comment)
        return commentRepository.save(comment).toResponse()
    }
}