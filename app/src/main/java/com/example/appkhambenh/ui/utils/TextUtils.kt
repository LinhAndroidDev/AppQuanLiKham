package com.example.appkhambenh.ui.utils

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StrikethroughSpan
import android.text.style.StyleSpan
import com.example.appkhambenh.R
import java.text.Normalizer

/**
 * Xem thêm và thu gọn text
 */
fun collapseText(context: Context, text: String): SpannableStringBuilder {
    val spannable = SpannableStringBuilder(text)
    spannable.setSpan(
        ForegroundColorSpan(context.getColor(R.color.text)),
        124,
        text.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    spannable.setSpan(
        StyleSpan(Typeface.BOLD),
        124,
        text.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    spannable.setSpan(
        AbsoluteSizeSpan(14, true),
        124,
        text.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    return spannable
}

/**
 * Gạch ngang text
 */
fun strikethroughText(txt: String): SpannableString {
    val spannableString = SpannableString(txt)
    val strikethroughSpan = StrikethroughSpan()
    spannableString.setSpan(strikethroughSpan, 0, txt.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    return spannableString
}

fun removeAccents(str: String): String {
    val normalizedString = Normalizer.normalize(str, Normalizer.Form.NFD)
    return normalizedString.replace(Regex("\\p{InCombiningDiacriticalMarks}+"), "").trim()
}