package com.shailesh.icewarptask.util.rxjava

import com.shailesh.icewarptask.domain.usecase.model.ErrorModel
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

interface SchedulerProvider {
    fun io(): Scheduler
    fun ui(): Scheduler
}

class AppSchedulerProvider @Inject constructor() : SchedulerProvider {
    override fun io(): Scheduler = Schedulers.io()
    override fun ui(): Scheduler = AndroidSchedulers.mainThread()
}