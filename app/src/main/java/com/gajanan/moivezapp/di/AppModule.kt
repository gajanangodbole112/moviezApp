package com.gajanan.moivezapp.di

import android.app.Application
import androidx.room.Room
import com.gajanan.moivezapp.cache.database.MovieDatabase
import com.gajanan.moivezapp.netwoek.Endpoints
import com.gajanan.moivezapp.netwoek.RetrofitApiInterface
import com.gajanan.moivezapp.utils.Constants.ACCESS_TOKEN
import com.gajanan.moivezapp.utils.Constants.CONNECTION_TIMEOUT
import com.gajanan.moivezapp.utils.Constants.READ_TIMEOUT
import com.gajanan.moivezapp.utils.Constants.WRITE_TIMEOUT
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit() : Retrofit {
        val gson = GsonBuilder().serializeNulls().create()

        val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain ->
                val originalReq = chain.request()
                val newRequest = originalReq
                    .newBuilder()
                    .header("Authorization", "Bearer $ACCESS_TOKEN")
                    .build()
                chain.proceed(newRequest)
            })
            .connectTimeout(
                CONNECTION_TIMEOUT.toLong(),
                TimeUnit.SECONDS
            )
            .readTimeout(
                READ_TIMEOUT.toLong(),
                TimeUnit.SECONDS
            )
            .writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()


        return Retrofit.Builder()
            .baseUrl(Endpoints.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun forApi(retrofit: Retrofit): RetrofitApiInterface =
        retrofit.create(RetrofitApiInterface::class.java)

    @Provides
    @Singleton
    fun provideDatabase(app: Application): MovieDatabase =
        Room.databaseBuilder(app , MovieDatabase::class.java,"movie_db")
            .build()
}