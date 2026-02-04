package com.shailesh.icewarptask.data.remote

import com.shailesh.icewarptask.data.remote.dto.ChannelResponseDTO
import com.shailesh.icewarptask.data.remote.dto.LoginResponseDTO
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface IceWarpApiService {
    @FormUrlEncoded
    @POST("iwauthentication.login.plain")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Single<LoginResponseDTO>

    @FormUrlEncoded
    @POST("channels.list")
    suspend fun getChannels(
        @Field("token") token: String,
        @Field("include_unread_count") includeUnreadCount: Boolean = true,
        @Field("exclude_members") excludeMembers: Boolean = true,
        @Field("include_permissions") includePermissions: Boolean = false
    ): ChannelResponseDTO
}