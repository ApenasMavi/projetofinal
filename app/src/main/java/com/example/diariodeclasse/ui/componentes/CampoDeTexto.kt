package com.example.diariodeclasse.ui.componentes

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampoDeTexto(
    value:String,
    onValueChange:(String)->Unit,
    enabled:Boolean = true,
    idTextoLabel:Int,
    isError:Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction:ImeAction = ImeAction.Default,
    campoSenha:Boolean = false

){
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        enabled = enabled,
        label = {
            Text(
                text = stringResource(id = idTextoLabel),
            )
        },
        isError = isError,

        visualTransformation = if(campoSenha)
                                    PasswordVisualTransformation()
                                else
                                    VisualTransformation.None,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(MaterialTheme.colorScheme.primary)
    )
}