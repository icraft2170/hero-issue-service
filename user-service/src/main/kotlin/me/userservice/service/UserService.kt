package me.userservice.service

import com.auth0.jwt.interfaces.DecodedJWT
import me.userservice.config.JWTProperties
import me.userservice.domain.entity.User
import me.userservice.domain.repository.UserRepository
import me.userservice.exception.InvalidJwtTokenException
import me.userservice.exception.PasswordNotMatchedException
import me.userservice.exception.UserExistsException
import me.userservice.exception.UserNotFoundException
import me.userservice.model.SignInRequest
import me.userservice.model.SignInResponse
import me.userservice.model.SignUpRequest
import me.userservice.utils.BCryptUtils
import me.userservice.utils.JWTClaim
import me.userservice.utils.JWTUtils
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class UserService(
    private val userRepository: UserRepository,
    private val jwtProperties: JWTProperties,
    private val cacheManager: CoroutineCacheManager<User>,
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

    suspend fun signIn(request: SignInRequest): SignInResponse {
        val user = userRepository.findByEmail(request.email) ?: throw UserNotFoundException()
        with(user) {
            val verified =
                BCryptUtils.verify(password = request.password, hashedPassword = user.password)
            if (!verified) {
                throw PasswordNotMatchedException()
            }

            val jwtClaim = JWTClaim(
                userId = id!!,
                email = email,
                profileUrl = profileUrl,
                username = username,
            )
            val token = JWTUtils.createToken(jwtClaim, jwtProperties)

            cacheManager.awaitPut(key = token, value = this, ttl = CACHE_TTL)
            return SignInResponse(
                email = email,
                username =username,
                token = token,
            )
        }
    }

    suspend fun logout(token: String) {
        cacheManager.awaitEvict(token)

    }

    suspend fun getByToken(token: String): User {
        val cachedUser = cacheManager.awaitGetOrPut(key = token, ttl = CACHE_TTL) {
            val decodedJwt: DecodedJWT = JWTUtils.decode(token, jwtProperties.secret, jwtProperties.issuer)

            val userId = decodedJwt.claims["userId"]?.asLong() ?: throw InvalidJwtTokenException()
            get(userId)
        }
        return cachedUser
    }

    suspend fun get(userId: Long): User {
        return userRepository.findById(userId) ?: throw UserNotFoundException()
    }

    suspend fun edit(token: String, username: String, profileUrl: String?) {
        val user = getByToken(token)
        val newUser = user.copy(username = username, profileUrl = profileUrl ?: user.profileUrl)
        userRepository.save(newUser).also {
            cacheManager.awaitPut(key = token, value = it, ttl = CACHE_TTL)
        }
    }

    companion object {
        private val CACHE_TTL = Duration.ofMinutes(1)
    }
}