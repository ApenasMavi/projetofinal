package com.example.diariodeclasse.ui.telaCadastroAluno

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType.Companion.Uri
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import com.example.diariodeclasse.model.Aluno
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TelaCadastroAlunoViewModel():ViewModel() {

    private var _telaCadastroAlunoUIState = MutableStateFlow(TelaCadastroAlunoUIState())
    val telaCadastroAlunoUIState: StateFlow<TelaCadastroAlunoUIState> = _telaCadastroAlunoUIState.asStateFlow()

    var campoNome by mutableStateOf("")
        private set

    var campoCurso by mutableStateOf("")
        private set

    var fotoPerfilUri by mutableStateOf<Uri?>(null)
        private set

    fun updateCampoNome(campoNome:String){
        this.campoNome = campoNome
    }
    fun updateCampoCurso(campoCurso:String){
        this.campoCurso = campoCurso
    }
    fun updateFotoPerfilUri(fotoPerfilUri: Uri){
        this.fotoPerfilUri = fotoPerfilUri
    }
    fun limpaTelaCadastro(){
        this.campoNome = ""
        this.campoCurso = ""
        this.fotoPerfilUri = null
        _telaCadastroAlunoUIState.update { currentState ->
            currentState.copy(
                cadastroEfetuado = false
            )
        }
    }

    fun salvarAluno(){
        val db = FirebaseFirestore.getInstance()
        val alunoRef = db.collection("Alunos").document()
        alunoRef.set(
            Aluno(
                id = alunoRef.id,
                nome = campoNome,
                curso = campoCurso,
                fotoPerfilUrl = fotoPerfilUri.toString()
            )
        )
        val filename = "${campoNome}-${alunoRef.id}"
        val ref = Firebase.storage.reference.child("/fotoAlunos/$filename")
        val uploadTask = fotoPerfilUri.let { ref.putFile(it!!) }
        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            ref.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val url =task.result.toString()
                alunoRef.update("fotoPerfilUrl",url)

                _telaCadastroAlunoUIState.value.cadastroEfetuado = true

                // mensagemToast(context, "Sucesso!!")
            } else {
                //mensagemToast(context, "Falha")
            }
        }
    }

}