package dev.fabled.home.screens

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.with
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import dev.fabled.common.ui.theme.BackgroundColor
import dev.fabled.common.ui.theme.LightRed
import dev.fabled.common.ui.theme.OrangeGradient
import dev.fabled.common.ui.theme.Oxygen
import dev.fabled.common.ui.theme.PinkGradient
import dev.fabled.common.ui.theme.PrimaryDark
import dev.fabled.common.ui.theme.PrimaryGradient
import dev.fabled.domain.model.Resource
import dev.fabled.home.HomeViewModel
import dev.fabled.home.R
import dev.fabled.home.components.ScheduledAlarmPopup
import dev.fabled.home.model.UiArticle
import dev.fabled.home.model.WelcomeData
import dev.fabled.navigation.navigation_directions.AlarmDirections
import dev.fabled.navigation.navigation_directions.HomeDirections

@Composable
fun HomeScreen(modifier: Modifier = Modifier, homeViewModel: HomeViewModel) {
    val context = LocalContext.current

    val welcomeData by homeViewModel.welcomeData.collectAsStateWithLifecycle()
    val dailyQuote by homeViewModel.dailyQuote.collectAsStateWithLifecycle()
    val notificationsCount by homeViewModel.notificationsCount.collectAsStateWithLifecycle()
    val dailyArticle by homeViewModel.dailyArticle.collectAsStateWithLifecycle()

    val onShowWebContentClick: (String) -> Unit = remember {
        { contentUrl ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(contentUrl))
            context.startActivity(intent)
        }
    }

    BackHandler(onBack = homeViewModel::navigateUp)

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(top = 30.dp)
            .padding(horizontal = 15.dp)
    ) {
        HomeTopBar(
            modifier = Modifier.fillMaxWidth(),
            welcomeData = welcomeData,
            showNotifications = notificationsCount > 0,
            onNotificationsClick = {
                homeViewModel.navigate(route = HomeDirections.NotificationsScreen.route())
            }
        )
        DailyQuote(
            modifier = Modifier
                .padding(top = 25.dp)
                .fillMaxWidth(),
            dailyQuote = dailyQuote
        )
        ScheduledAlarm(
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth()
                .height(135.dp),
            onPreviewAlarmClick = {
                homeViewModel.navigate(route = AlarmDirections.AlarmEditScreen.route())
            }
        )
        ActivityStatistics(
            modifier = Modifier
                .padding(vertical = 15.dp)
                .fillMaxWidth()
        )
        StatsButton(modifier = Modifier.fillMaxWidth(), onClick = homeViewModel::openUserStats)
        when (val article = dailyArticle) {
            is Resource.Success -> FitnessArticles(
                modifier = Modifier.padding(vertical = 10.dp),
                article = article.data,
                onShowContentClick = onShowWebContentClick
            )

            else -> Unit
        }
    }
}

@Composable
private fun HomeTopBar(
    modifier: Modifier = Modifier,
    welcomeData: WelcomeData,
    showNotifications: Boolean,
    onNotificationsClick: () -> Unit
) {
    val context = LocalContext.current

    val imageRequest = ImageRequest.Builder(context)
        .crossfade(enable = true)
        .data(welcomeData.userGender?.icon)
        .placeholder(ColorDrawable(0xFF212327.toInt()))
        .build()

    BackgroundColor

    val painter = rememberAsyncImagePainter(model = imageRequest)

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape),
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.padding(start = 10.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = welcomeData.username,
                    color = Color.White,
                    fontFamily = Oxygen,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Row(
                    modifier = Modifier.padding(top = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(end = 5.dp)
                            .size(20.dp),
                        imageVector = Icons.Default.CalendarToday,
                        contentDescription = null,
                        tint = Color.White
                    )
                    Text(
                        text = welcomeData.currentTime,
                        color = Color.White,
                        fontFamily = Oxygen,
                        fontSize = 16.sp
                    )
                }
            }
        }
        IconButton(onClick = onNotificationsClick) {
            Icon(
                painter = painterResource(id = R.drawable.ic_notifications),
                contentDescription = null,
                tint = Color.White
            )
            if (showNotifications)
                Canvas(
                    modifier = Modifier
                        .padding(start = 10.dp, bottom = 20.dp)
                        .size(10.dp)
                ) {
                    drawCircle(color = LightRed)
                }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun DailyQuote(modifier: Modifier = Modifier, dailyQuote: String) {
    AnimatedVisibility(
        visible = dailyQuote.isNotEmpty(),
        enter = expandVertically(),
        exit = shrinkVertically()
    ) {
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF3C3D3F))
        ) {
            Icon(
                modifier = Modifier.padding(start = 10.dp, top = 20.dp),
                painter = painterResource(id = R.drawable.ic_quotes),
                contentDescription = null,
                tint = Color.Unspecified
            )
            AnimatedContent(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 30.dp, vertical = 45.dp),
                targetState = dailyQuote,
                transitionSpec = { fadeIn() with fadeOut() },
                label = "daily_quote_animation"
            ) { animatedText ->
                Text(
                    text = animatedText,
                    color = Color.White,
                    fontFamily = Oxygen,
                    fontSize = 16.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
private fun ScheduledAlarm(modifier: Modifier = Modifier, onPreviewAlarmClick: () -> Unit) {
    val daysList = listOf("S", "M", "T", "W", "T", "F", "S")

    var isPopUpEnabled by remember { mutableStateOf(value = false) }

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF353535))
    ) {
        Column(
            modifier = Modifier
                .padding(start = 15.dp, top = 12.dp, bottom = 12.dp)
                .fillMaxWidth()
                .weight(.7f)
        ) {
            Text(
                text = "Scheduled Alarm",
                style = TextStyle(brush = PrimaryGradient),
                fontFamily = Oxygen,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Row(
                modifier = Modifier
                    .padding(top = 5.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = buildAnnotatedString {
                        append(
                            text = AnnotatedString(
                                text = "5:30",
                                spanStyle = SpanStyle(
                                    color = Color.White,
                                    fontFamily = Oxygen,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 32.sp
                                )
                            )
                        )
                        append(
                            text = AnnotatedString(
                                text = "AM",
                                spanStyle = SpanStyle(
                                    color = Color.White,
                                    fontFamily = Oxygen,
                                    fontSize = 24.sp
                                )
                            )
                        )
                    }
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Switch(
                        checked = true,
                        onCheckedChange = {},
                        colors = SwitchDefaults.colors(
                            checkedTrackColor = PrimaryDark,
                            uncheckedThumbColor = Color.White,
                            uncheckedBorderColor = Color.Transparent,
                            uncheckedTrackColor = Color.Gray
                        )
                    )
                    IconButton(onClick = { isPopUpEnabled = !isPopUpEnabled }) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
                    }
                    ScheduledAlarmPopup(
                        enabled = isPopUpEnabled,
                        onDismiss = { isPopUpEnabled = false },
                        onDeleteClick = { },
                        onPreviewClick = onPreviewAlarmClick
                    )
                }
            }
        }
        Row(
            Modifier
                .fillMaxWidth()
                .weight(.3f)
                .background(Color(0xFF474747).copy(alpha = .72f))
                .padding(horizontal = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = buildAnnotatedString {
                    daysList.forEachIndexed { index, s ->
                        append(
                            AnnotatedString(
                                text = s,
                                spanStyle = SpanStyle(
                                    color = if (index in 1..5)
                                        Color.White
                                    else
                                        Color(0xFF979797),
                                    fontWeight = if (index in 1..5)
                                        FontWeight.Bold
                                    else
                                        FontWeight.Light
                                )
                            )
                        )
                        append(" ")
                    }
                },
                fontFamily = Oxygen,
                fontSize = 16.sp
            )
        }
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
private fun ActivityStatistics(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = "My Activity",
            style = TextStyle(brush = PrimaryGradient),
            fontFamily = Oxygen,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Row(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ActivityBar(
                modifier = Modifier.weight(1f),
                barGradient = PinkGradient,
                barName = "Yoga",
                barIcon = painterResource(id = R.drawable.ic_yoga)
            )
            ActivityBar(
                modifier = Modifier.weight(1f),
                barGradient = PrimaryGradient,
                barName = "Steps",
                barIcon = painterResource(id = R.drawable.ic_steps)
            )
            ActivityBar(
                modifier = Modifier.weight(1f),
                barGradient = OrangeGradient,
                barName = "Calories",
                barIcon = painterResource(id = R.drawable.ic_calories)
            )
        }
    }
}

@Composable
private fun ActivityBar(
    modifier: Modifier = Modifier,
    barGradient: Brush,
    barName: String,
    barIcon: Painter
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.aspectRatio(1f),
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier.matchParentSize()
            ) {
                drawCircle(
                    brush = barGradient,
                    alpha = .1f,
                    radius = size.minDimension / 2.5f
                )
                drawCircle(color = Color(0xFF525252), style = Stroke(width = 15f))
                drawArc(
                    brush = barGradient,
                    startAngle = 90f,
                    sweepAngle = 270f,
                    useCenter = false,
                    style = Stroke(width = 15f)
                )
            }
            Icon(
                painter = barIcon,
                contentDescription = null,
                tint = Color.White
            )
        }
        Text(
            modifier = Modifier.padding(top = 10.dp),
            text = barName,
            color = Color.White,
            fontFamily = Oxygen,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    }
}

@Composable
private fun StatsButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF3C3D3F))
            .clickable { onClick() }
            .padding(horizontal = 15.dp, vertical = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.ic_stats),
                contentDescription = null,
                tint = Color.Unspecified
            )
            Text(
                modifier = Modifier.padding(start = 15.dp),
                text = stringResource(R.string.my_stats),
                color = Color.White,
                fontFamily = Oxygen,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        }
        Icon(
            modifier = Modifier.size(34.dp),
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = null,
            tint = Color.White
        )
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun FitnessArticles(
    modifier: Modifier = Modifier,
    article: UiArticle,
    onShowContentClick: (String) -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.articles),
            style = TextStyle(brush = PrimaryGradient),
            fontFamily = Oxygen,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Column(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF393C41))
                .clickable { onShowContentClick(article.url) }
                .padding(15.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(RoundedCornerShape(16.dp)),
                model = article.imagePath,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Text(
                modifier = Modifier.padding(vertical = 10.dp),
                text = article.articleTitle,
                color = Color.White,
                fontFamily = Oxygen,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = article.articleText,
                color = Color.White.copy(alpha = .7f),
                fontFamily = Oxygen,
                fontSize = 16.sp
            )
        }
        TextButton(
            modifier = Modifier.align(Alignment.End),
            onClick = {
                onShowContentClick("https://www.everydayhealth.com/fitness/all-articles/")
            }
        ) {
            Text(
                text = "Read more",
                style = TextStyle(brush = PrimaryGradient),
                fontFamily = Oxygen,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}