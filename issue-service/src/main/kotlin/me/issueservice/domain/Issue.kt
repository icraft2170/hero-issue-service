package me.issueservice.domain

import jakarta.persistence.*
import me.issueservice.domain.enums.IssuePriority
import me.issueservice.domain.enums.IssueStatus
import me.issueservice.domain.enums.IssueType

@Entity
class Issue(
    @Column
    var userId: Long,

    @OneToMany(fetch = FetchType.EAGER)
    val comments: MutableList<Comment> = mutableListOf(),

    @Column
    var summary: String,

    @Column
    var description: String,

    @Column
    @Enumerated(EnumType.STRING)
    var type: IssueType,

    @Column
    @Enumerated(EnumType.STRING)
    var priority: IssuePriority,

    @Column
    @Enumerated(EnumType.STRING)
    var status: IssueStatus,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) : BaseEntity()