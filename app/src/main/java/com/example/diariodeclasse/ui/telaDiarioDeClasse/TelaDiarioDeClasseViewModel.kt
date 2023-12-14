package com.example.diariodeclasse.ui.telaDiarioDeClasse

import androidx.lifecycle.ViewModel
import com.example.diariodeclasse.model.Aluno
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TelaDiarioDeClasseViewModel(): ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    private val _listaDeAlunos = MutableStateFlow<MutableList<Aluno>>(mutableListOf())
    private val listaDeAlunos: StateFlow<MutableList<Aluno>> = _listaDeAlunos.asStateFlow()

    private val _eventoEditarAluno = MutableStateFlow<Aluno?>(null)
    val eventoEditarAluno: StateFlow<Aluno?> = _eventoEditarAluno

    private val _eventoDeletarAluno = MutableStateFlow<Aluno?>(null)
    val eventoDeletarAluno: StateFlow<Aluno?> = _eventoDeletarAluno
    fun carregarListaDeAlunos(): Flow<MutableList<Aluno>> {

        val carregaListaDeAlunos:MutableList<Aluno> = mutableListOf()

        db.collection("Alunos").get().addOnCompleteListener{querySnapshot ->
            if(querySnapshot.isSuccessful) {
                for (documento in querySnapshot.result)
                {
                    val aluno = documento.toObject(Aluno::class.java)
                    carregaListaDeAlunos.add(aluno)
                }
            }
            _listaDeAlunos.value = carregaListaDeAlunos
        }
        return listaDeAlunos

    }
    fun editarAluno(aluno: Aluno) {
        // Implemente aqui a lógica para editar um aluno no Firebase
        // Use o id do aluno para identificá-lo no banco de dados
        // Exemplo:
        db.collection("Alunos").document(aluno.id).set(aluno).addOnCompleteListener {
            if (it.isSuccessful) {
                _eventoEditarAluno.value = aluno
            } else {
                _eventoEditarAluno.value = null
            }
        }
    }

    fun deletarAluno(aluno: Aluno) {
        // Implemente aqui a lógica para deletar um aluno no Firebase
        // Use o id do aluno para identificá-lo no banco de dados
        // Exemplo:
        db.collection("Alunos").document(aluno.id).delete().addOnCompleteListener {
            if (it.isSuccessful) {
                _eventoDeletarAluno.value = aluno
            } else {
                _eventoDeletarAluno.value = null
            }
        }
    }

}