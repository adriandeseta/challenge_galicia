package com.example.challengegalicia.utils

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.challengegalicia.R
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

private val figtreeBlack = Font(R.font.figtree_black, FontWeight.Black)
private val figtreeBold = Font(R.font.figtree_bold, FontWeight.Bold)
private val figtreeExtraBold = Font(R.font.figtree_extra_bold, FontWeight.ExtraBold)
private val figtreeLight = Font(R.font.figtree_light, FontWeight.Light)
private val figtreeMedium = Font(R.font.figtree_medium, FontWeight.Medium)
private val figtreeRegular = Font(R.font.figtree_regular, FontWeight.Normal)

// Familia de fuentes
val figtreeFontFamily = FontFamily(
    figtreeBlack,
    figtreeBold,
    figtreeExtraBold,
    figtreeLight,
    figtreeMedium,
    figtreeRegular
)

@Composable
fun CustomText(
    modifier: Modifier = Modifier,
    text: String? = null,
    annotatedText: AnnotatedString? = null,
    textAlign: TextAlign = TextAlign.Start,
    fontSize: TextUnit = 16.sp,
    color: Color = Color.Black,
    fontWeight: FontWeight = FontWeight.Normal,
    style: TextStyle = TextStyle.Default,
    textDecoration: TextDecoration = TextDecoration.None
) {
    require(text != null || annotatedText != null) { "Debes pasar text o annotatedText" }

    Text(
        text = annotatedText ?: AnnotatedString(text!!),
        modifier = modifier,
        color = color,
        fontSize = fontSize,
        textAlign = textAlign,
        fontFamily = figtreeFontFamily,
        fontWeight = fontWeight,
        style = style,
        textDecoration = textDecoration
    )
}


fun String?.orPlaceholder(placeholder: String = "-"): String = this ?: placeholder
fun Int?.orPlaceholder(placeholder: String = "-"): String = this?.toString() ?: placeholder

fun String?.formatDateOrPlaceholder(placeholder: String = "No disponible"): String {
    if (this.isNullOrBlank()) return placeholder
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")
        val date = inputFormat.parse(this)
        val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        date?.let { outputFormat.format(it) } ?: placeholder
    } catch (e: Exception) {
        placeholder
    }
}
