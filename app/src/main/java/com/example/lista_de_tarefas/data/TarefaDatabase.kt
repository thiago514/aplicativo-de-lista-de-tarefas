package com.example.lista_de_tarefas.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Tarefa::class], version = 1, exportSchema = false)
abstract class TarefaDatabase: RoomDatabase() {

    abstract fun tarefaDao(): TarefaDao

    companion object {
        @Volatile
        private var Instance: TarefaDatabase? = null

        fun getDatabase(context: Context): TarefaDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context, TarefaDatabase::class.java, "tarefa_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}