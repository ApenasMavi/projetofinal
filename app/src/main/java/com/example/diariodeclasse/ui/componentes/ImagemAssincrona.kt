package com.example.diariodeclasse.ui.componentes

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.diariodeclasse.R

@Composable
fun FotoPerfil(
    urlImagem: Uri
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(urlImagem)
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.person),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(150.dp)
            .border(
                BorderStroke(4.dp, Color.Black),
                CircleShape
            )
            .clip(CircleShape),
    )
}