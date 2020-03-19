package com.salvaperez.maaps.di

import android.app.Application
import com.salvaperez.maaps.*
import com.salvaperez.maaps.data.datasource.RemoteTransportDataSource
import com.salvaperez.maaps.data.repository.TransportDataRepository
import com.salvaperez.maaps.data.api.MaapsApi
import com.salvaperez.maaps.domain.repository.TransportRepository
import com.salvaperez.maaps.domain.use_case.GetTransportUseCase
import com.salvaperez.maaps.presentation.detail.DetailActivity
import com.salvaperez.maaps.presentation.detail.DetailViewModel
import com.salvaperez.maaps.presentation.main.MaapsViewModel
import com.salvaperez.maaps.presentation.main.MapsActivity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

fun Application.initDI() {
    startKoin {
        androidLogger()
        androidContext(this@initDI)
        modules(listOf(
            dataModule,
            transports
        ))
    }
}

val transports = module(override = true) {
    scope(named<MapsActivity>()) {
        viewModel { MaapsViewModel(get(), get()) }
        scoped { GetTransportUseCase(get()) }
    }

    scope(named<DetailActivity>()) {
        viewModel { DetailViewModel() }
    }

    single { RemoteTransportDataSource(get()) }
    single<TransportRepository> {
        TransportDataRepository(
            get()
        )
    }
}

val dataModule = module(override = true) {
    single<CoroutineDispatcher> { Dispatchers.Main }
    single<MaapsApi> { get<Retrofit>().create() }

    val apiTimeOutInSeconds = 60L

    single {
        Retrofit.Builder()
            .apply {
                addConverterFactory(GsonConverterFactory.create())
                baseUrl(BuildConfig.API_URL)
                client(get())
            }
            .build()
    }

    single {
        OkHttpClient.Builder()
            .connectTimeout(apiTimeOutInSeconds, TimeUnit.SECONDS)
            .readTimeout(apiTimeOutInSeconds, TimeUnit.SECONDS)
            .writeTimeout(apiTimeOutInSeconds, TimeUnit.SECONDS)
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    factory {
        val interceptor = HttpLoggingInterceptor()
        interceptor.apply {
            level = if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE
        }
        return@factory interceptor
    }
}
