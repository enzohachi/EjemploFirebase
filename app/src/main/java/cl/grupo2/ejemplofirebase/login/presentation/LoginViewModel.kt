package cl.grupo2.ejemplofirebase.login.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.grupo2.ejemplofirebase.login.domain.LoginUserPassUseCase

class LoginViewModel(
    private val loginUserPassUseCase: LoginUserPassUseCase
) : ViewModel(){
    private val liveData = MutableLiveData<LoginUiState>()

    fun getLiveData()=liveData

    fun doLogin (email : String, pass: String){
        liveData.postValue(LoginUiState.Loading)
        viewModelScope

        }
    }

