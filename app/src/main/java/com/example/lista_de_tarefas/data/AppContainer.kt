package com.example.lista_de_tarefas.data

import android.content.Context

interface AppContainer {
    val tarefasRepository: TarefasRepository
}

class AppContainerImpl(private val context: Context) : AppContainer {
    override val tarefasRepository: TarefasRepository by lazy {
        OffilineTarefasRepository(TarefaDatabase.getDatabase(context).tarefaDao())
    }
}