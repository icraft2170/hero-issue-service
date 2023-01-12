package me.issueservice.service

import me.issueservice.domain.Issue
import me.issueservice.domain.IssueRepository
import me.issueservice.domain.enums.IssueStatus
import me.issueservice.model.IssueRequest
import me.issueservice.model.IssueResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class IssueService (
    private val issueRepository: IssueRepository,
) {
    @Transactional
    fun create(
        request: IssueRequest,
        userId: Long,
    ): IssueResponse {
        val issue = Issue(
            summary = request.summary,
            description = request.description,
            userId = userId,
            type = request.type,
            priority = request.priority,
            status = request.status,
        )

        return IssueResponse(issueRepository.save(issue))
    }

    @Transactional(readOnly = true)
    fun getAll(status: IssueStatus): List<IssueResponse>? =
        issueRepository.findAllByStatusOrderByCreatedAtDesc(status)
            ?.map { IssueResponse(it) }
}