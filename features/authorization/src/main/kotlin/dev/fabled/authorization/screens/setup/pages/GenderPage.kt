package dev.fabled.authorization.screens.setup.pages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.fabled.authorization.R
import dev.fabled.common.code.model.Gender
import dev.fabled.common.ui.components.GradientButton
import dev.fabled.common.ui.theme.Oxygen
import dev.fabled.common.ui.theme.PrimaryGradient

@Composable
fun GenderPage(
    selectedGenderModel: Gender? = null,
    onGenderSelected: (Gender?) -> Unit,
    onNextClicked: () -> Unit
) {
    val buttonsEnabled by remember(selectedGenderModel) { mutableStateOf(value = selectedGenderModel != null) }

    val outlinedButtonColor by animateColorAsState(
        targetValue = if (buttonsEnabled) Color.White else Color.Gray,
        animationSpec = tween(durationMillis = 500)
    )

    Column(
        modifier = Modifier
            .padding(top = 15.dp, bottom = 80.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(R.string.select_gender),
            color = Color.White,
            fontFamily = Oxygen,
            fontSize = 18.sp
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            AnimatedVisibility(
                visible = selectedGenderModel == Gender.Male || selectedGenderModel == null,
                enter = slideInHorizontally(
                    animationSpec = tween(durationMillis = 500),
                    initialOffsetX = { -it - 150 }
                ),
                exit = slideOutHorizontally(
                    animationSpec = tween(durationMillis = 500),
                    targetOffsetX = { -it - 150 }
                )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Image(
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape)
                            .clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = null
                            ) {
                                onGenderSelected(Gender.Male)
                            },
                        painter = painterResource(id = Gender.Male.icon),
                        contentDescription = stringResource(R.string.icon_male),
                        contentScale = ContentScale.Crop
                    )
                    Text(text = "Male", color = Color.White, fontFamily = Oxygen, fontSize = 18.sp)
                }
            }
            AnimatedVisibility(
                visible = selectedGenderModel == Gender.Female || selectedGenderModel == null,
                enter = slideInHorizontally(
                    animationSpec = tween(durationMillis = 500),
                    initialOffsetX = { it + 150 }
                ),
                exit = slideOutHorizontally(
                    animationSpec = tween(durationMillis = 500),
                    targetOffsetX = { it + 150 }
                )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Image(
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape)
                            .animateContentSize()
                            .clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = null
                            ) {
                                onGenderSelected(Gender.Female)
                            },
                        painter = painterResource(id = Gender.Female.icon),
                        contentDescription = stringResource(R.string.icon_female)
                    )
                    Text(
                        text = "Female",
                        color = Color.White,
                        fontFamily = Oxygen,
                        fontSize = 18.sp
                    )
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = { onGenderSelected(null) },
                shape = RoundedCornerShape(56.dp),
                border = BorderStroke(width = 1.dp, color = outlinedButtonColor),
                contentPadding = PaddingValues(vertical = 15.dp),
                enabled = selectedGenderModel != null
            ) {
                Text(
                    text = "Cancel",
                    color = outlinedButtonColor,
                    fontFamily = Oxygen,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
            GradientButton(
                modifier = Modifier
                    .weight(1f)
                    .animateContentSize(),
                gradient = PrimaryGradient,
                enabled = buttonsEnabled,
                shape = RoundedCornerShape(8.dp),
                onClick = onNextClicked
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 15.dp),
                    text = stringResource(R.string.next),
                    fontFamily = Oxygen,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}