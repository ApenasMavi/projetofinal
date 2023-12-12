package com.example.diariodeclasse.ui.telaCadastroAluno

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.diariodeclasse.R
import com.example.diariodeclasse.ViewModelCompartilhado
import com.example.diariodeclasse.dataBase.firebase.FirebaseCloudFirestore
import com.example.diariodeclasse.model.Aluno
import com.example.diariodeclasse.ui.componentes.Botao
import com.example.diariodeclasse.ui.componentes.CampoDeTexto
import com.example.diariodeclasse.ui.componentes.TopBar
import androidx.compose.ui.unit.dp as dp1

@Composable
fun TelaCadastroAluno(
    telaCadastroAlunoViewModel: TelaCadastroAlunoViewModel = viewModel(),
    viewModelCompartilhado: ViewModelCompartilhado = viewModel(),
    controleDeNavegacao:NavController
){

    val telaCadastroAlunoUIState by telaCadastroAlunoViewModel.telaCadastroAlunoUIState.collectAsState()


    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                telaCadastroAlunoViewModel.updateFotoPerfilUri(uri)
            }
        }

    val painter = rememberAsyncImagePainter(
        ImageRequest
            .Builder(LocalContext.current)
            .data(data = telaCadastroAlunoViewModel.fotoPerfilUri)
            .build()
    )

    Scaffold(
        topBar = {
            TopBar(
                title = R.string.cadastrar_aluno,
                onClick = {
                    controleDeNavegacao.popBackStack()
                },
                mostraNavegationIcon = true
            )
        },

    ) { espacosDasBarras ->
        Column (
            modifier = Modifier
                .padding(espacosDasBarras),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){

            if (telaCadastroAlunoViewModel.fotoPerfilUri == null) {
                ImagemLogin(
                    painter = painterResource(id = R.drawable.person),
                    launcher = launcher
                )
            } else {
                ImagemLogin(
                    painter = painter,
                    launcher = launcher
                )
            }

            CampoDeTexto(
                value = telaCadastroAlunoViewModel.campoNome,
                onValueChange ={telaCadastroAlunoViewModel.updateCampoNome(it)},
                idTextoLabel = R.string.nome
            )
            CampoDeTexto(
                value =telaCadastroAlunoViewModel.campoCurso,
                onValueChange ={telaCadastroAlunoViewModel.updateCampoCurso(it)},
                idTextoLabel = R.string.curso
            )

            Botao(
                idTextoBotao = R.string.cadastrar,
                onClick = {
                    FirebaseCloudFirestore().salvarAluno(
                        Aluno(
                            telaCadastroAlunoViewModel.campoNome,
                            telaCadastroAlunoViewModel.campoCurso,
                            telaCadastroAlunoViewModel.fotoPerfilUri
                        )
                    )
                }
            )
        }
    }

}

@Composable
fun ImagemLogin(
    painter: Painter,
    launcher: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>
) {
    Image(
        painter = painter,
        contentDescription = null,
        modifier = Modifier
            .size(200.dp1)
            .clip(CircleShape)
            .clickable {
                launcher.launch(
                    PickVisualMediaRequest(
                        mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            },
        contentScale = ContentScale.Crop,
    )
}