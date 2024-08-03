package com.example.lista_de_tarefas.data

import kotlinx.coroutines.flow.Flow

class OffilineTarefasRepository(private val tarefaDao: TarefaDao) : TarefasRepository {

    override fun getAllTarefas(): Flow<List<Tarefa>> = tarefaDao.getAllTarefas()

    override fun getTarefa(id: Int): Flow<Tarefa> = tarefaDao.getTarefa(id)

    override suspend fun insertTarefa(tarefa: Tarefa) = tarefaDao.insert(tarefa)

    override suspend fun updateTarefa(tarefa: Tarefa) = tarefaDao.update(tarefa)

    override suspend fun deleteTarefa(tarefa: Tarefa) = tarefaDao.delete(tarefa)
}