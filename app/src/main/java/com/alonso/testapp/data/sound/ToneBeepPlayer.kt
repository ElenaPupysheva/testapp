package com.alonso.testapp.data.sound

import android.media.AudioManager
import android.media.ToneGenerator
import com.alonso.testapp.utils.sound.SoundPlayer

class ToneBeepPlayer: SoundPlayer {
        private val toneGen = ToneGenerator(AudioManager.STREAM_MUSIC, 100)

        override fun beep() {
            toneGen.startTone(ToneGenerator.TONE_PROP_BEEP, 150)
        }

        fun release() {
            toneGen.release()
        }
}