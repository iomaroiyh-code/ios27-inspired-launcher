package com.rexona.ios27launcher

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LockPreviewActivity : Activity() {
    private fun dp(v: Int) = (v * resources.displayMetrics.density).toInt()
    private fun label(value: String, size: Float, color: Int = Color.WHITE, bold: Boolean = false) =
        TextView(this).apply {
            text = value; textSize = size; setTextColor(color); gravity = Gravity.CENTER
            if (bold) setTypeface(typeface, android.graphics.Typeface.BOLD)
        }
    private fun glass() = GradientDrawable().apply {
        setColor(0x30FFFFFF); cornerRadius = dp(24).toFloat()
        setStroke(dp(1), 0x33FFFFFF)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = 0xFF253B75.toInt()
        window.navigationBarColor = 0xFF0B1230.toInt()
        val colors = when (getSharedPreferences("theme", MODE_PRIVATE).getInt("theme", 0)) {
            1 -> intArrayOf(0xFF9D75D4.toInt(), 0xFF4A347B.toInt(), 0xFF10132E.toInt())
            2 -> intArrayOf(0xFF35445D.toInt(), 0xFF182238.toInt(), 0xFF070B14.toInt())
            else -> intArrayOf(0xFF6B95E9.toInt(), 0xFF454B9D.toInt(), 0xFF0C173A.toInt())
        }
        val page = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            setPadding(dp(22), dp(20), dp(22), dp(22))
            background = GradientDrawable(GradientDrawable.Orientation.TL_BR, colors)
        }
        val status = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL; gravity = Gravity.CENTER }
        status.addView(label("● ● ●", 12f))
        status.addView(label("     4G  ▰", 12f))
        page.addView(status)
        page.addView(View(this), LinearLayout.LayoutParams(1, dp(42)))

        page.addView(label("◈", 26f))
        page.addView(label(SimpleDateFormat("EEEE, d MMMM", Locale.getDefault()).format(Date()), 18f, 0xFFF0F3FF.toInt()))
        page.addView(label(SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date()), 78f, Color.WHITE, true))
        page.addView(label("A little moment for yourself", 13f, 0xFFE1E8FF.toInt()))

        page.addView(View(this), LinearLayout.LayoutParams(1, 0, 1f))
        val notification = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(15), dp(13), dp(15), dp(13))
            background = glass()
        }
        notification.addView(label("NOTIFICATIONS", 11f, 0xFFE5EAFF.toInt(), true).apply { gravity = Gravity.START })
        notification.addView(label("Your notifications will stay in Android", 13f, Color.WHITE).apply {
            gravity = Gravity.START; setPadding(0, dp(8), 0, 0)
        })
        page.addView(notification, LinearLayout.LayoutParams(-1, dp(84)))
        page.addView(View(this), LinearLayout.LayoutParams(1, dp(24)))

        val shortcuts = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL; gravity = Gravity.CENTER }
        shortcuts.addView(label("◉", 25f).apply {
            background = glass(); layoutParams = LinearLayout.LayoutParams(dp(54), dp(54))
            setOnClickListener { finish() }
        })
        shortcuts.addView(View(this), LinearLayout.LayoutParams(0, 1, 1f))
        shortcuts.addView(label("☼", 25f).apply {
            background = glass(); layoutParams = LinearLayout.LayoutParams(dp(54), dp(54))
            setOnClickListener { finish() }
        })
        page.addView(shortcuts)
        page.addView(View(this), LinearLayout.LayoutParams(1, dp(16)))
        page.addView(label("Tap anywhere to return", 12f, 0xFFE1E8FF.toInt()))
        page.addView(label("Preview only • Android lock and biometrics unchanged", 10f, 0xFFD0DAF9.toInt()))
        page.setOnClickListener { finish() }
        setContentView(page)
    }
}
