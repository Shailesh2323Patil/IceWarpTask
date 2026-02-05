package com.shailesh.icewarptask.domain.usecase.model

data class ErrorModel(
    val message: String?,
    val code: Int?,
    var errorStatus: ErrorStatus,
    var errorInformation: Any?
) {
    constructor(errorStatus: ErrorStatus) : this(null, null, errorStatus, null)
}