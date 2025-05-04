package com.example.personalexpensetracker.ext

import java.util.Locale

fun Double.formatCurrencyVietnam(): String {
    val amount = this
    val locale = Locale("vi", "VN")
    return when {
        amount >= 1_000_000_000 -> String.format(locale, "%.1f tỷ", amount / 1_000_000_000)
        amount >= 1_000_000 -> String.format(locale, "%.1f triệu", amount / 1_000_000)
        amount >= 1_000 -> String.format(locale,"%.1f nghìn", amount / 1_000)
        else -> String.format(locale, "%.0f", amount)
    }
}