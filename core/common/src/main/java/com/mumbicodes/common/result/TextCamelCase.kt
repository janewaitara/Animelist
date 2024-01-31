package com.mumbicodes.common.result

import java.util.Locale

fun String.toCamelCase(): String = this.replaceFirstChar {
    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
}