package com.asadbek.mediaplayertakrorlash

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.asadbek.mediaplayertakrorlash.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var mediaPlayer: MediaPlayer // musiqani ijro ettirish uchun player
    lateinit var handler: Handler // musiqa davomiyligini tekshrib turish uchun oqim
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mediaPlayer = MediaPlayer.create(this,R.raw.dillar) // mediaplayerni tanitib olish va musiqani ko`rsatish
        binding.myBar.max = mediaPlayer.duration // seekbarning davomiyligini mediaplayerdagi musiqaning uzunligiga tenglash

        // handler vazifasi
        /*
            berilgan vaqtdan keyin 1000(1 soniya) runnable nomli o`zgaruvchining ichidagi buyuruqlarni bajaradi
            bu buyuruqlar asosiy oqimga xalaqit bermagan xolda bo`ladi
         */
        
       // mediaPlayer.prepareAsync() // musiqani tayyorlash
        binding.btnPause.setOnClickListener { 
            mediaPlayer.pause() // musiqani to`xtatib turish
            Toast.makeText(this, "To`xtatildi", Toast.LENGTH_SHORT).show()
        }
        binding.btnStart.setOnClickListener {
            handler = Handler(Looper.getMainLooper()) // asosiy oqimda ishlash
            handler.postDelayed(runnable,1000)
            mediaPlayer.start() // musiqani kuylashni boshlash
            Toast.makeText(this, "Boshlandi", Toast.LENGTH_SHORT).show()
        }
        binding.btnStop.setOnClickListener { 
            mediaPlayer.stop() // musiqani yakunlash
            Toast.makeText(this, "Yakunlandi!", Toast.LENGTH_SHORT).show()
        }

        binding.myBar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser){ // fromUser - user tomonidan seekbar o`zgartirilganida true bo`ladi
                    mediaPlayer.seekTo(progress) // mediaPlayerdagi musiqani seekbar progressiga tenglab aytib ketadi
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

    }
    val runnable = object : Runnable{
        override fun run() {
            binding.myBar.progress = mediaPlayer.currentPosition
            handler.postDelayed(this,1000)
        }
    }

}