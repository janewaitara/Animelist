package com.mumbicodes.designsystem.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mumbicodes.designsystem.R
import com.mumbicodes.designsystem.atoms.Badge
import com.mumbicodes.designsystem.atoms.BadgeColor
import com.mumbicodes.designsystem.atoms.Image
import com.mumbicodes.designsystem.atoms.Text
import com.mumbicodes.designsystem.molecules.Card
import com.mumbicodes.designsystem.theme.AnimeListTheme
import com.mumbicodes.designsystem.theme.AnimeTheme

@Composable
fun SearchCharacterComponent(
    modifier: Modifier = Modifier,
    characterEnglishName: String,
    characterNativeName: String,
    characterImageUrl: String?,
    age: String?,
    gender: String?,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .clip(AnimeTheme.shapes.mediumShape)
            .background(color = AnimeTheme.colors.cardColors)
            .height(96.dp),
        shape = AnimeTheme.shapes.mediumShape
    ) {
        Row(
            modifier = Modifier.padding(
                end = AnimeTheme.space.space16dp
            )
        ) {
            Image(
                modifier = Modifier.clip(shape = AnimeTheme.shapes.mediumShape),
                coverImageUrl = characterImageUrl,
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(AnimeTheme.space.space16dp))

            Column(
                modifier = Modifier
                    .padding(vertical = AnimeTheme.space.space8dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(AnimeTheme.space.space4dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = characterEnglishName,
                    color = AnimeTheme.colors.textNeutral,
                    style = AnimeTheme.typography.bodyMediumBold,
                    textAlign = TextAlign.Start
                )

                Text(
                    text = characterNativeName,
                    color = AnimeTheme.colors.primary,
                    style = AnimeTheme.typography.bodyExtraSmall,
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = when (age != null) {
                        true -> Arrangement.SpaceBetween
                        false -> Arrangement.End
                    },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    age?.let {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = stringResource(id = R.string.age, age),
                            color = AnimeTheme.colors.textStrong,
                            style = AnimeTheme.typography.bodySmall,
                            textAlign = TextAlign.Start
                        )
                    }

                    gender?.let {
                        Badge(
                            text = gender,
                            badgeColor = when (gender) {
                                "Male" -> BadgeColor.Info
                                "Female" -> BadgeColor.Primary
                                else -> BadgeColor.Info
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, device = Devices.PIXEL)
@Composable
fun SearchCharacterComponentPreview() {
    AnimeListTheme {
        Column {
            SearchCharacterComponent(
                characterImageUrl = null,
                characterEnglishName = "Character Name",
                characterNativeName = "Native name",
                age = "18 - 19",
                gender = "Male",
                onClick = {}
            )
            Spacer(modifier = Modifier.height(AnimeTheme.space.space16dp))

            SearchCharacterComponent(
                characterImageUrl = null,
                characterEnglishName = "Character Name",
                characterNativeName = "Native name",
                age = "18 - 19",
                gender = "Female",
                onClick = {}
            )
        }
    }
}