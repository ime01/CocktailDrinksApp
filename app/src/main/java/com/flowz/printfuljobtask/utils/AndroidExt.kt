package com.flowz.printfuljobtask.utils

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
//import com.localazy.android.Localazy.getString
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * @author robin
 * Created on 10/11/20
 */

var mediaPlayer: MediaPlayer? = null


fun AppCompatActivity.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun TextInputEditText.takeWords() : String{
    return this.text.toString().trim()
}
fun EditText.takeWords() : String{
    return this.text.toString().trim()
}

fun playAnimation(context:Context, int: Int, view:View){

    val animation = AnimationUtils.loadAnimation(context, int)
    view.startAnimation(animation)
}

fun showToast(string: String, context: Context){

    Toast.makeText(context, string, Toast.LENGTH_LONG).show()
}

fun showSnackbar(view: View, string: String){

    Snackbar.make(view, string, Snackbar.LENGTH_LONG).show()
}

@Suppress("DEPRECATION")
fun getConnectionType(context: Context): Boolean {
    var result = false
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        cm?.run {
            cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                if (hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    result = true
                } else if (hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    result = true
                }
            }
        }
    } else {
        cm?.run {
            cm.activeNetworkInfo?.run {
                if (type == ConnectivityManager.TYPE_WIFI) {
                    result = true
                } else if (type == ConnectivityManager.TYPE_MOBILE) {
                    result = true
                }
            }
        }
    }
    return result
}

fun playContentUri(uri: Uri, context: Context) {
//        val mMediaPlayer = MediaPlayer().apply {

    if (mediaPlayer != null) {

        mediaPlayer?.stop()

        try {
            mediaPlayer = MediaPlayer().apply {
                setDataSource(context!!, uri)
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
                )
                prepare()
            }
            mediaPlayer?.start()
        } catch (e: IOException) {

            mediaPlayer = null
            mediaPlayer?.release()
        }
    } else {

        try {
            mediaPlayer = MediaPlayer().apply {

                setDataSource(context!!, uri)

                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
                )
                prepare()
            }
            mediaPlayer?.start()
        } catch (e: IOException) {

            mediaPlayer = null
            mediaPlayer?.release()
        }

    }
}

fun playContentInt(int: Int, context: Context) {

        if (mediaPlayer == null) {
                                mediaPlayer = MediaPlayer.create(context, int)
                                mediaPlayer?.start()

                            } else {
                                mediaPlayer?.stop()
                                mediaPlayer = MediaPlayer.create(context, int)
                                mediaPlayer?.start()
                            }
    }