package com.example.diariodeclasse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.diariodeclasse.model.Tela
import com.example.diariodeclasse.ui.telaCadastroAluno.TelaCadastroAluno
import com.example.diariodeclasse.ui.telaDiarioDeClasse.TelaDiarioDeClasse
import com.example.diariodeclasse.ui.telaInicial.TelaInicial
import com.example.diariodeclasse.ui.telaLogin.TelaLogin
import com.example.diariodeclasse.ui.theme.DiarioDeClasseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiarioDeClasseTheme {
                AppDiarioDeClasse()
            }
        }
    }
}

@Composable
fun AppDiarioDeClasse(
    viewModelCompartilhado: ViewModelCompartilhado = viewModel()
){
    val controleDeNavegacao = rememberNavController()

    NavHost(
        navController =controleDeNavegacao,
        startDestination = Tela.LOGIN.descricao
    ){
       composable(
           route = Tela.LOGIN.descricao,
       ){
           TelaLogin(
               controleDeNavegacao = controleDeNavegacao,
               viewModelCompartilhado = viewModelCompartilhado
           )
       }
        composable(
            route = Tela.DIARIO_DE_CLASSE.descricao,
        ){
            TelaDiarioDeClasse(
                controleDeNavegacao = controleDeNavegacao,
                viewModelCompartilhado = viewModelCompartilhado
            )
        }
        composable(
            route = Tela.HOME.descricao,
        ){
            TelaInicial(
                controleDeNavegacao = controleDeNavegacao,
                viewModelCompartilhado = viewModelCompartilhado
            )
        }
        composable(
            route = Tela.CADASTRAR_ALUNO.descricao,
        ){
            TelaCadastroAluno(
                controleDeNavegacao = controleDeNavegacao,
                viewModelCompartilhado = viewModelCompartilhado
            )
        }
    }

}
