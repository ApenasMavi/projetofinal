package com.example.diariodeclasse.model

import android.net.Uri

open class Usuario(
    var nome: String? ="",
    var email: String? ="",
    var senha:String="",
    var id: String? ="",
    var fotoPerfilUrl: String?="",
    var fotoPerfilUri: Uri? = null,
    var autenticado: Boolean = false
)
