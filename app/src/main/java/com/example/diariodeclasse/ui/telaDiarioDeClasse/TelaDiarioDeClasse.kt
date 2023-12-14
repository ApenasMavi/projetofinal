package com.example.diariodeclasse.ui.telaDiarioDeClasse

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.diariodeclasse.R
import com.example.diariodeclasse.ViewModelCompartilhado
import com.example.diariodeclasse.model.Aluno
import com.example.diariodeclasse.model.Tela
import com.example.diariodeclasse.ui.componentes.CampoDeTexto
import com.example.diariodeclasse.ui.componentes.FotoPerfil
import com.example.diariodeclasse.ui.componentes.TopBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaDiarioDeClasse(
    controleDeNavegacao: NavController,
    viewModelCompartilhado: ViewModelCompartilhado = viewModel(),
    telaDiarioDeClasseViewModel: TelaDiarioDeClasseViewModel = viewModel()
) {

    val listaDeAlunos = telaDiarioDeClasseViewModel
        .carregarListaDeAlunos()
        .collectAsState(mutableListOf())
        .value

    Log.d(TAG, "teste ${listaDeAlunos}")

    Scaffold(
        topBar = {
            TopBar(
                title = R.string.lista_de_alunos,
                onClick = {
                    controleDeNavegacao.popBackStack()
                },
                mostraNavegationIcon = true
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {

                }
            ) {
                IconButton(
                    onClick = {
                        controleDeNavegacao.navigate(Tela.CADASTRAR_ALUNO.descricao)
                    }
                ) {
                    Icon(
                        Icons.Filled.Add,
                        contentDescription = null,
                    )
                }
            }
        }
    ) { espacosDasBarras ->

        LazyColumn(
            modifier = Modifier
                .padding(espacosDasBarras)
        ) {
            items(listaDeAlunos) { aluno ->
                CardAluno(aluno = aluno, telaDiarioDeClasseViewModel=telaDiarioDeClasseViewModel)
            }
        }
    }

}

@Composable
fun CardAluno(
    aluno: Aluno,
    telaDiarioDeClasseViewModel:TelaDiarioDeClasseViewModel
) {
    var expandir by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
                .padding(5.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                if (aluno.fotoPerfilUrl != "") {
                    aluno.fotoPerfilUrl.let { FotoPerfil(it.toUri()) }
                } else
                    FotoPerfil(null)

                aluno.nome.let { DadosMatriculaAluno(it, aluno.curso) }
                BotaoExpandirDadosAluno(expandir, { expandir = !expandir })
            }
            if (expandir) {
                DadosRendimentoAluno(aluno,telaDiarioDeClasseViewModel)
            }
        }
    }

}

@Composable
fun DadosMatriculaAluno(
    nome: String,
    curso: String
) {
    Column(
    ) {
        Text(
            modifier = Modifier.padding(10.dp),
            text = nome,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier.padding(10.dp),
            text = curso,
            fontSize = 20.sp,
        )
    }
}

@Composable
fun DadosRendimentoAluno(
    aluno: Aluno,
    telaDiarioDeClasseViewModel:TelaDiarioDeClasseViewModel
) {

    var editar by remember { mutableStateOf(false) }

    Row {
        Column(
            modifier = Modifier
                .padding(20.dp)
        ) {
            if (!editar) {

                Text(
                    text = "Nota: ${aluno.nota}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Faltas: ${aluno.faltas}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            } else {
                CampoDeTexto(
                    value = "${aluno.nota}",
                    onValueChange = { aluno.nota = it.toInt() },
                    idTextoLabel = R.string.nota
                )
                CampoDeTexto(
                    value = "${aluno.faltas}",
                    onValueChange = { aluno.faltas = it.toInt() },
                    idTextoLabel = R.string.faltas
                )
            }
        }
        IconButton(
            onClick = {

            },
        ) {
            Icon(
                imageVector = Icons.Filled.Clear,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary
            )
        }

        IconButton(
            onClick = {
                if (editar) {
                    telaDiarioDeClasseViewModel.editarAluno(aluno)
                    editar = false
                }
            },
        ) {
            Icon(
                imageVector = if (editar)
                    Icons.Filled.Check
                else
                    Icons.Filled.Create,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Composable
fun BotaoExpandirDadosAluno(
    expandir: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector =
            if (expandir)
                Icons.Filled.KeyboardArrowUp
            else
                Icons.Filled.KeyboardArrowDown,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}
