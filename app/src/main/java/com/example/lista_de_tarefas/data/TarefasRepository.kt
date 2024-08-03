package com.example.lista_de_tarefas.data

import kotlinx.coroutines.flow.Flow

interface TarefasRepository {

    fun getAllTarefas(): Flow<List<Tarefa>>

    fun getTarefa(id: Int): Flow<Tarefa>

    suspend fun insertTarefa(tarefa: Tarefa)

    suspend fun updateTarefa(tarefa: Tarefa)

    suspend fun deleteTarefa(tarefa: Tarefa)

}