package com.mumbicodes.designsystem.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mumbicodes.designsystem.atoms.shimmerEffect

@Composable
fun ListLoadingComponent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // The mask with the shimmer
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .shimmerEffect()
        ) {}
    }
}