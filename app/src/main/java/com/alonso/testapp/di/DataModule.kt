package com.alonso.testapp.di

import com.alonso.testapp.data.api.ApiService
import com.alonso.testapp.data.api.NetworkClient
import com.alonso.testapp.data.impl.TrainingRepositoryImpl
import com.alonso.testapp.data.sound.ToneBeepPlayer
import com.alonso.testapp.domain.repo.TrainingRepository
import com.alonso.testapp.utils.sound.SoundPlayer
import org.koin.dsl.module

val dataModule = module {
    single<ApiService> {
        NetworkClient.api
    }

    single<TrainingRepository> {
        TrainingRepositoryImpl(get())
    }
    single<SoundPlayer> {
        ToneBeepPlayer()
    }
}