package cl.grupo2.ejemplofirebase.registro.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cl.grupo2.ejemplofirebase.R
import cl.grupo2.ejemplofirebase.databinding.FragmentRegistroUsuarioBinding
import cl.grupo2.ejemplofirebase.login.presentation.LoginUiState
import cl.grupo2.ejemplofirebase.registro.data.remote.FirebaseRegistroRepository
import cl.grupo2.ejemplofirebase.registro.domain.RegistraUsuarioUseCase
import cl.grupo2.ejemplofirebase.registro.domain.RegistroUsuario
import cl.grupo2.ejemplofirebase.utils.extensions.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegistroUsuarioFragment : Fragment (R.layout.fragment_registro_usuario){

    lateinit var binding : FragmentRegistroUsuarioBinding
    lateinit var viewModel: RegistroUsuarioViewModel
    lateinit var viewModelFactory: RegistroViewModelFactory

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDependencies()
        binding = FragmentRegistroUsuarioBinding.bind(view)
        setupLiveData()
        setuplistener()
    }

    private fun setupDependencies() {
        viewModelFactory =
            RegistroViewModelFactory(
                RegistraUsuarioUseCase(
                    FirebaseRegistroRepository(
                        FirebaseAuth.getInstance(),
                        FirebaseDatabase.getInstance()
                    )
                )
            )
        viewModel =
            ViewModelProvider(this,viewModelFactory).get(RegistroUsuarioViewModel::class.java)
    }

    private fun setupLiveData() {
        viewModel.getLiveData().observe(
            viewLifecycleOwner, Observer { handleState(it) })

    }

    private fun setuplistener() {
       binding.apply {
           btnRegistrarse.setOnClickListener{
               if (isAllValidInputs()){
                    viewModel.registrarUsuario(obtenerDatosUsuario())
               }
           }
           btnVolver.setOnClickListener{
               activity?.onBackPressed()
           }
       }
    }

    private fun obtenerDatosUsuario(): RegistroUsuario {
        binding.apply {
            return RegistroUsuario(
                etNombre.text.toString(),
                etRut.text.toString(),
                etEmail.text.toString(),
                etPassword.text.toString()
            )
        }

    }

    private fun isAllValidInputs(): Boolean {
        binding.apply {
            return etNombre.isValidNameInput("Ingrese nombre") ||
                    etEmail.isValidEmailInput("Ingrese correo valido")||
                    etRut.isValidRutInput("Ingrese un Rut Valido")||
                    etPassword.isValidPassInput("Ingrese contraseña con 6 o mas caracteres")||
                    etPasswordConform.isValidPassConfirm(etPassword.text.toString(),"La contraseña debe coincidir con la anterior")
        }
    }


    private fun handleState(estado: RegistroUiState?) {
        when(estado){
            is RegistroUiState.LoadingRegistroUiState -> showLoading()
            is RegistroUiState.InvalidEmailRegistroUiState -> showInvalidEmail()
            is RegistroUiState.SuccessRegistroUiState -> showSuccessRegistro()
            is RegistroUiState.ErrorRegistroUiState -> showErrorRegistro()
        }
    }

    private fun showErrorRegistro() {
        alert("Error en el registro")
    }

    private fun showSuccessRegistro() {
        alert("Registro Exitoso")
    }

    private fun showInvalidEmail() {
        alert("Error en correo electronico")
    }

    private fun showLoading() {
        alert("Esta Lenta la carga")
    }

}