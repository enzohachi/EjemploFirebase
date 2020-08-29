package cl.grupo2.ejemplofirebase.login.presentation

import cl.grupo2.ejemplofirebase.login.domain.UserAuth

sealed class LoginUiState (
    open val result: UserAuth? =null,
    open val error: Throwable? =null
){
    object Loading : LoginUiState()
    data class Success(override val result: UserAuth?) : LoginUiState(result = result)
    data class Error (override val error : Throwable?) : LoginUiState(error = error)
    object Invaliduser : LoginUiState()
}
