package com.example.lista_de_tarefas.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.lista_de_tarefas.models.Tarefa
import com.example.lista_de_tarefas.viewmodel.ListaDeTarefasViewModel
import kotlin.reflect.KFunction1

@Composable
fun ListaDeTarefas(
    viewModel: ListaDeTarefasViewModel,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val uiState by viewModel.listaDeTarefasUiState.collectAsState()
    LazyColumn {
        items(uiState.tarefas) { tarefa ->
            TarefaCard(
                tarefa = tarefa,
                onDelete = viewModel::deleteTarefa,
                modifier = modifier,
                onEditTarefa = {
                    viewModel.updateTarefa(
                        tarefa = tarefa,
                        navController = navController
                    )
                },
                onConcluida = {
                    viewModel.checkTarefa(tarefa)
                })
        }
    }
}

@Composable
fun TarefaCard(
    tarefa: Tarefa,
    onDelete: KFunction1<Tarefa, Unit>,
    onEditTarefa: () -> Unit,
    onConcluida: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(2.dp)
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.background)

    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            IconButton(
                onClick = { onConcluida() }, modifier = modifier
                    .border(1.dp, Color.Black)
                    .padding(bottom = 7.dp)
            ) {
                if(tarefa.concluida){
                    Icon(
                        Icons.Filled.Check,
                        contentDescription = "Delete",
                        modifier = modifier.size(50.dp),
                    )
                }

            }
        }
        Card(
            modifier = Modifier
                .padding(10.dp)
                .clickable {
                    onEditTarefa()
                },
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background,
            ),
        ) {
            Text(
                text = tarefa.titulo,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                style = TextStyle(textDecoration = if(tarefa.concluida) TextDecoration.LineThrough else TextDecoration.None),
                modifier = Modifier.padding(1.dp)
            )
            Text(
                text = tarefa.descricao,
                fontSize = 16.sp,
                style = TextStyle(textDecoration = if(tarefa.concluida) TextDecoration.LineThrough else TextDecoration.None),
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(1.dp)

            )
        }
        Spacer(modifier = modifier.weight(1f))
        IconButton(onClick = { onDelete(tarefa) }, modifier = modifier.padding(10.dp)) {
            Icon(
                Icons.Filled.Delete,
                contentDescription = "Delete",
                modifier = modifier.size(30.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TarefaCardPreview() {
    TarefaCard(
        tarefa = Tarefa(
            titulo = "Titulo",
            descricao = "Descrição",
            concluida = false
        ),
        onConcluida = {},
        onDelete = ::deleteForPreview,
        onEditTarefa = {}
    )
}

fun deleteForPreview(tarefa: Tarefa) {
    println("Tarefa deletada: $tarefa")
}