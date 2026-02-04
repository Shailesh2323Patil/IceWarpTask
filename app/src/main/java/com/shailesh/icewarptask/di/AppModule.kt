package com.shailesh.icewarptask.di

import android.content.Context
import com.google.gson.Gson
import com.shailesh.icewarptask.BuildConfig
import com.shailesh.icewarptask.data.remote.IceWarpApiService
import com.shailesh.icewarptask.data.repository.ChannelRepositoryImpl
import com.shailesh.icewarptask.data.repository.LoginRepositoryImpl
import com.shailesh.icewarptask.data.source.DataSource
import com.shailesh.icewarptask.data.source.cache.CacheDataSource
import com.shailesh.icewarptask.data.source.cloud.RemoteDataSource
import com.shailesh.icewarptask.data.source.local.LocalDataSource
import com.shailesh.icewarptask.domain.repository.ChannelRepository
import com.shailesh.icewarptask.domain.repository.LoginRepository
import com.shailesh.icewarptask.util.network.getUnsafeOkHttpClient
import com.shailesh.icewarptask.util.rxjava.AppDispatcherProvider
import com.shailesh.icewarptask.util.rxjava.AppSchedulerProvider
import com.shailesh.icewarptask.util.rxjava.DispatcherProvider
import com.shailesh.icewarptask.util.rxjava.SchedulerProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient {
        try {
            val connectionTimeOut90Seconds: Long = 90
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            if (BuildConfig.DEBUG) {
                val client =
                    getUnsafeOkHttpClient().addNetworkInterceptor(httpLoggingInterceptor)
                        .connectTimeout(connectionTimeOut90Seconds, TimeUnit.SECONDS)
                        .writeTimeout(connectionTimeOut90Seconds, TimeUnit.SECONDS)
                        .readTimeout(connectionTimeOut90Seconds, TimeUnit.SECONDS)
                        .retryOnConnectionFailure(false).cache(null)

                return client.build()
            } else {
                val client = OkHttpClient.Builder()
                    .connectTimeout(connectionTimeOut90Seconds, TimeUnit.SECONDS)
                    .writeTimeout(connectionTimeOut90Seconds, TimeUnit.SECONDS)
                    .readTimeout(connectionTimeOut90Seconds, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(false).cache(null)

                return client.build()
            }
        } catch (exception: Exception) {
            throw RuntimeException(exception)
        }
    }

    @Provides
    fun createRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
    }

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): IceWarpApiService {
        return retrofit.create(IceWarpApiService::class.java)
    }

    @Provides
    fun provideRemoteDataSource(apiService: IceWarpApiService): DataSource.Remote {
        return RemoteDataSource(apiService)
    }

    @Provides
    fun provideLocalDataSource(@ApplicationContext context: Context): DataSource.Local {
        return LocalDataSource(context)
    }

    @Provides
    fun provideCacheDataSource(): DataSource.Cache {
        return CacheDataSource()
    }

    @Provides
    @Singleton
    fun provideChannelRepository(
        remoteDataSource: DataSource.Remote,
        localDataSource: DataSource.Local,
        cacheDataSource: DataSource.Cache
    ): ChannelRepository {
        return ChannelRepositoryImpl(remoteDataSource, localDataSource, cacheDataSource)
    }

    @Provides
    @Singleton
    fun provideLoginRepository(
        remoteDataSource: DataSource.Remote,
        localDataSource: DataSource.Local,
        cacheDataSource: DataSource.Cache
    ): LoginRepository {
        return LoginRepositoryImpl(remoteDataSource, localDataSource, cacheDataSource)
    }

    @Provides
    @Singleton
    fun providesGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideSchedulerProvider(): SchedulerProvider {
        return AppSchedulerProvider()
    }

    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider {
        return AppDispatcherProvider()
    }
}