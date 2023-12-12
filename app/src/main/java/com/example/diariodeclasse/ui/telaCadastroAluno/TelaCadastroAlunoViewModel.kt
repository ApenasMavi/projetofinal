package com.example.diariodeclasse.ui.telaCadastroAluno

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType.Companion.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TelaCadastroAlunoViewModel():ViewModel() {

    private val _telaCadastroAlunoUIState = MutableStateFlow<TelaCadastroAlunoUIState?>(null)
    var telaCadastroAlunoUIState: StateFlow<TelaCadastroAlunoUIState?> = _telaCadastroAlunoUIState.asStateFlow()

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

}