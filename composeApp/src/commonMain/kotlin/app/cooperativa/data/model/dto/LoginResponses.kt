package app.cooperativa.data.model.dto

@kotlinx.serialization.Serializable
data class LoginRequest(
    val user_name: String,
    val pass_code: String
)

@kotlinx.serialization.Serializable
data class LoginSuccessPayload(
    val access_token: String
)

@kotlinx.serialization.Serializable
data class LoginSuccessWrapper(
    val Ok: LoginSuccessPayload? = null,
    val Err: LoginErrorPayload? = null
)

@kotlinx.serialization.Serializable
data class LoginErrorPayload(
    val message: String
)