package com.example.lista_de_tarefas.viewmodel

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.lista_de_tarefas.R
import com.example.lista_de_tarefas.models.Tarefa
import com.example.lista_de_tarefas.ui.views.InitialScreenAction
import com.example.lista_de_tarefas.ui.views.InitialScreenUiState
import com.example.lista_de_tarefas.ui.views.InsertTarefaUiState
import com.example.lista_de_tarefas.ui.views.ListaDeTarefasUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ListaDeTarefasViewModel : ViewModel() {

    private val _listaDeTarefasUiState: MutableStateFlow<ListaDeTarefasUiState> =
        MutableStateFlow(ListaDeTarefasUiState(emptyList()))

    val listaDeTarefasUiState: StateFlow<ListaDeTarefasUiState> =
        _listaDeTarefasUiState.asStateFlow()

    private val _insertTarefaUiState: MutableStateFlow<InsertTarefaUiState> =
        MutableStateFlow(InsertTarefaUiState())

    val insertTarefaUiState: StateFlow<InsertTarefaUiState> =
        _insertTarefaUiState.asStateFlow()

    private val _initialScreenUiState: MutableStateFlow<InitialScreenUiState> =
        MutableStateFlow(
            InitialScreenUiState(
                "Lista de Tarefas",
                R.drawable.baseline_add_24,
                "Inserir Tarefa"
            )
        )

    val initialScreenUiState: StateFlow<InitialScreenUiState> =
        _initialScreenUiState.asStateFlow()

    private var editTarefa: Boolean = false
    private var tarefaToEdit: Tarefa = Tarefa(
        "",
        "",
        false
    )

    fun fabAction(navCotroller: NavController) {
        if (_initialScreenUiState.value.title == "Lista de Tarefas") {
            _initialScreenUiState.update {
                it.copy(
                    title = "Inserir Tarefa",
                    icon = R.drawable.baseline_check_24,
                    iconContentDescription = "Confirmar"
                )
            }
            navCotroller.navigate(InitialScreenAction.INSERT_TAREFA.name)
        } else {
            if (editTarefa) {
                val tarefas = _listaDeTarefasUiState.value.tarefas.toMutableList()
                val pos = tarefas.indexOf(tarefaToEdit)
                tarefas.remove(tarefaToEdit)
                tarefas.add(
                    pos, tarefaToEdit.copy(
                        titulo = _insertTarefaUiState.value.titulo,
                        descricao = _insertTarefaUiState.value.descricao,
                        concluida = _insertTarefaUiState.value.concluida
                    )
                )
                _listaDeTarefasUiState.update {
                    it.copy(
                        tarefas = tarefas.toList()
                    )
                }
                editTarefa = false
                tarefaToEdit = Tarefa("", "", false)
            } else {
                _listaDeTarefasUiState.update {
                    it.copy(
                        tarefas = it.tarefas + Tarefa(
                            _insertTarefaUiState.value.titulo,
                            _insertTarefaUiState.value.descricao,
                            _insertTarefaUiState.value.concluida
                        )
                    )
                }
            }

            _insertTarefaUiState.update {
                InsertTarefaUiState()
            }
            _initialScreenUiState.update {
                InitialScreenUiState(
                    "Lista de Tarefas",
                    R.drawable.baseline_add_24,
                    "Inserir Tarefa"
                )
            }
            navCotroller.navigate(InitialScreenAction.LISTA_DE_TAREFAS.name) {
                popUpTo(InitialScreenAction.LISTA_DE_TAREFAS.name) {
                    inclusive = true
                }
            }
        }
    }

    fun voltar(navController: NavController) {
        editTarefa = false
        tarefaToEdit = Tarefa("", "", false)
        _insertTarefaUiState.update { InsertTarefaUiState() }
        _initialScreenUiState.update {
            InitialScreenUiState(
                "Lista de Tarefas",
                R.drawable.baseline_add_24,
                "Inserir Tarefa"
            )
        }
        navController.popBackStack()
    }

    fun updateTarefa(tarefa: Tarefa, navController: NavController) {
        editTarefa = true
        tarefaToEdit = tarefa
        _insertTarefaUiState.update {
            it.copy(
                titulo = tarefa.titulo,
                descricao = tarefa.descricao,
                concluida = tarefa.concluida
            )
        }
        _initialScreenUiState.update {
            InitialScreenUiState(
                "Editar Tarefa",
                R.drawable.baseline_check_24,
                "Confirmar"
            )
        }
        navController.navigate(InitialScreenAction.INSERT_TAREFA.name)

    }

    fun checkTarefa(tarefa: Tarefa) {
        val tarefas = _listaDeTarefasUiState.value.tarefas.toMutableList()
        val pos = tarefas.indexOf(tarefa)
        tarefas.remove(tarefa)
        tarefas.add(
            pos, tarefa.copy(
                concluida = !tarefa.concluida
            )
        )
        _listaDeTarefasUiState.update {
            it.copy(
                tarefas = tarefas.toList()
            )
        }
    }

    fun deleteTarefa(tarefa: Tarefa) {
        val tarefas = _listaDeTarefasUiState.value.tarefas.toMutableList()
        tarefas.remove(tarefa)
        _listaDeTarefasUiState.update {
            it.copy(
                tarefas = tarefas.toList()
            )
        }
    }

    fun updateTitulo(titulo: String) {
        _insertTarefaUiState.update {
            it.copy(
                titulo = titulo
            )
        }
    }

    fun updateDescricao(descricao: String) {
        _insertTarefaUiState.update {
            it.copy(
                descricao = descricao
            )
        }
    }


}