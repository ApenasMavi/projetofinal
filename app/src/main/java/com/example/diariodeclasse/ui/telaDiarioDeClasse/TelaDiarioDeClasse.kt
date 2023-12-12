package com.example.diariodeclasse.ui.telaDiarioDeClasse

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.diariodeclasse.R
import com.example.diariodeclasse.ViewModelCompartilhado
import com.example.diariodeclasse.model.Aluno
import com.example.diariodeclasse.model.Tela
import com.example.diariodeclasse.ui.componentes.FotoPerfil
import com.example.diariodeclasse.ui.componentes.TopBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaDiarioDeClasse(
    controleDeNavegacao:NavController,
    viewModelCompartilhado: ViewModelCompartilhado = viewModel(),
    telaDiarioDeClasseViewModel: TelaDiarioDeClasseViewModel = viewModel()
) {

    val listaDeAlunos = telaDiarioDeClasseViewModel
        .carregarListaDeAlunos()
        .collectAsState(mutableListOf())
        .value

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
                CardAluno(aluno = aluno)
            }
        }
    }
}

@Composable
fun CardAluno(
    aluno: Aluno
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

                aluno.fotoPerfilUrl?.let { FotoPerfil(it) }
                aluno.nome.let { DadosMatriculaAluno(it, aluno.curso) }
                BotaoExpandirDadosAluno(expandir, { expandir = !expandir })
            }
            if (expandir) {
                DadosRendimentoAluno(aluno.notas, aluno.faltas)
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
    nota: Int,
    faltas: Int
) {
    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Nota: $nota",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Faltas: $faltas",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
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
