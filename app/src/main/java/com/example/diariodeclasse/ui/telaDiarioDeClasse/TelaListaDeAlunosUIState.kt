package com.example.diariodeclasse.ui.telaDiarioDeClasse

import com.example.diariodeclasse.model.Aluno

data class TelaListaDeAlunosUIState(
    val listaDeAlunos:MutableList<Aluno> = mutableListOf()
)
