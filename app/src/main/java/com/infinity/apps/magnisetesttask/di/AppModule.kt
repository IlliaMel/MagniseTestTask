package com.infinity.apps.magnisetesttask.di

import android.content.Context
import androidx.room.Room
import com.infinity.apps.magnisetesttask.data.api.AuthApi
import com.infinity.apps.magnisetesttask.data.api.HistoricalApi
import com.infinity.apps.magnisetesttask.data.api.InstrumentApi
import com.infinity.apps.magnisetesttask.data.db.converter.Converters
import com.infinity.apps.magnisetesttask.data.db.dao.InstrumentDataDao
import com.infinity.apps.magnisetesttask.data.db.impl.AppDatabase
import com.infinity.apps.magnisetesttask.data.local.manager.TimeManagerImpl
import com.infinity.apps.magnisetesttask.data.local.repository.InstrumentDataCacheRepositoryImpl
import com.infinity.apps.magnisetesttask.data.local.repository.SecureCacheRepositoryImpl
import com.infinity.apps.magnisetesttask.data.local.repository.TokenCacheRepositoryImpl
import com.infinity.apps.magnisetesttask.data.remote.interceptor.AuthInterceptor
import com.infinity.apps.magnisetesttask.data.remote.repository.AuthRepositoryImpl
import com.infinity.apps.magnisetesttask.data.remote.source.HistoricalDataSourceImpl
import com.infinity.apps.magnisetesttask.data.remote.source.InstrumentsDataSourceImpl
import com.infinity.apps.magnisetesttask.data.remote.source.RealTimeDataSourceImpl
import com.infinity.apps.magnisetesttask.domain.local.manager.ITimeManager
import com.infinity.apps.magnisetesttask.domain.local.repository.IInstrumentDataCacheRepository
import com.infinity.apps.magnisetesttask.domain.local.repository.ISecureCacheRepository
import com.infinity.apps.magnisetesttask.domain.local.repository.ITokenCacheRepository
import com.infinity.apps.magnisetesttask.domain.remote.repository.IAuthRepository
import com.infinity.apps.magnisetesttask.domain.remote.source.IHistoricalDataSource
import com.infinity.apps.magnisetesttask.domain.remote.source.IInstrumentsDataSource
import com.infinity.apps.magnisetesttask.domain.remote.source.IRealTimeDataSource
import com.squareup.moshi.Moshi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.websocket.WebSockets
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named

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
    fun provideHttpClient () : HttpClient {
        return HttpClient(CIO) {
            install(WebSockets)
            engine {
                https
            }
        }
    }

    @Provides
    fun provideDatabase(@ApplicationContext context: Context, moshi : Moshi): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "magnise_database"
        ).addTypeConverter(Converters(moshi)).build()
    }

    @Provides
    fun provideInstrumentDataDao(database: AppDatabase): InstrumentDataDao {
        return database.instrumentDataDao()
    }

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

    @Provides
    fun provideInstrumentApi(retrofit: Retrofit): InstrumentApi = retrofit.create(InstrumentApi::class.java)

    @Provides
    @Named("wssUri")
    fun provideWssUri(): String = "platform.fintacharts.com/api/streaming/ws/v1/realtime"

}

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class RepositoryModuleBinder  {

    @Binds
    abstract fun bindTimeManagerImpl(
        timeManagerImpl: TimeManagerImpl
    ) : ITimeManager

    @Binds
    abstract fun bindInstrumentDataCacheRepositoryImpl(
        instrumentDataCacheRepositoryImpl: InstrumentDataCacheRepositoryImpl
    ) : IInstrumentDataCacheRepository

    @Binds
    abstract fun bindRealTimeDataSourceImpl(
        realTimeDataSourceImpl: RealTimeDataSourceImpl
    ): IRealTimeDataSource

    @Binds
    abstract fun bindInstrumentsDataSource(
        instrumentsDataSourceImpl: InstrumentsDataSourceImpl
    ): IInstrumentsDataSource

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