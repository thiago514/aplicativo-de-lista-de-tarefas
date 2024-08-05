package com.example.lista_de_tarefas.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.lista_de_tarefas.R
import com.example.lista_de_tarefas.data.Tarefa
import com.example.lista_de_tarefas.data.TarefasRepository
import com.example.lista_de_tarefas.ui.views.InitialScreenAction
import com.example.lista_de_tarefas.ui.views.InitialScreenUiState
import com.example.lista_de_tarefas.ui.views.InsertTarefaUiState
import com.example.lista_de_tarefas.ui.views.ListaDeTarefasUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class ListaDeTarefasViewModel(
    private val tarefasRepository: TarefasRepository
) : ViewModel() {

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }


    val listaDeTarefasUiState: StateFlow<ListaDeTarefasUiState> =
        tarefasRepository.getAllTarefas().map { tarefas ->
            ListaDeTarefasUiState(tarefas.toList())
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = ListaDeTarefasUiState(emptyList())
        )

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
        0,
        "",
        "",
        false
    )

    suspend fun fabAction(navCotroller: NavController) {
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
                tarefasRepository.updateTarefa(
                    Tarefa(
                        id = tarefaToEdit.id,
                        titulo = _insertTarefaUiState.value.titulo,
                        descricao = _insertTarefaUiState.value.descricao,
                        concluida = _insertTarefaUiState.value.concluida
                    )
                )
                editTarefa = false
                tarefaToEdit = Tarefa(0,"", "", false)
            } else {
                tarefasRepository.insertTarefa(
                    Tarefa(
                       titulo = _insertTarefaUiState.value.titulo,
                        descricao = _insertTarefaUiState.value.descricao,
                        concluida = _insertTarefaUiState.value.concluida
                    )
                )
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
        tarefaToEdit = Tarefa(0,"", "", false)
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

    suspend fun checkTarefa(tarefa: Tarefa) {
        val tarefaAtualizada = tarefa.copy(
            concluida = !tarefa.concluida
        )
        tarefasRepository.updateTarefa(tarefaAtualizada)
    }

    suspend fun deleteTarefa(tarefa: Tarefa) {
        tarefasRepository.deleteTarefa(tarefa)
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