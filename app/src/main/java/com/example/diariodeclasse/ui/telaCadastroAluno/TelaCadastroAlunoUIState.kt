package com.example.diariodeclasse.ui.telaCadastroAluno

import com.example.diariodeclasse.model.Aluno

data class TelaCadastroAlunoUIState(
    var campoNome:String = "",
    var campoCurso:String = "",
    val aluno:Aluno = Aluno()
)
