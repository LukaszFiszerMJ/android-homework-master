package com.infullmobile.androidhomework.di

import dagger.Module
import dagger.Provides
import com.google.gson.Gson
import com.infullmobile.androidhomework.BuildConfig
import com.infullmobile.androidhomework.repository.network.ForecastRemoteService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import android.content.Context
import com.infullmobile.androidhomework.repository.network.utils.ErrorUtils
import com.infullmobile.androidhomework.repository.network.utils.RxErrorHandlingCallAdapterFactory
import okhttp3.Cache

@Module(includes = [ContextModule::class])
class NetworkModule {
    private val unitParam = "units"
    private val appidParam = "APPID"
    @Provides
    @WeatherScope
    fun provideHttpCache(application: Context): Cache {
        val cacheSize = 10L * 1024 * 1024
        return Cache(application.cacheDir, cacheSize)
    }
    
    @Provides
    @WeatherScope
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @WeatherScope
    fun provideOkHttpClient(cache:Cache): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.cache(cache)

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        client.addInterceptor{ chain ->
            val original = chain.request()
            val builder = original.newBuilder()
            val url = original.url().newBuilder()
                    .addQueryParameter(appidParam, BuildConfig.OPEN_WEATHER_API_KEY)
                    .addQueryParameter(unitParam, BuildConfig.OPEN_WEATHER_UNIT)
                    .build()

            builder.header("Accept", "application/json")
            builder.url(url)

            val request = builder.method(original.method(), original.body())
                    .build()

            chain.proceed(request)
        }.addNetworkInterceptor(logging)
        return client.build()
    }

    @Provides
    @WeatherScope
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient, errorUtils: ErrorUtils): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create(errorUtils))
                .baseUrl(BuildConfig.BASE_URL)
                .client(okHttpClient)
                .build()
    }

    @Provides
    @WeatherScope
    fun provideForecastRemoteService(retrofit: Retrofit) :ForecastRemoteService{
        return retrofit.create(ForecastRemoteService::class.java)
    }

    @Provides
    @WeatherScope
    fun provideErrorUtil(gson: Gson) = ErrorUtils(gson)


}