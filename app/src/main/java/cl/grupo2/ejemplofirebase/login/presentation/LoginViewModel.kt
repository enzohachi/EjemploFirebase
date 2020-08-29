package cl.grupo2.ejemplofirebase.login.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.grupo2.ejemplofirebase.login.domain.LoginUserPassUseCase
import cl.grupo2.ejemplofirebase.login.domain.UserAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import kotlinx.coroutines.launch
import java.lang.Exception

class LoginViewModel(
    private val loginUserPassUseCase: LoginUserPassUseCase
) : ViewModel(){
    private val liveData = MutableLiveData<LoginUiState>()

    fun getLiveData()=liveData

    fun doLogin (email : String, pass: String){
        liveData.postValue(LoginUiState.Loading)
        viewModelScope.launch {
            try{
                val result = loginUserPassUseCase.execute(email,pass)
                handleResult (result)
            } catch (exception: Exception){
                handleError (exception)
            }
        }
    }

    private fun handleError(exception: Exception) {
        if (exception is FirebaseAuthInvalidCredentialsException){
            liveData.postValue(LoginUiState.Invaliduser)
        }
        else{
            liveData.postValue(LoginUiState.Error(exception))
        }
    }

    private fun handleResult(result: UserAuth) {
        liveData.postValue(LoginUiState.Success(result))
    }
}

