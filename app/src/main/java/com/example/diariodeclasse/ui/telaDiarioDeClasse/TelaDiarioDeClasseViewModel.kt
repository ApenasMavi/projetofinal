package com.example.diariodeclasse.ui.telaDiarioDeClasse

import androidx.lifecycle.ViewModel
import com.example.diariodeclasse.dataBase.firebase.FirebaseCloudFirestore
import com.example.diariodeclasse.model.Aluno
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TelaDiarioDeClasseViewModel(): ViewModel() {


    private val _telaListaDeAlunosUiState = MutableStateFlow(TelaListaDeAlunosUIState())
    val telaListaDeAlunosUiState: StateFlow<TelaListaDeAlunosUIState> = _telaListaDeAlunosUiState.asStateFlow()

    private val firebaseCloudFirestore = FirebaseCloudFirestore()

    fun carregarListaDeAlunos(): Flow<MutableList<Aluno>> {
        return firebaseCloudFirestore.carregarListaDeAlunos()
    }
}