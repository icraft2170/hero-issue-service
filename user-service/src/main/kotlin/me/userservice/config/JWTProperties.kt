package me.userservice.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "jwt")
class JWTProperties @ConstructorBinding constructor(
    val issuer: String,
    val subject: String,
    val expiresTime: Long,
    val secret: String,
)