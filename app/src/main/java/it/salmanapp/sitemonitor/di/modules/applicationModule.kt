package it.salmanapp.sitemonitor.di.modules

import it.salmanapp.sitemonitor.logic.data.database.PageDatabase
import it.salmanapp.sitemonitor.logic.data.repository.pageRepository.PageRepository
import org.koin.dsl.module

val applicationModule = module{
    //Put here dependencies

    single {
        this
    }

    single {
        PageDatabase.getInstance(get()).pageDao()
    }

    single{
        PageRepository(get())
    }
}