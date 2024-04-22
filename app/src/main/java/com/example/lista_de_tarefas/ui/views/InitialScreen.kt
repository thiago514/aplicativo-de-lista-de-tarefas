package com.example.lista_de_tarefas.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lista_de_tarefas.viewmodel.ListaDeTarefasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InitalScreen() {
    val viewModel: ListaDeTarefasViewModel = viewModel()
    val navController = rememberNavController()
    val uiState by viewModel.initialScreenUiState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = uiState.title) })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.fabAction(navController) }) {
                Image(painter = painterResource(id = uiState.icon), contentDescription = uiState.iconContentDescription)
            }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = InitialScreenAction.LISTA_DE_TAREFAS.name,
            modifier = Modifier.padding(it)
        ){
            composable(route = InitialScreenAction.LISTA_DE_TAREFAS.name){
                ListaDeTarefas(viewModel = viewModel, navController = navController)
            }
            composable(route = InitialScreenAction.INSERT_TAREFA.name){
                InsertTarefa(viewModel = viewModel, navController = navController)
            }
        }
    }
}

enum class InitialScreenAction {
    INSERT_TAREFA,
    LISTA_DE_TAREFAS
}
