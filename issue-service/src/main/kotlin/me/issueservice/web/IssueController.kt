package me.issueservice.web

import me.issueservice.config.AuthUser
import me.issueservice.domain.enums.IssueStatus
import me.issueservice.model.IssueRequest
import me.issueservice.service.IssueService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/issues")
class IssueController(
    private val issueService: IssueService,
) {
    @PostMapping
    fun create(
        authUser: AuthUser,
        @RequestBody request: IssueRequest,
    ) = issueService.create(
        request = request,
        userId = authUser.userId,
    )

    @GetMapping
    fun getAll(
        authUser: AuthUser,
        @RequestParam(required = false, defaultValue = "TODO") status: IssueStatus,
    ) = issueService.getAll(status)

    @GetMapping("/{issueId}")
    fun get(
        authUser: AuthUser,
        @PathVariable("issueId") issueId: Long,
    ) = issueService.get(issueId)


    @PutMapping("/{issueId}")
    fun edit(
        authUser: AuthUser,
        @PathVariable("issueId") issueId: Long,
        @RequestBody request: IssueRequest,
    ) = issueService.edit(authUser.userId, issueId, request)

    @DeleteMapping("/{issueId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(
        authUser: AuthUser,
        @PathVariable("issueId") issueId: Long,
    ) = issueService.delete(issueId)
}