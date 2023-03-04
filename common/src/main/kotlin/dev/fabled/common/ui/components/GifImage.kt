package dev.fabled.common.ui.components

import android.os.Build
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size

@Composable
fun GifImage(
    modifier: Modifier = Modifier,
    @DrawableRes model: Int,
    contentScale: ContentScale = ContentScale.Crop
) {
    val context = LocalContext.current

    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    Image(
        modifier = modifier,
        painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(context)
                .data(model)
                .size(Size.ORIGINAL)
                .build(),
            imageLoader = imageLoader
        ),
        contentDescription = null,
        contentScale = contentScale
    )
}