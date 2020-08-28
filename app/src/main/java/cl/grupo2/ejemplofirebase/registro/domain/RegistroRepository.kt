package cl.grupo2.ejemplofirebase.registro.domain

interface RegistroRepository {
    suspend fun registrarUsuario (registroUsuario: RegistroUsuario): Boolean
}