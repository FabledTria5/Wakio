package dev.fabled.on_boarding.model

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dev.fabled.common.ui.theme.Oxygen
import dev.fabled.common.ui.theme.PrimaryGradient
import dev.fabled.on_boarding.R

@Stable
sealed class OnBoardingPage(
    val page: Int,
    @DrawableRes val pageGif: Int,
    val pagePrimaryText: AnnotatedString,
    val pageSecondaryText: String,
    val nextPageAction: String
) {

    @OptIn(ExperimentalTextApi::class)
    object FirstPage : OnBoardingPage(
        page = 0,
        pageGif = R.drawable.on_boarding_1,
        pagePrimaryText = buildAnnotatedString {
            append(
                text = AnnotatedString(
                    text = "Morning habit ",
                    spanStyle = SpanStyle(
                        brush = PrimaryGradient,
                        fontFamily = Oxygen,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                )
            )
            append(
                text = AnnotatedString(
                    text = "can make a big difference in your dentire day choose wisely.",
                    spanStyle = SpanStyle(
                        color = Color.White,
                        fontFamily = Oxygen,
                        fontSize = 18.sp
                    )
                )
            )
        },
        pageSecondaryText = "Wakio is based on your behavioral psychology and Artificial intelligence.",
        nextPageAction = "Find Out More"
    )

    @OptIn(ExperimentalTextApi::class)
    object SecondPage : OnBoardingPage(
        page = 1,
        pageGif = R.drawable.on_boarding_2,
        pagePrimaryText = buildAnnotatedString {
            append(
                text = AnnotatedString(
                    text = "As per research ",
                    spanStyle = SpanStyle(
                        brush = PrimaryGradient,
                        fontFamily = Oxygen,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                )
            )
            append(
                text = AnnotatedString(
                    text = "Healthy morning routine makes people Active and Successful.",
                    spanStyle = SpanStyle(
                        color = Color.White,
                        fontFamily = Oxygen,
                        fontSize = 18.sp
                    )
                )
            )
        },
        pageSecondaryText = "Wakio helps to achieve your goals by making you wake and active.",
        nextPageAction = "Find Out More"
    )

    @OptIn(ExperimentalTextApi::class)
    object LastPage : OnBoardingPage(
        page = 2,
        pageGif = R.drawable.on_boarding_3,
        pagePrimaryText = buildAnnotatedString {
            append(
                text = AnnotatedString(
                    text = "Start a ",
                    spanStyle = SpanStyle(
                        color = Color.White,
                        fontFamily = Oxygen,
                        fontSize = 18.sp
                    )
                )
            )
            append(
                text = AnnotatedString(
                    text = "healthy life ",
                    spanStyle = SpanStyle(
                        brush = PrimaryGradient,
                        fontFamily = Oxygen,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                )
            )
            append(
                text = AnnotatedString(
                    text = "setup your goal get motivated and achieve all you success you deserve.",
                    spanStyle = SpanStyle(
                        color = Color.White,
                        fontFamily = Oxygen,
                        fontSize = 18.sp
                    )
                )
            )
        },
        pageSecondaryText = "hope to take the courage to pursue your dreams.",
        nextPageAction = "Next"
    )

}
