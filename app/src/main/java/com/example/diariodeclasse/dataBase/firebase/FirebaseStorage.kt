package com.example.diariodeclassemobile.firebase

import android.content.Context
import android.widget.Toast
import com.example.diariodeclasse.ViewModelCompartilhado
import com.example.diariodeclasse.model.Usuario
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FirebaseStorage {

    private val _urlFotoUsuario = MutableStateFlow<String?>(null)
    val urlFotoUsuario: StateFlow<String?> = _urlFotoUsuario.asStateFlow()



    fun uploadImageToFirebaseStorage(usuario:Usuario, context: Context){

        val filename = "${usuario.id}${usuario.nome}"

        val ref = Firebase.storage.reference.child("/fotoUsuarios/$filename")

        val uploadTask = usuario.fotoPerfilUri?.let { ref.putFile(it) }

        uploadTask?.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            ref.downloadUrl
        }?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val url =task.result.toString()
                _urlFotoUsuario.value = url

                mensagemToast(context, "Sucesso!!")
            } else {
                mensagemToast(context, "Falha")
            }
        }
    }

    fun cadastraFotoUsuario(
        usuario: Usuario,
        context: Context
    ){
        val filename = "${usuario.id}${usuario.nome}"

        val ref = Firebase.storage.reference.child("/fotoUsuarios/$filename")

        val uploadTask = usuario.fotoPerfilUri?.let { ref.putFile(it) }

        uploadTask?.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            ref.downloadUrl
        }?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                usuario.fotoPerfilUrl =task.result.toString()

                mensagemToast(context, "Foto salva com Sucesso!!")

                ViewModelCompartilhado().cadastrarUsuario(
                    usuario,
                    context
                )
            } else {
                mensagemToast(context, "Falha")
            }
        }
    }



    fun mensagemToast(
        localContext: Context,
        mensagem:String
    ){
        Toast.makeText(
            localContext,
            mensagem,
            Toast.LENGTH_SHORT
        ).show()
    }
}