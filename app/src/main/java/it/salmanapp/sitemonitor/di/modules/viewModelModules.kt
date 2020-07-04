package it.salmanapp.sitemonitor.di.modules

import it.salmanapp.sitemonitor.ui.home.HomeViewModel
import it.salmanapp.sitemonitor.ui.list.ListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val viewModelModule: Module = module{
   viewModel{HomeViewModel(get())}
   viewModel{ListViewModel(get())}
}