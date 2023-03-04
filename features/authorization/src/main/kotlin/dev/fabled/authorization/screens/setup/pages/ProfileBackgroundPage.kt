package dev.fabled.authorization.screens.setup.pages

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.fabled.authorization.R
import dev.fabled.authorization.model.ProfileBackgroundModel
import dev.fabled.common.ui.components.GradientButton
import dev.fabled.common.ui.theme.Oxygen
import dev.fabled.common.ui.theme.PrimaryDark
import dev.fabled.common.ui.theme.PrimaryGradient
import dev.fabled.common.ui.theme.Roboto

@Composable
fun ProfileBackgroundScreen(
    selectedBackgroundModel: ProfileBackgroundModel,
    onBackgroundSelected: (ProfileBackgroundModel) -> Unit,
    onFinishClicked: () -> Unit
) {
    val backgroundImages = remember { ProfileBackgroundModel.getPictures() }

    Column(
        modifier = Modifier
            .padding(top = 15.dp, bottom = 80.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(R.string.customize_experience),
            color = Color.White,
            fontFamily = Oxygen,
            fontSize = 18.sp
        )
        LazyVerticalGrid(
            modifier = Modifier.fillMaxWidth(),
            columns = GridCells.Fixed(count = 2),
            contentPadding = PaddingValues(horizontal = 30.dp, vertical = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            items(items = backgroundImages) { model ->
                val borderColor by animateColorAsState(
                    targetValue = if (selectedBackgroundModel == model) PrimaryDark else Color.White
                )

                Column {
                    Text(
                        text = stringResource(id = model.imageName),
                        color = Color.White,
                        fontFamily = Roboto,
                        fontSize = 12.sp
                    )
                    Box(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .size(122.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .border(
                                width = 1.dp,
                                color = borderColor,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable { onBackgroundSelected(model) },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            modifier = Modifier
                                .size(108.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            painter = painterResource(id = model.backgroundPicture),
                            contentDescription = stringResource(id = model.imageName),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
        GradientButton(
            modifier = Modifier.fillMaxWidth(),
            gradient = PrimaryGradient,
            shape = RoundedCornerShape(8.dp),
            onClick = onFinishClicked
        ) {
            Text(text = stringResource(R.string.save))
        }
    }
}