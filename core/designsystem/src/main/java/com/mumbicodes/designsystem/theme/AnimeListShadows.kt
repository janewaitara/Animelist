package com.mumbicodes.designsystem.theme

import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.platform.inspectable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Suppress("detekt:StringLiteralDuplication")
/**
 * Check how this will appear on dark Mode
 * This is shadow extra small on figma
 * */
@Stable
fun Modifier.extraSmallShadow(
    elevation: Dp = 2.dp,
    shape: Shape = RectangleShape,
    clip: Boolean = elevation > 0.dp,
    ambientColor: Color = Color(0xFFCCCCCC).copy(alpha = 0.75f),
    spotColor: Color = Color(0xFFCCCCCC).copy(alpha = 0.75f)
) = if (elevation > 0.dp || clip) {
    inspectable(
        inspectorInfo = debugInspectorInfo {
            name = "shadow"
            properties["elevation"] = elevation
            properties["shape"] = shape
            properties["clip"] = clip
            properties["ambientColor"] = ambientColor
            properties["spotColor"] = spotColor
        }
    ) {
        graphicsLayer {
            this.shadowElevation = elevation.toPx()
            this.shape = shape
            this.clip = clip
            this.ambientShadowColor = ambientColor
            this.spotShadowColor = spotColor
        }
    }
} else {
    this
}

/**
 * Check how this will appear on dark Mode
 * This is shadow small on figma
 * */
@Stable
fun Modifier.smallShadow(
    elevation: Dp = 4.dp,
    shape: Shape = RectangleShape,
    clip: Boolean = elevation > 0.dp,
    ambientColor: Color = Color(0xFFCCCCCC).copy(alpha = 0.75f),
    spotColor: Color = Color(0xFFCCCCCC).copy(alpha = 0.75f)
) = if (elevation > 0.dp || clip) {
    inspectable(
        inspectorInfo = debugInspectorInfo {
            name = "shadow"
            properties["elevation"] = elevation
            properties["shape"] = shape
            properties["clip"] = clip
            properties["ambientColor"] = ambientColor
            properties["spotColor"] = spotColor
        }
    ) {
        graphicsLayer {
            this.shadowElevation = elevation.toPx()
            this.shape = shape
            this.clip = clip
            this.ambientShadowColor = ambientColor
            this.spotShadowColor = spotColor
        }
    }
} else {
    this
}

/**
 * Check how this will appear on dark Mode
 * This is shadow Medium on figma
 * */
@Stable
fun Modifier.mediumShadow(
    elevation: Dp = 8.dp,
    shape: Shape = RectangleShape,
    clip: Boolean = elevation > 0.dp,
    ambientColor: Color = Color(0xFFCCCCCC).copy(alpha = 0.75f),
    spotColor: Color = Color(0xFFCCCCCC).copy(alpha = 0.75f)
) = if (elevation > 0.dp || clip) {
    inspectable(
        inspectorInfo = debugInspectorInfo {
            name = "shadow"
            properties["elevation"] = elevation
            properties["shape"] = shape
            properties["clip"] = clip
            properties["ambientColor"] = ambientColor
            properties["spotColor"] = spotColor
        }
    ) {
        graphicsLayer {
            this.shadowElevation = elevation.toPx()
            this.shape = shape
            this.clip = clip
            this.ambientShadowColor = ambientColor
            this.spotShadowColor = spotColor
        }
    }
} else {
    this
}