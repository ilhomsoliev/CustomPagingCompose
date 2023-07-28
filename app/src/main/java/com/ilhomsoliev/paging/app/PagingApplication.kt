package com.ilhomsoliev.paging.app

import android.app.Application
import com.ilhomsoliev.paging.data.repository.Repository
import com.ilhomsoliev.paging.data.repository.RepositoryImpl
import com.ilhomsoliev.paging.presentation.viewmodel.MainViewModel
import com.ilhomsoliev.paging.data.network.BASE_URL
import com.ilhomsoliev.paging.data.network.ServerApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class PagingApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@PagingApplication)
            modules(
                listOf(
                    viewModelModule,
                    repositoryModule,
                    networkModule,
                )
            )
        }
    }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}
val repositoryModule = module {
    single<Repository> { RepositoryImpl(get()) }
}

val networkModule = module {
    single<ServerApi> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(
                        HttpLoggingInterceptor().setLevel(
                            HttpLoggingInterceptor.Level.BODY
                        )
                    )
                    .readTimeout(120, TimeUnit.SECONDS)
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .build()
            )
            .build()
            .create(ServerApi::class.java)
    }
}


