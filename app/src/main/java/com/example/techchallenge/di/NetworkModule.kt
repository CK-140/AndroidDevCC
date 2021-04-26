package com.example.techchallenge.di

import com.example.techchallenge.BuildConfig
import com.example.techchallenge.data.RestApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideOkhttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor {
            val request = it.request().newBuilder()
                .header("Content-Type", "application/json")
                .header("Cookie", BuildConfig.COOKIE)
                .build()
            it.proceed(request)
        }.build()

    @Provides
    fun provideRetrofitBuilder(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun provideRestApi(retrofit: Retrofit): RestApi = retrofit.create(RestApi::class.java)
}