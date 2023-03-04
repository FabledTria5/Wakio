package dev.fabled.on_boarding.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.fabled.common.ui.components.GifImage
import dev.fabled.common.ui.components.GradientButton
import dev.fabled.common.ui.theme.BackgroundColor
import dev.fabled.common.ui.theme.Oxygen
import dev.fabled.common.ui.theme.PrimaryGradient
import dev.fabled.on_boarding.OnBoardingViewModel
import dev.fabled.on_boarding.R
import dev.fabled.on_boarding.components.PagerIndicators
import dev.fabled.on_boarding.model.OnBoardingPage

@Composable
fun OnBoardingScreen(onBoardingViewModel: OnBoardingViewModel) {
    val currentPage by onBoardingViewModel.currentPage.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
        PageGif(
            currentPage = currentPage,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = .55f),
            onSkipClicked = onBoardingViewModel::skipOnBoarding
        )
        Text(
            modifier = Modifier
                .padding(horizontal = 15.dp, vertical = 24.dp)
                .animateContentSize(),
            text = currentPage.pagePrimaryText
        )
        Text(
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .animateContentSize(),
            text = currentPage.pageSecondaryText,
            color = Color.White,
            fontFamily = Oxygen,
            fontSize = 14.sp
        )
        PageIndication(
            modifier = Modifier
                .padding(top = 55.dp, bottom = 24.dp)
                .fillMaxWidth(),
            currentPage = currentPage,
            onNextClicked = onBoardingViewModel::openNextPage
        )
    }
}

@Composable
private fun PageGif(
    modifier: Modifier = Modifier,
    currentPage: OnBoardingPage,
    onSkipClicked: () -> Unit
) {
    Box(
        modifier = modifier
    ) {
        GifImage(
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(bottomEnd = 40.dp, bottomStart = 40.dp)),
            model = currentPage.pageGif
        )
        TextButton(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .statusBarsPadding()
                .padding(horizontal = 15.dp, vertical = 20.dp),
            onClick = onSkipClicked
        ) {
            Text(
                text = stringResource(R.string.skip),
                color = Color.White,
                fontFamily = Oxygen,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun PageIndication(
    modifier: Modifier = Modifier,
    currentPage: OnBoardingPage,
    onNextClicked: () -> Unit,
) {
    PagerIndicators(
        modifier = modifier,
        indicatorSize = 8.dp,
        pageCount = 3,
        currentPage = currentPage.page,
        activePageColor = PrimaryGradient,
        inActivePageColor = Color.Gray
    )
    GradientButton(
        modifier = Modifier
            .padding(horizontal = 15.dp)
            .fillMaxWidth(),
        onClick = onNextClicked,
        shape = RoundedCornerShape(8.dp),
        gradient = PrimaryGradient,
    ) {
        AnimatedContent(
            targetState = currentPage.nextPageAction,
            transitionSpec = {
                slideInHorizontally(
                    animationSpec = tween(durationMillis = 500),
                    initialOffsetX = { it }
                ) + fadeIn() with slideOutHorizontally(
                    animationSpec = tween(durationMillis = 500),
                    targetOffsetX = { 0 }
                ) + fadeOut()
            }
        ) { text ->
            Text(
                modifier = Modifier.padding(vertical = 18.dp),
                text = text,
                color = Color.White,
                fontFamily = Oxygen,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}