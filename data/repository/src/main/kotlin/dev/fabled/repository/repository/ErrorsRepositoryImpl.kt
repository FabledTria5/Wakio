package dev.fabled.repository.repository

import android.content.Context
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.fabled.domain.model.DefaultErrors
import dev.fabled.domain.model.ErrorItem
import dev.fabled.domain.repository.ErrorsRepository
import javax.inject.Inject

class ErrorsRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ErrorsRepository {

    sealed class NetworkErrors {
        class ForbiddenError(override val errorMessage: String) : ErrorItem()
        class BadRequestError(override val errorMessage: String) : ErrorItem()
        class UnAuthorizedError(override val errorMessage: String) : ErrorItem()
        class NotFoundError(override val errorMessage: String) : ErrorItem()
        class ServerError(override val errorMessage: String) : ErrorItem()
        class TimeoutError(override val errorMessage: String) : ErrorItem()
    }

    sealed class AuthenticationErrors {
        class NotExists(override val errorMessage: String) : ErrorItem()
        class InvalidCredentials(override val errorMessage: String) : ErrorItem()
        class AlreadyExists(override val errorMessage: String) : ErrorItem()
    }

    sealed class RemoteDataStoreErrors {
        class Cancelled(override val errorMessage: String) : ErrorItem()
        class AlreadyExists(override val errorMessage: String) : ErrorItem()
        class ServerError(override val errorMessage: String) : ErrorItem()
    }

    override fun resolveAuthenticationException(exception: Exception): ErrorItem {
        require(exception is FirebaseAuthException)

        return when (exception) {
            is FirebaseAuthInvalidUserException -> AuthenticationErrors.NotExists(
                errorMessage = "User does not exists"
            )

            is FirebaseAuthInvalidCredentialsException -> AuthenticationErrors.InvalidCredentials(
                errorMessage = "Invalid credentials"
            )

            is FirebaseAuthUserCollisionException -> AuthenticationErrors.AlreadyExists(
                errorMessage = "User already exists"
            )

            else -> DefaultErrors.UnknownError(errorMessage = "Unknown error")
        }
    }

}