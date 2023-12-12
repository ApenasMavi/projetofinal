package com.example.diariodeclasse.ui.telaLogin

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.diariodeclasse.R
import com.example.diariodeclasse.model.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TelaLoginViewModel():ViewModel() {

    private var _telaLoginUIState = MutableStateFlow(TelaLoginECadastroUIState())
    val telaLoginUIState: StateFlow<TelaLoginECadastroUIState> = _telaLoginUIState.asStateFlow()


    var nome by mutableStateOf("")
        private set
    var email by mutableStateOf("")
        private set
    var senha by mutableStateOf("")
        private set
    var confirmarSenha by mutableStateOf("")
        private set
    var fotoPerfilUri by mutableStateOf<Uri?>(null)
        private set


    fun updateNome(nome: String) {
        this.nome = nome
    }

    fun updateEmail(email: String) {
        this.email = email
        limpaErroAoLogar()
    }

    fun updateSenha(senha: String) {
        this.senha = senha
        limpaErroAoLogar()
    }

    fun updateConfirmarSenha(confirmarSenha: String) {
        this.confirmarSenha = confirmarSenha
    }

    fun updatefotoPerfilUri(fotoPerfilUri: Uri) {
        this.fotoPerfilUri = fotoPerfilUri
    }

    fun limpaTelaLogin(
        erroAoLogar: Boolean = false,
        idTextoCampoEmail: Int = R.string.email,
        idTextoCampoSenha: Int = R.string.senha,
        idTextoCampoConfirmaSenha: Int = R.string.confirmar_senha,
        idtextoBotaoEntrarCadastrar: Int = R.string.entrar,
        usuarioPreenchido: Usuario? = null,
        habilitarBotao: Boolean = false

    ) {
        this.nome = ""
        this.email = ""
        this.senha = ""
        this.confirmarSenha = ""
        this.fotoPerfilUri = null
        _telaLoginUIState.update { currentState ->
            currentState.copy(
                idTextoCampoSenha = idTextoCampoSenha,
                idTextoCampoEmail = idTextoCampoEmail,
                idTextoCampoConfirmarSenha = idTextoCampoConfirmaSenha,
                idtextoBotaoEntrarCadastrar = idtextoBotaoEntrarCadastrar,
                erroAoLogar = erroAoLogar,
                usuarioPreenchido = usuarioPreenchido,
                habilitarBotao = habilitarBotao,

            )
        }
    }

    fun updateCadastrarAtivo() {
        _telaLoginUIState.update { currentState ->
            currentState.copy(
                cadastrarAtivo = !currentState.cadastrarAtivo,
                idtextoBotaoEntrarCadastrar = if (!currentState.cadastrarAtivo)
                    R.string.cadastrar
                else
                    R.string.entrar
            )
        }
    }

    fun updateHabilitarBotao(
        habilitarBotao: Boolean = true
    ) {
        _telaLoginUIState.update { currentState ->
            currentState.copy(
                habilitarBotao = habilitarBotao
            )
        }
    }

    fun limpaErroAoLogar(
        erroAoLogar: Boolean = false
    ) {
        _telaLoginUIState.update { currentState ->
            currentState.copy(
                erroAoLogar = erroAoLogar,
                idTextoCampoEmail = R.string.email,
                idTextoCampoSenha = R.string.senha
            )
        }
    }

    fun preencherUsuario(){
        _telaLoginUIState.update { currentState ->
            currentState.copy(
                usuarioPreenchido = Usuario(nome,email,senha,fotoPerfilUri = fotoPerfilUri)
            )
        }
    }


}