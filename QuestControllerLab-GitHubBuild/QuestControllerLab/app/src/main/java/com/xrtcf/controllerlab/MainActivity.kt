package com.xrtcf.controllerlab

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.*
import android.content.Context

class MainActivity : Activity() {
    private val prefs by lazy { getSharedPreferences("controller_lab", Context.MODE_PRIVATE) }
    private lateinit var status: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.rgb(12, 15, 22)
        window.navigationBarColor = Color.rgb(12, 15, 22)

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(34, 24, 34, 24)
            setBackgroundColor(Color.rgb(12, 15, 22))
        }
        val title = TextView(this).apply {
            text = "CONTROLLER LAB  /  QUEST 2"
            textSize = 25f; setTextColor(Color.WHITE); gravity = Gravity.CENTER_VERTICAL
        }
        root.addView(title, matchWrap())
        val subtitle = TextView(this).apply {
            text = "Local tuning profile • values are saved on this headset"
            textSize = 14f; setTextColor(Color.LTGRAY); setPadding(0, 5, 0, 20)
        }
        root.addView(subtitle, matchWrap())

        addSlider(root, "Smoothing", "Lower = more responsive; higher = steadier", "smoothing", 0, 100, 25, "%")
        addSlider(root, "Virtual reach", "Profile value only; does not alter other games", "reach", 50, 200, 100, "%")
        addSlider(root, "Response", "Profile value for a future compatible integration", "response", 25, 200, 100, "%")

        val buttons = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL; gravity = Gravity.CENTER_VERTICAL }
        val reset = Button(this).apply {
            text = "RESET PROFILE"
            setOnClickListener {
                prefs.edit().clear().apply()
                recreate()
            }
        }
        buttons.addView(reset, LinearLayout.LayoutParams(0, 58, 1f))
        val calibrate = Button(this).apply {
            text = "CALIBRATION CHECK"
            setOnClickListener {
                status.text = "Profile check complete. No live controller pose is connected in this build."
            }
        }
        val lp = LinearLayout.LayoutParams(0, 58, 1f); lp.leftMargin = 12
        buttons.addView(calibrate, lp)
        root.addView(buttons)

        status = TextView(this).apply {
            text = "READY • Profile saved locally"
            textSize = 14f; setTextColor(Color.rgb(125, 220, 170)); setPadding(0, 22, 0, 0)
        }
        root.addView(status, matchWrap())
        val note = TextView(this).apply {
            text = "LIMITATION: This is a settings/profile utility, not a system tracking modifier. Quest apps own their OpenXR sessions; GILLA would need to support an integration before these values could change its controller poses."
            textSize = 13f; setTextColor(Color.LTGRAY); setPadding(0, 18, 0, 0)
        }
        root.addView(note, matchWrap())
        setContentView(root)
    }

    private fun addSlider(root: LinearLayout, heading: String, help: String, key: String, min: Int, max: Int, default: Int, suffix: String) {
        val label = TextView(this).apply { setTextColor(Color.WHITE); textSize = 18f }
        fun update(v: Int) { label.text = "$heading: $v$suffix" }
        val current = prefs.getInt(key, default).coerceIn(min, max)
        update(current)
        root.addView(label, matchWrap())
        val detail = TextView(this).apply { text = help; textSize = 12f; setTextColor(Color.LTGRAY) }
        root.addView(detail, matchWrap())
        val seek = SeekBar(this).apply {
            max = max - min
            progress = current - min
            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    val v = min + progress; update(v)
                    if (fromUser) prefs.edit().putInt(key, v).apply()
                }
                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })
        }
        root.addView(seek, LinearLayout.LayoutParams(-1, 48))
    }

    private fun matchWrap() = LinearLayout.LayoutParams(-1, -2)
}
