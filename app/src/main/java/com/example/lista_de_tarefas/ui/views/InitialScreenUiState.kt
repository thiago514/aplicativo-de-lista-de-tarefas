package com.example.lista_de_tarefas.ui.views

import androidx.annotation.DrawableRes

data class InitialScreenUiState(
    val title: String,
    @DrawableRes val icon: Int,
    val iconContentDescription: String,
)
