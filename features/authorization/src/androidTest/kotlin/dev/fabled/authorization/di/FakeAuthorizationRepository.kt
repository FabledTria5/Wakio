package dev.fabled.authorization.di

import dev.fabled.domain.model.Resource
import dev.fabled.domain.model.UserModel
import dev.fabled.domain.repository.AuthorizationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeAuthorizationRepository : AuthorizationRepository {

    override suspend fun getUserData(): UserModel {
        return UserModel(username = "Test", "Male", backgroundPicTag = "Empty")
    }

    override fun isUserAuthenticated(): Flow<Boolean> = flowOf(false)

    override suspend fun signUpFirebase(
        email: String,
        password: String,
        userModel: UserModel
    ): Flow<Resource<Nothing>> {
        return flowOf()
    }

    override suspend fun signInFirebase(email: String, password: String): Flow<Resource<Nothing>> {
        return flowOf()
    }

    override fun signOut() {
        return
    }
}