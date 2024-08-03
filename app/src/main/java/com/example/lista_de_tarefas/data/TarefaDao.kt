package com.example.lista_de_tarefas.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TarefaDao {

    @Query("SELECT * from tarefas ORDER BY concluida ASC")
    fun getAllTarefas(): Flow<List<Tarefa>>

    @Query("SELECT * from tarefas WHERE id = :id")
    fun getTarefa(id: Int): Flow<Tarefa>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(tarefa: Tarefa)

    @Update
    suspend fun update(tarefa: Tarefa)

    @Delete
    suspend fun delete(tarefa: Tarefa)
}