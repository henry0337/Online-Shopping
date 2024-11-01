package com.henry.onlineshopping.module

import android.content.Context
import com.henry.onlineshopping.R
import com.henry.onlineshopping.annotation.BaseUrl
import com.henry.onlineshopping.utility.constant.NetworkConstant
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.security.KeyStore
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @BaseUrl
    fun baseBackendURL() = NetworkConstant.HTTPS_URL

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(context: Context, baseUrl: String): OkHttpClient =
        if (baseUrl.startsWith("https")) {
            val certInput = context.resources.openRawResource(R.raw.backend)
            val sslContext = SSLContext.getInstance("TLS")

            val keystore = KeyStore.getInstance("PKCS12").apply {
                load(certInput, "23370000".toCharArray())
            }

            val trustManagerFactory =
                TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm()).apply {
                    init(keystore)
                }

            val trustManagers = trustManagerFactory.trustManagers

            sslContext.init(null, trustManagers, null)

            OkHttpClient.Builder()
                .sslSocketFactory(sslContext.socketFactory, trustManagers[0] as X509TrustManager)
                .build()
        } else {
            OkHttpClient.Builder().build()
        }

    @Provides
    @Singleton
    fun provideRetrofit(
        context: Context,
        moshi: Moshi,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseBackendURL())
        .client(provideOkHttpClient(context, baseBackendURL()))
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
}