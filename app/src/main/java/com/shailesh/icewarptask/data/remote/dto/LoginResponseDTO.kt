package com.shailesh.icewarptask.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.shailesh.icewarptask.ui.login.model.User
import kotlin.String

data class LoginResponseDTO(
    @SerializedName("authorized") var authorized: Boolean? = null,
    @SerializedName("token") var token: String? = null,
    @SerializedName("host") var host: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("ok") var ok: Boolean? = null
) {
    fun toUser(): User = User(token = token, host = host, email = email)
}