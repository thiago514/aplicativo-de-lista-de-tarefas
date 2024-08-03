package com.example.lista_de_tarefas

import android.app.Application
import com.example.lista_de_tarefas.data.AppContainer
import com.example.lista_de_tarefas.data.AppContainerImpl

class TarefasApplication: Application() {
    lateinit var appContainer: AppContainer
    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainerImpl(this)
    }
}