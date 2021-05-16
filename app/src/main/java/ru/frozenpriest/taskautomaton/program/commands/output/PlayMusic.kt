package ru.frozenpriest.taskautomaton.program.commands.output

import android.content.Context
import android.database.Cursor
import android.media.AudioAttributes
import android.media.AudioAttributes.USAGE_ALARM
import android.media.MediaPlayer
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import androidx.core.net.toUri
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeName
import ru.frozenpriest.taskautomaton.R
import ru.frozenpriest.taskautomaton.presentation.commands.CommandBuilder
import ru.frozenpriest.taskautomaton.program.Program
import ru.frozenpriest.taskautomaton.program.commands.Command
import ru.frozenpriest.taskautomaton.program.commands.CommandType
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


val allPlayers = mutableListOf<MediaPlayer>()

@JsonTypeName("PlayMusic")
class PlayMusic(
    @JsonProperty("songPath")
    var songPath: String
) : Command(
    name = "Play selected music",
    description = "",
    iconId = R.drawable.icon_sample,
    commandType = CommandType.Output,
    commandClass = CommandBuilder.CommandClass.PlayMusic
) {
    //content://com.android.providers.downloads.documents/document/raw%3A%2Fstorage%2Femulated%2F0%2FDownload%2FJohnny_Cash_-_Ring_of_Fire_48120126.mp3
    //
    override fun perform(program: Program, context: Context) {
        val songUri = Uri.parse(songPath)

        val mediaPlayer = MediaPlayer()
        try {
            val attributes =
                AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(USAGE_ALARM).build()
            mediaPlayer.setAudioAttributes(attributes)
            mediaPlayer.setDataSource(context, songUri)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        try {
            mediaPlayer.prepare()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        allPlayers.add(mediaPlayer)
        mediaPlayer.start()
        mediaPlayer.setOnCompletionListener {
            it.release()
            allPlayers.remove(it)
        }
    }

}

@JsonTypeName("StopMusic")
class StopMusic : Command(
    name = "Stop all music",
    description = "",
    iconId = R.drawable.icon_sample,
    commandType = CommandType.Output,
    commandClass = CommandBuilder.CommandClass.StopMusic
) {
    override fun perform(program: Program, context: Context) {
        for (player in allPlayers) {
            player.release()
        }
        allPlayers.clear()
    }

}


