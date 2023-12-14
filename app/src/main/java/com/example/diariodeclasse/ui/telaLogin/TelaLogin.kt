package com.example.diariodeclasse.ui.telaLogin

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.diariodeclasse.R
import com.example.diariodeclasse.ViewModelCompartilhado
import com.example.diariodeclasse.model.Tela
import com.example.diariodeclasse.ui.componentes.Botao
import com.example.diariodeclasse.ui.componentes.CampoDeTexto
import com.example.diariodeclasse.ui.componentes.TopBar

@Composable
fun TelaLogin(
    telaLoginViewModel: TelaLoginViewModel = viewModel(),
    viewModelCompartilhado: ViewModelCompartilhado = viewModel(),
    controleDeNavegacao: NavController
) {

    val localContext = LocalContext.current
    val telaLoginUiState by telaLoginViewModel.telaLoginUIState.collectAsState()

    val usuarioAutenticado = viewModelCompartilhado.usuarioAutenticado.collectAsState()

    if (usuarioAutenticado.value?.autenticado == true) {
        controleDeNavegacao.navigate(Tela.HOME.descricao)
    }

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let { telaLoginViewModel.updatefotoPerfilUri(it) }
        }

    val painter = rememberAsyncImagePainter(
        ImageRequest
            .Builder(LocalContext.current)
            .data(data = telaLoginViewModel.fotoPerfilUri)
            .build()
    )

    telaLoginViewModel.preencherUsuario()

    Scaffold(
        topBar = {
            TopBar(
                title = R.string.diario_de_classe,
                onClick = {
                    telaLoginViewModel.updateCadastrarAtivo()
                },
                mostraNavegationIcon = telaLoginUiState.cadastrarAtivo
            )
        },
    ) { espacosDasBarras ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(espacosDasBarras)
                .fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioNoBouncy,
                            stiffness = Spring.StiffnessVeryLow
                        )
                    )
            ) {

                if (telaLoginViewModel.fotoPerfilUri == null) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = null,
                        modifier = Modifier
                            .size(200.dp)
                            .clickable {
                                launcher.launch(
                                    PickVisualMediaRequest(
                                        mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                                    )
                                )
                            },
                        tint = MaterialTheme.colorScheme.primary
                    )
                } else {
                    Image(
                        painter = painter,
                        contentDescription = null,
                        modifier = Modifier
                            .size(200.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop,
                    )
                }

                if (telaLoginUiState.cadastrarAtivo) {
                    CampoDeTexto(
                        value = telaLoginViewModel.nome,
                        onValueChange = { telaLoginViewModel.updateNome(it) },
                        idTextoLabel = R.string.nome
                    )
                }
                CampoDeTexto(
                    value = telaLoginViewModel.email,
                    onValueChange = {
                        telaLoginViewModel.updateEmail(it)
                        telaLoginViewModel.updateHabilitarBotao(
                            telaLoginViewModel.email.isNotEmpty() &&
                                    telaLoginViewModel.senha.isNotEmpty()
                        )
                    },
                    idTextoLabel = telaLoginUiState.idTextoCampoEmail,
                    isError = telaLoginUiState.erroAoLogar,
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next

                )
                CampoDeTexto(
                    value = telaLoginViewModel.senha,
                    onValueChange = {
                        telaLoginViewModel.updateSenha(it)
                        telaLoginViewModel.updateHabilitarBotao(
                            telaLoginViewModel.email.isNotEmpty() &&
                                    telaLoginViewModel.senha.isNotEmpty()
                        )
                    },
                    idTextoLabel = telaLoginUiState.idTextoCampoSenha,
                    isError = telaLoginUiState.erroAoLogar,
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
                    campoSenha = true
                )
                if (telaLoginUiState.cadastrarAtivo) {
                    CampoDeTexto(
                        value = telaLoginViewModel.confirmarSenha,
                        onValueChange = { telaLoginViewModel.updateConfirmarSenha(it) },
                        idTextoLabel = telaLoginUiState.idTextoCampoConfirmarSenha,
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done,
                        campoSenha = true
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                }
            }
            if (!telaLoginUiState.cadastrarAtivo) {
                TextButton(
                    onClick = {
                        telaLoginViewModel.updateCadastrarAtivo()
                    }
                ) {
                    Text(
                        text = stringResource(R.string.cadastrar)
                    )
                }
            }

            Botao(
                onClick = {
                    if (telaLoginUiState.cadastrarAtivo) {
                        telaLoginUiState.usuarioPreenchido?.let {
                            viewModelCompartilhado.cadastrarUsuario(
                                it,
                                localContext
                            )
                        }
                    } else {
                        telaLoginUiState.usuarioPreenchido?.let {
                            viewModelCompartilhado.logarUsuario(
                                it,
                                localContext
                            )
                        }
                    }

                },
                idTextoBotao = telaLoginUiState.idtextoBotaoEntrarCadastrar,
                enabled = telaLoginUiState.habilitarBotao
            )
        }
    }
}