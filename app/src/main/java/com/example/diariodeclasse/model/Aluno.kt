package com.example.diariodeclasse.model

import android.net.Uri

data class Aluno(
    var nome:String="",
    var curso:String ="",
    var fotoPerfilUrl: Uri? =null,
    var notas:Int = 0,
    var faltas:Int = 0
)
