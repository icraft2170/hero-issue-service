package me.userservice.service

import me.userservice.domain.entity.User
import me.userservice.domain.repository.UserRepository
import me.userservice.exception.UserExistsException
import me.userservice.model.SignUpRequest
import me.userservice.utils.BCryptUtils
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
) {

    suspend fun signUp(signUpRequest: SignUpRequest) {
        with(signUpRequest) {
            userRepository.findByEmail(email)?. let {
                throw UserExistsException()
            }

            val user = User(
                email = email,
                password = BCryptUtils.hash(password),
                username = username,
            )
            userRepository.save(user)
        }

    }
}