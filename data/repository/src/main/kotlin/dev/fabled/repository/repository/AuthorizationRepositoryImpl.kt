package dev.fabled.repository.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestore
import dev.fabled.domain.model.DefaultErrors
import dev.fabled.domain.model.Resource
import dev.fabled.domain.model.UserModel
import dev.fabled.domain.repository.AuthorizationRepository
import dev.fabled.domain.repository.ErrorsRepository
import dev.fabled.repository.utils.Constants.USERS_COLLECTION
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject

class AuthorizationRepositoryImpl @Inject constructor(
    private val authenticator: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore,
    private val errorsRepository: ErrorsRepository
) : AuthorizationRepository {

    override suspend fun getUserData(): UserModel {
        val userDataMap = firebaseFirestore
            .collection(USERS_COLLECTION)
            .document(authenticator.uid.toString())
            .get()
            .await()
            .data

        return UserModel(
            username = userDataMap?.get("username").toString(),
            userGender = userDataMap?.get("gender").toString(),
            backgroundPicTag = userDataMap?.get("background_pic_tag").toString()
        )
    }

    override fun isUserAuthenticated(): Flow<Boolean> = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener { auth ->
            trySend(element = auth.currentUser != null)
        }
        authenticator.addAuthStateListener(authStateListener)

        awaitClose {
            authenticator.removeAuthStateListener(authStateListener)
        }
    }

    override suspend fun signUpFirebase(
        email: String,
        password: String,
        userModel: UserModel
    ) = flow {
        emit(Resource.Loading)
        try {
            val credentials = authenticator.createUserWithEmailAndPassword(email, password).await()
            val userDataMap = hashMapOf(
                "username" to userModel.username,
                "gender" to userModel.userGender,
                "background_pic_tag" to userModel.backgroundPicTag
            )

            firebaseFirestore
                .collection("USERS_COLLECTION")
                .document(credentials.user!!.uid)
                .set(userDataMap)
                .await()

            emit(Resource.Completed)
        } catch (exception: FirebaseAuthException) {
            Timber.e(exception)
            val error = errorsRepository.resolveAuthenticationException(exception)
            emit(Resource.Error(error))
        } catch (exception: Exception) {
            Timber.e(exception)
            emit(Resource.Error(DefaultErrors.UnknownError(errorMessage = "Unknown error")))
        }
    }

    override suspend fun signInFirebase(email: String, password: String) = flow {
        emit(Resource.Loading)
        try {
            authenticator.signInWithEmailAndPassword(email, password).await()
            emit(Resource.Completed)
        } catch (exception: FirebaseAuthException) {
            Timber.e(exception)
            val error = errorsRepository.resolveAuthenticationException(exception)
            emit(Resource.Error(error))
        }
    }

    override fun signOut() {
        authenticator.signOut()
    }

}