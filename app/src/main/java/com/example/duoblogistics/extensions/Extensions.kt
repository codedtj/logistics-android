package com.example.duoblogistics.extensions

import android.content.Context
import android.media.MediaPlayer
import android.media.RingtoneManager
import com.example.duoblogistics.R
import android.util.Log


fun Context.notificationSignal() {
    try {
        val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val r = RingtoneManager.getRingtone(applicationContext, notification)
        r.play()
    } catch (e: Exception) {
        e.printStackTrace()
    }

}

fun Context.playSuccessSound() {
    val mPlayer = MediaPlayer.create(this, R.raw.scanner_beep)
    mPlayer.setOnCompletionListener { mPlayer.stopPlay() }
    mPlayer.start()
}

//fun Context.playFailSound() {
//    val mPlayer = MediaPlayer.create(this, R.raw.wrong_alert_beep_sound)
//    mPlayer.setOnCompletionListener { mPlayer.stopPlay() }
//    mPlayer.start()
//}

fun MediaPlayer.stopPlay() {
    this.stop()
    try {
        this.prepare()
        this.seekTo(0)
    } catch (t: Throwable) {
        Log.e("media-player", "Failed to stop player " + t.message)
    }
}