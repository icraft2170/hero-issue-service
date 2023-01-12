package me.issueservice.domain

import jakarta.persistence.*

@Entity
@Table
class Comment (

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issue_id")
    val issue: Issue,

    @Column
    val userId: Long,

    @Column
    val username: String,

    @Column
    val body: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

) : BaseEntity()