package com.stoffe.githubrep.api.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.stoffe.githubrep.api.GithubService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Credentials
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

const val GITHUB_USER_NAME = "Github Username here"
const val GITHUB_TOKEN = "Personal Token here"

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Provides
    @Singleton
    fun provideGithubService(
        moshi: Moshi,
        okHttpClient: OkHttpClient,
    ): GithubService =
        Retrofit.Builder()
            .run {
                baseUrl("https://api.github.com")
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .client(okHttpClient)
                    .build()
            }.create(GithubService::class.java)

    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {

        /*I would add the loggingInterceptor inside a if(BuildConfig.DEBUG)
         but there is currently a bug i n the KSP compiler not creating the generated file for this
         */

        return OkHttpClient
            .Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor { chain ->
                val requestBuilder = chain.request().newBuilder()
                requestBuilder.addHeader(
                    "Authorization",
                    Credentials.basic(GITHUB_USER_NAME, GITHUB_TOKEN)
                )
                chain.proceed(requestBuilder.build())
            }
            .build()
    }
}