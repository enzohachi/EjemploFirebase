package cl.grupo2.ejemplofirebase.registro.domain

class RegistraUsuarioUseCase (
    private val registroRepository: RegistroRepository
){
    suspend fun execute (registroUsuario: RegistroUsuario): Boolean =
        registroRepository.registrarUsuario(registroUsuario)
}