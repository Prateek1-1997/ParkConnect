package com.vehicle.owner.network

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.vehicle.owner.data.local.ISharedPreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideInterceptor(sharedPreference: ISharedPreference) =
        Interceptor { chain ->
            val builder = chain.request().newBuilder()
            builder.addHeader("accept", "application/json")
            builder.addHeader("Content-Type", "application/json")
            sharedPreference.getAuthToken().isNotBlank().let {
                builder.addHeader("Authorization", "Bearer ${sharedPreference.getAuthToken()}")
            }
            val response = chain.proceed(
                builder.build(),
            )
            response
        }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        headersInterceptor: Interceptor,
        logging: HttpLoggingInterceptor,
        curlLoggingInterceptor: CurlLoggingInterceptor,
        @ApplicationContext context: Context,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(90, TimeUnit.SECONDS)
            .connectTimeout(
                90,
                TimeUnit.SECONDS,
            )
            .writeTimeout(90, TimeUnit.SECONDS)
            .addInterceptor(headersInterceptor)
            .addNetworkInterceptor(logging)
            .addInterceptor(ChuckerInterceptor(context))
            .addInterceptor(curlLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .serializeNulls() // To allow sending null values
            .create()
    }

    @Provides
    @Singleton
    fun provideCurlLoggingInterceptor(): CurlLoggingInterceptor {
        return CurlLoggingInterceptor()
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://backend.sadhakah.com")
            .build()
}
