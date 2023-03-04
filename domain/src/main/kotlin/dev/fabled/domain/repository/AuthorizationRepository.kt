package dev.fabled.domain.repository

import dev.fabled.domain.model.Resource
import dev.fabled.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

interface AuthorizationRepository {

    suspend fun getUserData(): UserModel

    fun isUserAuthenticated(): Flow<Boolean>

    suspend fun signUpFirebase(
        email: String,
        password: String,
        userModel: UserModel
    ): Flow<Resource<Nothing>>

    suspend fun signInFirebase(email: String, password: String): Flow<Resource<Nothing>>

    fun signOut()

}