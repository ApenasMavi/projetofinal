package com.example.diariodeclasse.ui.telaInicial

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.diariodeclasse.R
import com.example.diariodeclasse.ViewModelCompartilhado
import com.example.diariodeclasse.model.Tela
import com.example.diariodeclasse.ui.componentes.TopBar

@Composable
fun TelaInicial(
    controleDeNavegacao: NavController,
    viewModelCompartilhado: ViewModelCompartilhado = viewModel()
) {

    val usuarioAutenticado = viewModelCompartilhado.usuarioAutenticado.collectAsState()

    Scaffold(
        topBar = {
            TopBar(
                title = R.string.app_name,
                onClick = {
                    viewModelCompartilhado.deslogarUsuario()
                    controleDeNavegacao.popBackStack()
                },
                mostraNavegationIcon = true
            )
        },

        ) { espacosDasBarras ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(espacosDasBarras),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(usuarioAutenticado.value?.fotoPerfilUrl)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.add_a_photo),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape),
            )

            usuarioAutenticado.value?.nome?.let {
                Text(
                    text = it,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Button(
                onClick = {
                    controleDeNavegacao.navigate(Tela.DIARIO_DE_CLASSE.descricao)
                }
            ) {
                Text(
                    text = "Lista de Alunos"
                )
            }
        }
    }
}