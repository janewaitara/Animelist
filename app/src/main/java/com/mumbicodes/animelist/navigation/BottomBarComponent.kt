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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mumbicodes.animelist.ui.theme.AnimelistTheme
import com.mumbicodes.designsystem.theme.AnimeListSpacing.Space48dp
import com.mumbicodes.designsystem.theme.AnimeListSpacing.Space4dp

// TODO research how to move this to design system currently, it causes gradle errors cause of adding app module
@Composable
fun BottomBarComponent(
    destinations: List<MainAppDestinations>,
    currentDestination: NavDestination?,
    onItemClick: (MainAppDestinations) -> Unit
) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 8.dp, bottom = 8.dp)
            .height(72.dp)
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

@Composable
fun RowScope.AddItem(
    itemIsSelected: Boolean,
    destination: MainAppDestinations,
    onItemClick: (MainAppDestinations) -> Unit
) {
    val contentColor =
        if (itemIsSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
    Box(
        modifier = Modifier
            // .clip(shape = MaterialTheme.shapes.small)
            .weight(1f)
            .fillMaxHeight()
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
                            width = Space48dp,
                            height = 40.dp
                        )
                        .offset(y = (-40).dp)
                )
            }
        }

        Column(
            modifier = Modifier,
            // .padding(start = 10.dp, end = 10.dp, top = 8.dp, bottom = 8.dp)
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
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
                style = MaterialTheme.typography.bodySmall,
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
        val backgroundColor = MaterialTheme.colorScheme.background
        val dotIndicatorColor = MaterialTheme.colorScheme.primary
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
                radius = Space4dp.toPx(),
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
    AnimelistTheme {
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
    AnimelistTheme {
        Box(
            Modifier.size(200.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Row(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .padding(top = 8.dp, bottom = 8.dp)
                    .height(72.dp)
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