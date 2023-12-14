package com.example.diariodeclasse.ui.componentes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.diariodeclasse.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: Int,
    onClick: () -> Unit,
    mostraNavegationIcon: Boolean,
    urlImagem: String? =null
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(id = title),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        },
        navigationIcon = {
            if (mostraNavegationIcon ) {
                IconButton(
                    onClick = onClick
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        },
        actions = {
            if (mostraNavegationIcon && urlImagem!=null && urlImagem!="") {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(urlImagem)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.person),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(50.dp)
                        .border(
                            BorderStroke(4.dp, Color.Black),
                            CircleShape
                        )
                        .clip(CircleShape),
                )
            }
        },

    )
}