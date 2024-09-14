package com.example.appkhambenh.ui.utils

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextUtils
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StrikethroughSpan
import android.text.style.StyleSpan
import android.util.Patterns
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

fun setStyleTextAtPosition(str: String, strChange: String, style: Any, spannable: Spannable) {
    val strRegex = Regex(strChange)
    val strMatchResult = strRegex.find(str)
    if (strMatchResult != null) {
        val strStartIndex = strMatchResult.range.first
        spannable.setSpan(
            style,
            strStartIndex,
            strStartIndex + strChange.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
}

fun validateEmail(email: String): Boolean {
    return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email)
        .matches()
}

fun validatePassword(password: String): Boolean {
    return !TextUtils.isEmpty(password) && password.length >= 6
}

fun validatePhone(phone: String): Boolean {
    return !TextUtils.isEmpty(phone) && (Patterns.PHONE.matcher(phone)
        .matches() && phone.length >= 10)
}

fun textNullOrEmpty(str: String?): String? {
    return if(str.isNullOrEmpty()) null else str
}