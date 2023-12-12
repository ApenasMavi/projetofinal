package com.example.diariodeclasse

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import com.example.diariodeclasse.model.Usuario
import com.google.firebase.Firebase
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.storage.storage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ViewModelCompartilhado() : ViewModel() {

    private var auth by mutableStateOf(Firebase.auth)

    private val _usuarioAutenticado = MutableStateFlow<Usuario?>(null)
    val usuarioAutenticado: StateFlow<Usuario?> = _usuarioAutenticado

    fun deslogarUsuario() {
        _usuarioAutenticado.value?.autenticado = false
    }

    fun logarUsuario(
        usuario: Usuario,
        localContext: Context
    ){
        auth.signInWithEmailAndPassword(usuario.email!!, usuario.senha)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.let {
                        val usuarioLogado = Usuario(
                            id = user.uid,
                            nome = user.displayName ?: "",
                            email = user.email ?: "",
                            fotoPerfilUrl = user.photoUrl.toString() ?: "",
                            autenticado = true
                        )
                        _usuarioAutenticado.value = usuarioLogado
                        mensagemToast(localContext, "Autenticado com sucesso!!")
                    }
                } else {
                    mensagemToast(localContext, "Erro ao Autenticar")
                }
            }
    }


    fun cadastrarUsuario(
        usuario: Usuario,
        localContext: Context,
    ) {
        auth.createUserWithEmailAndPassword(usuario.email!!, usuario.senha)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.let {
                        val profileUpdates = userProfileChangeRequest {
                            displayName = usuario.nome
                            photoUri = usuario.fotoPerfilUrl?.toUri()
                        }
                        it.updateProfile(profileUpdates)
                            .addOnCompleteListener { profileUpdateTask ->
                                if (profileUpdateTask.isSuccessful) {
                                    usuario.senha = ""
                                    usuario.id = user.uid
                                    usuario.autenticado = true
                                    salvarFoto(usuario,localContext)
                                    mensagemToast(localContext,"Cadastro efetuado com Sucesso!!")
                                } else {
                                    mensagemToast(localContext,"Falha ao Atualizar Nome ou Foto de Perfil")
                                }
                            }
                    }
                } else {
                    // Falha no cadastro
                    mensagemToast(localContext, "Falha no Cadastro")
                }
            }
    }

    fun salvarFoto(usuario: Usuario, localContext: Context) {
        val filename = "${usuario.nome}-${usuario.id}"
        val ref = Firebase.storage.reference.child("/fotoUsuarios/$filename")
        val uploadTask = usuario.fotoPerfilUri?.let { ref.putFile(it) }
        uploadTask?.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let { throw it }
            }
            ref.downloadUrl
        }?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                usuario.fotoPerfilUrl = task.result.toString()
                atualizarPerfil(usuario,localContext)
            } else {
                mensagemToast(localContext, "Falha")
            }
        }
    }

    fun atualizarPerfil(usuario: Usuario, localContext: Context) {
        val user = auth.currentUser
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setPhotoUri(Uri.parse(usuario.fotoPerfilUrl))
            .build()
        user?.updateProfile(profileUpdates)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _usuarioAutenticado.value = usuario
                } else {
                    mensagemToast(localContext, "Falha")
                }
            }
    }

    fun mensagemToast(
        localContext: Context,
        mensagem: String
    ) {
        Toast.makeText(
            localContext,
            mensagem,
            Toast.LENGTH_SHORT
        ).show()
    }
}