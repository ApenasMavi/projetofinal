package com.example.diariodeclasse.model

import android.net.Uri

data class Aluno(
    var nome:String="",
    var curso:String ="",
    var fotoPerfilUrl: String ="",
    var notas:Int = 0,
    var faltas:Int = 0
)
