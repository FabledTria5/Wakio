package dev.fabled.alarm.utils

import android.content.Context
import android.media.MediaPlayer
import androidx.annotation.RawRes

fun playRawResAudio(context: Context, mediaPlayer: MediaPlayer, @RawRes audioRes: Int) {
    val assertFileDescriptor = context.resources.openRawResourceFd(audioRes)

    assertFileDescriptor.use { descriptor ->
        mediaPlayer.setDataSource(
            descriptor.fileDescriptor,
            descriptor.startOffset,
            descriptor.length
        )
    }

    mediaPlayer.prepare()
    mediaPlayer.start()
}