package cl.grupo2.ejemplofirebase.login.domain

interface AutentificacionRepository {
    suspend fun doLogin (email: String, pass: String) : UserAuth
}