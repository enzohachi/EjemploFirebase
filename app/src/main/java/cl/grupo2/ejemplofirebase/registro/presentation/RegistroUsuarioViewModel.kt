package cl.grupo2.ejemplofirebase.registro.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.grupo2.ejemplofirebase.registro.domain.RegistraUsuarioUseCase
import cl.grupo2.ejemplofirebase.registro.domain.RegistroUsuario
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.coroutines.launch
import java.lang.Exception

class RegistroUsuarioViewModel(
    private val registroUsuarioUseCase: RegistraUsuarioUseCase
) : ViewModel(){
    private val liveData = MutableLiveData<RegistroUiState>()

    fun getLiveData() = liveData

    fun registrarUsuario (registroUsuario: RegistroUsuario){
        liveData.postValue(RegistroUiState.LoadingRegistroUiState)
        viewModelScope.launch {
            try{
                val result = registroUsuarioUseCase.execute(registroUsuario)
                handleResult(result)
            } catch (exception : Exception){
                handleError (exception)
            }
        }
    }

    private fun handleError(exception: Exception) {
        if (exception is FirebaseAuthUserCollisionException){
            liveData.postValue(RegistroUiState.InvalidEmailRegistroUiState)
        } else {
            liveData.postValue(RegistroUiState.ErrorRegistroUiState(exception))
        }
    }

    private fun handleResult(result: Boolean) {
        if (result){
            liveData.postValue(RegistroUiState.SuccessRegistroUiState)
        } else{
            liveData.postValue(RegistroUiState.InvalidEmailRegistroUiState)
        }
    }
}