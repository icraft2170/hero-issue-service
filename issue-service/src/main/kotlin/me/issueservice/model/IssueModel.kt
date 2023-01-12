package me.issueservice.model

import com.fasterxml.jackson.annotation.JsonFormat
import me.issueservice.domain.Issue
import me.issueservice.domain.enums.IssuePriority
import me.issueservice.domain.enums.IssueStatus
import me.issueservice.domain.enums.IssueType
import java.time.LocalDateTime

data class IssueRequest(
    val summary: String,
    val description: String,
    val type: IssueType,
    val priority: IssuePriority,
    val status: IssueStatus,
)
data class IssueResponse (
    val id: Long,
    val summary: String,
    val description: String,
    val type: IssueType,
    val priority: IssuePriority,
    val status: IssueStatus,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime?,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val updatedAt: LocalDateTime?,
) {

    companion object {
        operator fun invoke(issue: Issue) =
            with(issue) {
                IssueResponse(
                    id = issue.id!!,
                    summary = issue.summary,
                    description = issue.description,
                    type = issue.type,
                    priority = issue.priority,
                    status = issue.status,
                    createdAt = issue.createdAt,
                    updatedAt = issue.updatedAt,
                )
            }
    }

}
