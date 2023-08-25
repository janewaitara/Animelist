package com.mumbicodes.designsystem.molecules

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mumbicodes.designsystem.R
import com.mumbicodes.designsystem.atoms.Text
import com.mumbicodes.designsystem.atoms.TextButton
import com.mumbicodes.designsystem.theme.AnimeListTheme
import com.mumbicodes.designsystem.theme.AnimeTheme

@Composable
fun AnimeSection(
    @StringRes sectionTitle: Int,
    @StringRes buttonText: Int,
    buttonOnClick: () -> Unit,
    content: @Composable () -> Unit

) {
    Column(
        modifier = Modifier.background(color = AnimeTheme.colors.background)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier,
                text = stringResource(id = sectionTitle),
                style = AnimeTheme.typography.bodyLargeBold,
                color = AnimeTheme.colors.textStrong
            )

            Spacer(modifier = Modifier.width(AnimeTheme.space.space16dp))

            TextButton(
                text = stringResource(id = buttonText),
                onClick = buttonOnClick
            )
        }
        Spacer(modifier = Modifier.height(AnimeTheme.space.space12dp))

        content()
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SectionPreview() {
    AnimeListTheme {
        AnimeSection(
            sectionTitle = R.string.animeSection,
            buttonText = R.string.buttonTitle,
            buttonOnClick = {}
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = AnimeTheme.colors.background)
            ) {
                repeat(2) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(72.dp),
                        onClick = { /*TODO*/ }
                    ) {
                    }
                    Spacer(modifier = Modifier.height(AnimeTheme.space.space12dp))
                }
            }
        }
    }
}