package com.example.diariodeclasse.ui.telaInicial

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.google.firebase.auth.FirebaseAuth

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
                mostraNavegationIcon = true,
                urlImagem = usuarioAutenticado.value?.fotoPerfilUrl
            )
        },
        bottomBar = {
            BottomAppBar(
                actions = {
                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    ){

                        IconButton(onClick = { /* do something */ }) {
                            Icon(Icons.Filled.Home, contentDescription = "Localized description")
                        }
                        IconButton(onClick = {
                            controleDeNavegacao.navigate(Tela.DIARIO_DE_CLASSE.descricao)
                        }) {
                            Icon(
                                Icons.Filled.List,
                                contentDescription = "Localized description",
                            )
                        }
                        IconButton(onClick = {
                            FirebaseAuth.getInstance().signOut()
                            viewModelCompartilhado.deslogarUsuario()
                            controleDeNavegacao.popBackStack()
                        }) {
                            Icon(
                                Icons.Filled.ExitToApp,
                                contentDescription = "Localized description",
                            )
                        }
                    }
                }
            )
        }

    ) { espacosDasBarras ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(espacosDasBarras),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            usuarioAutenticado.value?.nome?.let {
                Text(
                    text = "Bem Vindo ${it}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

        }
    }
}