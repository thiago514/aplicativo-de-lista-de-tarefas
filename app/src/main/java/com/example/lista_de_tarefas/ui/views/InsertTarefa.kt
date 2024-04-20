package com.example.lista_de_tarefas.ui.views

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.lista_de_tarefas.viewmodel.ListaDeTarefasViewModel

@Composable
fun InsertTarefa(
    viewModel: ListaDeTarefasViewModel,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val uiState by viewModel.insertTarefaUiState.collectAsState()
    BackHandler {
        viewModel.voltar(navController = navController)
    }
    InsertForm(
        titulo = uiState.titulo,
        descricao = uiState.descricao,
        onUpdateTitulo = viewModel::updateTitulo,
        onUpdateDescricao = viewModel::updateDescricao,
        modifier = modifier
    )
}

@Composable
fun InsertForm(
    titulo: String,
    descricao: String,
    onUpdateTitulo: (String) -> Unit,
    onUpdateDescricao: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        TextField(
            value = titulo,
            onValueChange = onUpdateTitulo,
            label = { Text("Título") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = descricao,
            onValueChange = onUpdateDescricao,
            label = { Text("Descrição") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun InsertFormPreview() {
    InsertForm(
        titulo = "Titulo",
        descricao = "Descrição",
        onUpdateTitulo = {},
        onUpdateDescricao = {}
    )
}