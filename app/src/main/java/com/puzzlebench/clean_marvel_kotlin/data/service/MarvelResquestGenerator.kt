package com.puzzlebench.clean_marvel_kotlin.data.service

import com.puzzlebench.clean_marvel_kotlin.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MarvelResquestGenerator {
    companion object {
        private const val PRIVATE_API_KEY_ARG = "hash"
        private const val PUBLIC_API_KEY_ARG = "apikey"
        private const val TS = "ts"
        private const val TS_VALUE = "1"
    }

    private val marvelKeyFormula = "$TS_VALUE${BuildConfig.PRIVATE_API_KEY_VALUE}${BuildConfig.PUBLIC_API_KEY_VALUE}"

    private val httpClient = OkHttpClient.Builder().addInterceptor { chain ->
        val defaultRequest = chain.request()

        val defaultHttpUrl = defaultRequest.url()

        val marvelKeyFormulaToMd5 = MD5.compute(marvelKeyFormula.toByteArray()).toHexString()

        val httpUrl = defaultHttpUrl.newBuilder()
                .addQueryParameter(PUBLIC_API_KEY_ARG, BuildConfig.PUBLIC_API_KEY_VALUE)
                .addQueryParameter(PRIVATE_API_KEY_ARG, marvelKeyFormulaToMd5)
                .addQueryParameter(TS, TS_VALUE)
                .build()

        val requestBuilder = defaultRequest.newBuilder().url(httpUrl)

        chain.proceed(requestBuilder.build())
    }

    private val builder = Retrofit.Builder()
            .baseUrl(BuildConfig.MARVEL_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())

    fun <S> createService(serviceClass: Class<S>): S {
        val retrofit = builder.client(httpClient.build()).build()
        return retrofit.create(serviceClass)
    }
}
