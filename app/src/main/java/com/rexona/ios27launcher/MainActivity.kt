package com.rexona.ios27launcher

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : Activity() {
    private fun dp(v: Int) = (v * resources.displayMetrics.density).toInt()
    private fun txt(s: String, size: Float, color: Int = Color.WHITE, bold: Boolean = false) =
        TextView(this).apply {
            text = s; textSize = size; setTextColor(color); gravity = Gravity.CENTER
            if (bold) setTypeface(typeface, android.graphics.Typeface.BOLD)
        }
    private fun panel(color: Int, radius: Int = 22) = GradientDrawable().apply {
        setColor(color); cornerRadius = dp(radius).toFloat()
    }
    private fun gap(parent: LinearLayout, h: Int) {
        parent.addView(View(this), LinearLayout.LayoutParams(1, dp(h)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        renderHome()
    }

    private fun renderHome() {
        val colors = when (getSharedPreferences("theme", MODE_PRIVATE).getInt("theme", 0)) {
            1 -> intArrayOf(0xFF9369C9.toInt(), 0xFF30234F.toInt(), 0xFF0B1025.toInt())
            2 -> intArrayOf(0xFF35445D.toInt(), 0xFF182238.toInt(), 0xFF070B14.toInt())
            else -> intArrayOf(0xFF426CC2.toInt(), 0xFF494B9B.toInt(), 0xFF111A3B.toInt())
        }
        window.statusBarColor = colors[0]
        window.navigationBarColor = 0xFF080D20.toInt()
        val page = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(20), dp(16), dp(20), dp(24))
            background = GradientDrawable(GradientDrawable.Orientation.TL_BR, colors)
        }
        setContentView(ScrollView(this).apply { clipToPadding = false; addView(page) })

        val header = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL; gravity = Gravity.CENTER_VERTICAL }
        val heading = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL }
        heading.addView(txt("GOOD DAY", 11f, 0xFFDDE7FF.toInt(), true).apply { gravity = Gravity.START })
        heading.addView(txt("Your space", 25f, Color.WHITE, true).apply { gravity = Gravity.START })
        header.addView(heading, LinearLayout.LayoutParams(0, -2, 1f))
        header.addView(txt("◉", 23f).apply { background = panel(0x44FFFFFF, 50); layoutParams = LinearLayout.LayoutParams(dp(46), dp(46)) })
        page.addView(header)
        gap(page, 16)

        val date = SimpleDateFormat("EEEE, d MMMM", Locale.getDefault()).format(Date())
        val clock = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
        val clockCard = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL; gravity = Gravity.CENTER
            setPadding(dp(12), dp(12), dp(12), dp(12)); background = panel(0x22FFFFFF, 28)
        }
        clockCard.addView(txt(date, 14f, 0xFFE5EAFF.toInt()))
        clockCard.addView(txt(clock, 66f, Color.WHITE, true))
        clockCard.addView(txt("iOS-inspired • Android 15", 12f, 0xFFDDE7FF.toInt()))
        page.addView(clockCard, LinearLayout.LayoutParams(-1, dp(155)))
        gap(page, 12)

        val widgets = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        widgets.addView(widget("☀", "Weather", "Tap to search weather", 0x553B70CF), LinearLayout.LayoutParams(0, dp(106), 1f))
        widgets.addView(View(this), LinearLayout.LayoutParams(dp(10), 1))
        widgets.addView(widget("▦", "Calendar", date, 0x554F55A9), LinearLayout.LayoutParams(0, dp(106), 1f))
        page.addView(widgets)
        gap(page, 20)

        val titleRow = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL; gravity = Gravity.CENTER_VERTICAL }
        titleRow.addView(txt("Applications", 18f, Color.WHITE, true).apply { gravity = Gravity.START }, LinearLayout.LayoutParams(0, -2, 1f))
        titleRow.addView(txt("8 shortcuts", 11f, 0xFFDDE7FF.toInt()))
        page.addView(titleRow)
        val grid = GridLayout(this).apply { columnCount = 4 }
        val apps = listOf(
            Shortcut("☎", "Phone", 0xFF36B66A.toInt(), "phone"),
            Shortcut("✉", "Messages", 0xFF4E91FF.toInt(), "messages"),
            Shortcut("⚙", "Settings", 0xFF6E778B.toInt(), "settings"),
            Shortcut("◎", "Browser", 0xFF319CEB.toInt(), "browser"),
            Shortcut("▣", "Photos", 0xFFA18AFF.toInt(), "photos"),
            Shortcut("◉", "Camera", 0xFF59657C.toInt(), "camera"),
            Shortcut("⌁", "Control", 0xFF657FFF.toInt(), "control"),
            Shortcut("🔒", "Lock view", 0xFF3B4B78.toInt(), "lock")
        )
        apps.forEach { app ->
            val cell = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL; gravity = Gravity.CENTER
                setPadding(dp(2), dp(10), dp(2), dp(6)); isClickable = true; isFocusable = true
                setOnClickListener { launch(app.action) }
            }
            cell.addView(txt(app.glyph, 25f).apply {
                background = panel(app.color, 18); layoutParams = LinearLayout.LayoutParams(dp(56), dp(56)); elevation = dp(3).toFloat()
            })
            cell.addView(txt(app.name, 11f).apply { setPadding(0, dp(6), 0, 0) })
            grid.addView(cell, GridLayout.LayoutParams().apply {
                width = 0; columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
            })
        }
        page.addView(grid)
        gap(page, 8)

        val search = EditText(this).apply {
            hint = "⌕   Search the web"; textSize = 14f; setSingleLine(true)
            setTextColor(Color.WHITE); setHintTextColor(0xFFDDE7FF.toInt())
            setPadding(dp(16), 0, dp(16), 0); background = panel(0x33FFFFFF, 24)
            setOnEditorActionListener { _, _, _ -> searchWeb(this.text.toString()); true }
        }
        page.addView(search, LinearLayout.LayoutParams(-1, dp(50)))
        gap(page, 12)

        val dock = LinearLayout(this).apply {
            gravity = Gravity.CENTER; background = panel(0x44FFFFFF, 27)
            setPadding(dp(7), dp(7), dp(7), dp(7))
        }
        listOf("☎" to "phone", "◎" to "browser", "✉" to "messages", "▣" to "photos").forEach { item ->
            dock.addView(txt(item.first, 23f).apply {
                background = panel(0x554B7FFF, 17)
                layoutParams = LinearLayout.LayoutParams(0, dp(48), 1f).apply { setMargins(dp(4), 0, dp(4), 0) }
                setOnClickListener { launch(item.second) }
            })
        }
        page.addView(dock)
        gap(page, 12)

        val buttons = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        buttons.addView(actionButton("Lock screen preview") { startActivity(Intent(this@MainActivity, LockPreviewActivity::class.java)) }, LinearLayout.LayoutParams(0, dp(48), 1f))
        buttons.addView(View(this), LinearLayout.LayoutParams(dp(8), 1))
        buttons.addView(actionButton("Theme") { selectTheme() }, LinearLayout.LayoutParams(0, dp(48), 1f))
        page.addView(buttons)
        gap(page, 8)
        page.addView(txt("Visual launcher • Android security stays active", 10f, 0xFFDDE7FF.toInt()))
    }

    private data class Shortcut(val glyph: String, val name: String, val color: Int, val action: String)

    private fun widget(glyph: String, title: String, subtitle: String, color: Int) =
        LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL; gravity = Gravity.CENTER_VERTICAL
            setPadding(dp(13), dp(10), dp(8), dp(10)); background = panel(color, 22)
            addView(txt(glyph, 23f))
            addView(txt(title, 14f, Color.WHITE, true))
            addView(txt(subtitle, 10f, 0xFFE3E9FF.toInt()))
            setOnClickListener { if (title == "Weather") searchWeb("weather near me") else openCalendar() }
        }

    private fun actionButton(label: String, action: () -> Unit) = Button(this).apply {
        text = label; textSize = 12f; isAllCaps = false; setOnClickListener { action() }
    }

    private fun launch(action: String) {
        try {
            when (action) {
                "settings" -> startActivity(Intent(Settings.ACTION_SETTINGS))
                "phone" -> startActivity(Intent(Intent.ACTION_DIAL))
                "messages" -> startActivity(Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_APP_MESSAGING))
                "browser" -> startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com")))
                "photos" -> startActivity(Intent(Intent.ACTION_VIEW).setType("image/*"))
                "camera" -> startActivity(Intent("android.media.action.IMAGE_CAPTURE"))
                "control" -> AlertDialog.Builder(this).setTitle("Control Center")
                    .setItems(arrayOf("Wi-Fi", "Bluetooth", "Display", "Sound")) { _, which ->
                        startActivity(Intent(when (which) {
                            0 -> Settings.ACTION_WIFI_SETTINGS
                            1 -> Settings.ACTION_BLUETOOTH_SETTINGS
                            2 -> Settings.ACTION_DISPLAY_SETTINGS
                            else -> Settings.ACTION_SOUND_SETTINGS
                        }))
                    }.show()
                "lock" -> startActivity(Intent(this, LockPreviewActivity::class.java))
            }
        } catch (_: Exception) {
            AlertDialog.Builder(this).setMessage("This shortcut is not available on this device.")
                .setPositiveButton("OK", null).show()
        }
    }

    private fun openCalendar() {
        try { startActivity(Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_APP_CALENDAR)) }
        catch (_: Exception) { searchWeb("calendar") }
    }

    private fun searchWeb(query: String) {
        val q = if (query.isBlank()) "weather" else query
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=" + Uri.encode(q))))
    }

    private fun selectTheme() {
        AlertDialog.Builder(this).setTitle("Choose your style")
            .setItems(arrayOf("Ocean blue", "Purple dusk", "Midnight dark")) { _, which ->
                getSharedPreferences("theme", MODE_PRIVATE).edit().putInt("theme", which).apply()
                renderHome()
            }.show()
    }
}
