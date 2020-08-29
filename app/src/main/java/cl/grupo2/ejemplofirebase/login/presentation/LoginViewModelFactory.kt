package cl.grupo2.ejemplofirebase.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cl.grupo2.ejemplofirebase.login.domain.LoginUserPassUseCase

class LoginViewModelFactory (
    private val loginUserPassUseCase: LoginUserPassUseCase
) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(LoginUserPassUseCase::class.java)
            .newInstance(loginUserPassUseCase)
    }
}
