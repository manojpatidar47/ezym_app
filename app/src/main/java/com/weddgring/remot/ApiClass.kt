package com.ezyloop.remot

import android.content.Context
import android.util.Log
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class ApiClass {
    private val context: Context? = null

    companion object {
        var baseurl = "http://15.206.66.117/ezymp/index.php/api/"
        var baseurl_profile_image = "https://www.qitstaging.com/iot/uploads/images/customers/"
        var baseurl_product_image = "https://www.qitstaging.com/iot/uploads/images/products/"

        private var retrofit: Retrofit? = null
        // public static String token="";
        private val TAG = ApiClass::class.java.simpleName
        private const val REQUEST_TIMEOUT = 60
        private var okHttpClient: OkHttpClient? = null
        fun getRetrofit(): Retrofit? {
            if (retrofit == null) {

                retrofit = Retrofit.Builder().baseUrl(baseurl)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(
                        GsonBuilder()
                            .setLenient()
                            .create()))
                    .client(getokHTTPClient()).build()
            }
            return retrofit
        }

        fun getApiServices(context: Context?): ApiServices {
            try { //   token= Preferences.readString(context,Preferences.token,"");
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return getRetrofit()!!.create(
                ApiServices::class.java
            )
        }

        /* public static OkHttpClient getokHTTPClient(){
         final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                 .readTimeout(60, TimeUnit.SECONDS)
                 .connectTimeout(60, TimeUnit.SECONDS)
                 .build();
         return okHttpClient;
     }*/
        private fun getokHTTPClient(): OkHttpClient {
            val httpClient = OkHttpClient().newBuilder()
                .connectTimeout(
                    REQUEST_TIMEOUT.toLong(),
                    TimeUnit.SECONDS
                )
                .readTimeout(
                    REQUEST_TIMEOUT.toLong(),
                    TimeUnit.SECONDS
                )
                .writeTimeout(
                    REQUEST_TIMEOUT.toLong(),
                    TimeUnit.SECONDS
                )
                .followRedirects(false)
                .followSslRedirects(false)
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(interceptor)
            httpClient.addInterceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .method(original.method(), original.body())
                    .build()
                val response = chain.proceed(request)
                Log.d("MyApp", "Code : " + response.code())
                if (response.code() == 401) { // Magic is here ( Handle the error as your way )
                    response
                } else response
            }
            /* httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("Request-Type", "Android")
                        .addHeader("Content-Type", "application/json");

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });*/return httpClient.build().also { okHttpClient = it }
        }
    }
}