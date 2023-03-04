package dev.fabled.domain.repository

import dev.fabled.domain.model.ErrorItem

interface ErrorsRepository {

    fun resolveAuthenticationException(exception: Exception): ErrorItem

}