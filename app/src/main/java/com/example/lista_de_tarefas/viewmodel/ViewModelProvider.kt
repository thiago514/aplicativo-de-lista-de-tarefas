package com.example.lista_de_tarefas.viewmodel

import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.lista_de_tarefas.TarefasApplication
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory

object ViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            ListaDeTarefasViewModel(
                TarefasApplication().appContainer.tarefasRepository
            )
        }
    }
}

fun CreationExtras.TarefasApplication(): TarefasApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as TarefasApplication)