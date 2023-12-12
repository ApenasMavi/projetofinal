package com.example.diariodeclasse.ui.componentes

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun Botao(
    idTextoBotao:Int,
    onClick:()->Unit,
    enabled:Boolean = true
){
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(5.dp),
        elevation = ButtonDefaults.elevatedButtonElevation(4.dp),
        enabled = enabled
    ) {
        Text(
            text = stringResource(id = idTextoBotao)
        )
    }
}