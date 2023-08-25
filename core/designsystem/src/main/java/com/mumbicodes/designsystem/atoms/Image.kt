package com.mumbicodes.designsystem.atoms

import android.content.res.Configuration
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mumbicodes.designsystem.R
import com.mumbicodes.designsystem.theme.AnimeListTheme

@Composable
fun Image(
    modifier: Modifier,
    contentDescription: String? = null,
    coverImageUrl: String? = null
) {
    AsyncImage(
        model = coverImageUrl,
        contentDescription = contentDescription,
        modifier = modifier.size(width = 140.dp, height = 120.dp),
        contentScale = ContentScale.Fit,
        placeholder = painterResource(id = R.drawable.anime_placeholder)
    )
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ImagePreview() {
    AnimeListTheme {
        Image(
            modifier = Modifier,
            contentDescription = null
        )
    }
}