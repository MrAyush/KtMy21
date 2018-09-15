package com.example.ayushgupta.ktmy21.Model

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.response.CustomTypeAdapter
import com.apollographql.apollo.response.CustomTypeValue
import com.example.ayushgupta.ktmy21.type.CustomType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.net.URI

class GetGitClient {
    fun getApolloClient(): ApolloClient {
        val stringUrl = "https://api.github.com/graphql"
        val log = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val token = "YOUR_TOKEN"
        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(log)
                .addInterceptor{
                    val original = it.request()
                    val builder = original.newBuilder().method(original.method(),
                            original.body())
                    builder.addHeader("Authorization"
                            , "Bearer $token")
                    it.proceed(builder.build())
                }.build()
        return ApolloClient.builder()
                .serverUrl(stringUrl)
                .okHttpClient(okHttpClient)
                .addCustomTypeAdapter(CustomType.URI, object: CustomTypeAdapter<URI> {
                    override fun encode(value: URI): CustomTypeValue<*> {
                        return CustomTypeValue.GraphQLString(value.toString())
                    }

                    override fun decode(value: CustomTypeValue<*>): URI {
                        return URI.create(value.toString())
                    }

                })
                .build()
    }
}