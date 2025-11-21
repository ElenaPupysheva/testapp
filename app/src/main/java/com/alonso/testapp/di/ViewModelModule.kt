package com.alonso.testapp.di

import com.alonso.testapp.domain.StartTrainingUseCase
import com.alonso.testapp.presentation.MainViewModel
import com.alonso.testapp.presentation.TrainingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    single { StartTrainingUseCase() }

    viewModel<MainViewModel>{
        MainViewModel(get())
    }
    viewModel<TrainingViewModel> {
        TrainingViewModel()
    }
}