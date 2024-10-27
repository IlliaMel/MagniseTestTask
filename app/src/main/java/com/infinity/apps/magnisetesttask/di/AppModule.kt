package com.infinity.apps.magnisetesttask.di

import com.infinity.apps.magnisetesttask.data.api.AuthApi
import com.infinity.apps.magnisetesttask.data.api.HistoricalApi
import com.infinity.apps.magnisetesttask.data.local.repository.SecureCacheRepositoryImpl
import com.infinity.apps.magnisetesttask.data.local.repository.TokenCacheRepositoryImpl
import com.infinity.apps.magnisetesttask.data.remote.interceptor.AuthInterceptor
import com.infinity.apps.magnisetesttask.data.remote.repository.AuthRepositoryImpl
import com.infinity.apps.magnisetesttask.data.remote.source.HistoricalDataSourceImpl
import com.infinity.apps.magnisetesttask.domain.local.repository.ISecureCacheRepository
import com.infinity.apps.magnisetesttask.domain.local.repository.ITokenCacheRepository
import com.infinity.apps.magnisetesttask.domain.remote.repository.IAuthRepository
import com.infinity.apps.magnisetesttask.domain.remote.source.IHistoricalDataSource
import com.squareup.moshi.Moshi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModuleBinder {

    @Binds
    abstract fun bindSecureCacheRepository(
        secureCacheRepository: SecureCacheRepositoryImpl
    ): ISecureCacheRepository

}

@Module
@InstallIn(ActivityRetainedComponent::class)
object NetworkModule {

    private const val BASE_URI = "https://platform.fintacharts.com/"

    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Provides
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).build()
    }

    @Provides
    fun provideRetrofit(moshi: Moshi, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URI)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideAuthApi(retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)

    @Provides
    fun provideHistoricalApi(retrofit: Retrofit): HistoricalApi = retrofit.create(HistoricalApi::class.java)

}

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class RepositoryModuleBinder  {

    @Binds
    abstract fun bindTokenCacheRepository(
        tokenCacheRepositoryImpl: TokenCacheRepositoryImpl
    ): ITokenCacheRepository

    @Binds
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): IAuthRepository

    @Binds
    abstract fun bindHistoricalDataSource(
        historicalDataSourceImpl: HistoricalDataSourceImpl
    ): IHistoricalDataSource

}