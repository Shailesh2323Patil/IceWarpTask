package com.shailesh.icewarptask.domain.usecase.mapper

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.shailesh.icewarptask.domain.usecase.model.ErrorModel
import com.shailesh.icewarptask.domain.usecase.model.ErrorStatus
import com.shailesh.icewarptask.util.constants.AppConstants
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

/** * Class for parsing network error */
class CloudErrorMapper @Inject constructor(private val gson: Gson) {
    private var userDoesNoteExist = "User Does Not Exist"
    private var unauthorized = "UNAUTHORIZED"
    private var timeOut = "TIME OUT!!"
    private var checkConnection = "CHECK CONNECTION"
    private var somethingWentWrong = "Something Went Wrong"

    fun mapToDomainErrorException(throwable: Throwable?): ErrorModel {
        Log.e("CloudError", "Error " + throwable!!.localizedMessage)
        val errorModel: ErrorModel? =
            when (throwable) {
                // if throwable is an instance of HttpException
                // then attempt to parse error data from response body
                is HttpException -> {
                    // handle UNAUTHORIZED situation (when token expired)
                    if (throwable.code() == AppConstants.ERROR_CODE_401) {
                        ErrorModel(
                            unauthorized,
                            AppConstants.ERROR_CODE_401,
                            ErrorStatus.UNAUTHORIZED,
                            userDoesNoteExist
                        )
                    } else {
                        getHttpError(throwable.response()!!.errorBody())
                    }
                }


                // handle api call timeout error is
                is SocketTimeoutException -> {
                    ErrorModel(
                        timeOut,
                        0,
                        ErrorStatus.TIMEOUT,
                        somethingWentWrong
                    )
                }

                // handle connection error is
                is IOException -> {
                    ErrorModel(
                        checkConnection,
                        0,
                        ErrorStatus.NO_CONNECTION,
                        checkConnection
                    )
                }

                is UnknownHostException -> {
                    ErrorModel(
                        checkConnection,
                        0,
                        ErrorStatus.NO_CONNECTION,
                        checkConnection
                    )
                }

                else -> {
                    ErrorModel(
                        throwable.message,
                        0,
                        ErrorStatus.EMPTY_RESPONSE,
                        somethingWentWrong
                    )
                }
            }

        return errorModel!!
    }

    private fun getHttpError(body: ResponseBody?): ErrorModel {
        return try {
            val result = body?.string()
            val json = Gson().fromJson(result, JsonObject::class.java)
            val errorObject: Any = json.get("error")
            if (errorObject is String) {
                ErrorModel(
                    json.get("message").asString,
                    json.get("status_code").asInt,
                    ErrorStatus.BAD_RESPONSE,
                    json.get("error").asString
                )
            } else {
                ErrorModel(
                    json.get("message").asString,
                    json.get("status_code").asInt,
                    ErrorStatus.BAD_RESPONSE,
                    errorObject
                )
            }
        } catch (e: Throwable) {
            e.printStackTrace()
            ErrorModel(
                e.message,
                AppConstants.ERROR_CODE_404,
                ErrorStatus.NOT_DEFINED,
                somethingWentWrong
            )
        }
    }
}