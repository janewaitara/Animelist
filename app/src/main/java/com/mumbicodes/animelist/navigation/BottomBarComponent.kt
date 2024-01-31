package com.mumbicodes.animelist.com.mumbicodes.animelist.navigation

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mumbicodes.designsystem.atoms.Icon
import com.mumbicodes.designsystem.atoms.Text
import com.mumbicodes.designsystem.theme.AnimeListTheme
import com.mumbicodes.designsystem.theme.AnimeTheme

// TODO research how to move this to design system currently, it causes gradle errors cause of adding app module
@Composable
fun BottomBarComponent(
    modifier: Modifier = Modifier,
    destinations: List<MainAppDestinations>,
    currentDestination: NavDestination?,
    onItemClick: (MainAppDestinations) -> Unit
) {
    Row(
        modifier = modifier
            .background(AnimeTheme.colors.bottomNavBackground)
            .wrapContentHeight()
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        destinations.forEach { destination ->
            val isSelected = currentDestination.isSelectedDestinationInHierarchy(destination)
            AddItem(
                destination = destination,
                itemIsSelected = isSelected,
                onItemClick = onItemClick
            )
        }
    }
}

// TODO What if we animate the position of the indicator horizontally instead of the animated visibility
@Composable
fun RowScope.AddItem(
    itemIsSelected: Boolean,
    destination: MainAppDestinations,
    onItemClick: (MainAppDestinations) -> Unit
) {
    val contentColor =
        if (itemIsSelected) AnimeTheme.colors.primary else AnimeTheme.colors.bottomNavContentColor
    Box(
        modifier = Modifier
            .background(Color.Transparent)
            .weight(1f)
            .wrapContentHeight()
            .clickable(
                onClick = {
                    onItemClick(destination)
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Row {
            AnimatedVisibility(visible = itemIsSelected) {
                ActiveBarItemIndicator(
                    modifier = Modifier
                        .size(
                            width = 48.dp,
                            height = 40.dp
                        )
                        .offset(y = (-33.5).dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .background(Color.Transparent)
                .padding(top = 12.dp, bottom = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                modifier = Modifier.size(AnimeTheme.space.space24dp),
                painter = painterResource(
                    id = if (itemIsSelected) destination.selectedIcon else destination.unselectedIcon
                ),
                contentDescription = stringResource(destination.iconLabel),
                tint = contentColor
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(id = destination.iconLabel),
                style = AnimeTheme.typography.bodyExtraSmall,
                color = contentColor,
                textAlign = TextAlign.Center
            )
        }
    }
}

// TODO Research how smoothen the edges as they lay on the item
@Composable
fun ActiveBarItemIndicator(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        val backgroundColor = AnimeTheme.colors.bottomNavBackground
        val dotIndicatorColor = AnimeTheme.colors.primary
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            drawArc(
                color = backgroundColor,
                startAngle = 0f,
                sweepAngle = -180f,
                useCenter = false,
                size = Size(size.width, size.height),
                alpha = 1f,
                style = Fill
            )
            drawCircle(
                color = dotIndicatorColor,
                radius = 4.dp.toPx(),
                center = Offset(size.width / 2, size.height / 4),
                alpha = 1f,
                style = Fill
            )
        }
    }
}

// TODO This is for testing - should not be here
@Composable
fun provideCurrentDestination(): NavDestination? {
    val navStackBackEntry by rememberNavController().currentBackStackEntryAsState()
    return navStackBackEntry?.destination
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun BottomNavPreview() {
    AnimeListTheme {
        BottomBarComponent(
            destinations = mainAppDestinations,
            currentDestination = provideCurrentDestination(),
            onItemClick = {}
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun BottomNavItemPreview() {
    AnimeListTheme {
        Box(
            Modifier.size(200.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Row(
                modifier = Modifier
                    .background(AnimeTheme.colors.bottomNavBackground)
                    .wrapContentHeight()
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AddItem(
                    destination = MainAppDestinations.SEARCH,
                    itemIsSelected = true,
                    onItemClick = { }
                )
            }
        }
    }
}