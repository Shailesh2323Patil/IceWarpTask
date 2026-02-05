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
                            message = unauthorized,
                            code = AppConstants.ERROR_CODE_401,
                            errorStatus = ErrorStatus.UNAUTHORIZED,
                            errorInformation = userDoesNoteExist
                        )
                    } else {
                        getHttpError(throwable.response()!!.errorBody())
                    }
                }

                // handle api call timeout error is
                is SocketTimeoutException -> {
                    ErrorModel(
                        message = timeOut,
                        code = 0,
                        errorStatus = ErrorStatus.TIMEOUT,
                        errorInformation = somethingWentWrong
                    )
                }

                // handle connection error is
                is IOException -> {
                    ErrorModel(
                        message = checkConnection,
                        code = 0,
                        errorStatus = ErrorStatus.NO_CONNECTION,
                        errorInformation = checkConnection
                    )
                }

                is UnknownHostException -> {
                    ErrorModel(
                        message = checkConnection,
                        code = 0,
                        errorStatus = ErrorStatus.NO_CONNECTION,
                        errorInformation = checkConnection
                    )
                }

                else -> {
                    ErrorModel(
                        message = throwable.message,
                        code = 0,
                        errorStatus = ErrorStatus.EMPTY_RESPONSE,
                        errorInformation = somethingWentWrong
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
            ErrorModel(
                message = json.get("message").asString,
                code = json.get("status_code").asInt,
                errorStatus = ErrorStatus.BAD_RESPONSE,
                errorInformation = errorObject
            )
        } catch (e: Throwable) {
            e.printStackTrace()
            ErrorModel(
                message = e.message,
                code = AppConstants.ERROR_CODE_404,
                errorStatus = ErrorStatus.NOT_DEFINED,
                errorInformation = somethingWentWrong
            )
        }
    }
}