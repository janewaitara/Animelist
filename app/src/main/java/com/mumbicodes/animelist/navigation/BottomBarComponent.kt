package com.mumbicodes.animelist.com.mumbicodes.animelist.navigation

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mumbicodes.animelist.ui.theme.AnimelistTheme

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
            // val isSelected = currentDestination.isSelectedDestinationInHierarchy(destination)
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
            .clip(shape = MaterialTheme.shapes.small)
            .weight(1f)
            .fillMaxHeight()
            .clickable(
                onClick = {
                    onItemClick(destination)
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier,
            // .padding(start = 10.dp, end = 10.dp, top = 8.dp, bottom = 8.dp)
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            AnimatedVisibility(visible = itemIsSelected) {
                // TODO Show the indicator
            }

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
                color = contentColor
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