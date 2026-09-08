package za.org.ecdoe.security.di

import com.auth0.jwt.algorithms.Algorithm
import de.mkammerer.argon2.Argon2
import de.mkammerer.argon2.Argon2Factory
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.module
import za.org.ecdoe.security.jwt.JwtManager
import za.org.ecdoe.security.jwt.JwtEnvironment
import za.org.ecdoe.security.jwt.JwtProvider
import za.org.ecdoe.security.password.PasswordHashProvider
import za.org.ecdoe.security.password.PasswordHasher_Argon2

private val SERVER_SECURITY_MODULE_NAME = "KoinDI@Elevate:server:security"

fun securityModule() = module {
    named(SERVER_SECURITY_MODULE_NAME)

    single<Algorithm> {
        Algorithm.HMAC256(JwtEnvironment.SECRET)
    }
    single<JwtProvider> {
        JwtManager(JwtAlgorithm)
    }

    single<Argon2> {
        Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id)
    }
    single<PasswordHashProvider> {
        PasswordHasher_Argon2(ArgonInstance)
    }
}

val Scope.ArgonInstance
    get() = get<Argon2>()

val Scope.PasswordHasher
    get() = get<PasswordHashProvider>()

val Scope.JwtAlgorithm
    get() = get<Algorithm>()

val Scope.JwtProvider
    get() = get<JwtProvider>()