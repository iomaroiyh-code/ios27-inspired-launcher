package com.rexona.ios27launcher

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : Activity() {
    private fun dp(v: Int) = (v * resources.displayMetrics.density).toInt()
    private fun label(s: String, size: Float, color: Int = Color.WHITE) = TextView(this).apply {
        text=s; textSize=size; setTextColor(color); gravity=Gravity.CENTER
    }
    private fun rounded(c: Int) = GradientDrawable().apply { setColor(c); cornerRadius=dp(20).toFloat() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor=0xFF17244D.toInt()
        window.navigationBarColor=0xFF080D20.toInt()
        val page=LinearLayout(this).apply {
            orientation=LinearLayout.VERTICAL
            setPadding(dp(18),dp(20),dp(18),dp(24))
            background=GradientDrawable(GradientDrawable.Orientation.TL_BR,intArrayOf(0xFF213D7C.toInt(),0xFF39356F.toInt(),0xFF0B122B.toInt()))
        }
        val scroll=ScrollView(this); scroll.addView(page); setContentView(scroll)
        page.addView(label("●  iOS 27 Inspired",18f))
        page.addView(label(SimpleDateFormat("EEEE, d MMMM",Locale.getDefault()).format(Date()),16f,0xFFD8E1FF.toInt()))
        page.addView(label(SimpleDateFormat("HH:mm",Locale.getDefault()).format(Date()),68f))
        page.addView(label("Widget-style home screen",13f,0xFFD8E1FF.toInt()))
        page.addView(View(this),LinearLayout.LayoutParams(1,dp(20)))
        val widgets=LinearLayout(this).apply { orientation=LinearLayout.HORIZONTAL }
        widgets.addView(widget("☀","Weather","Weather card"),LinearLayout.LayoutParams(0,dp(100),1f))
        widgets.addView(View(this),LinearLayout.LayoutParams(dp(10),1))
        widgets.addView(widget("▦","Calendar",SimpleDateFormat("d MMM",Locale.getDefault()).format(Date())),LinearLayout.LayoutParams(0,dp(100),1f))
        page.addView(widgets)
        page.addView(View(this),LinearLayout.LayoutParams(1,dp(22)))
        page.addView(label("YOUR APPS",12f,0xFFD8E1FF.toInt()))
        val grid=GridLayout(this).apply { columnCount=4 }
        val apps=listOf(Triple("☎","Phone",0xFF36B66A.toInt()),Triple("✉","Email",0xFF4E91FF.toInt()),Triple("⚙","Settings",0xFF777F91.toInt()),Triple("◉","Browser",0xFF4C9CF4.toInt()),Triple("♫","Music",0xFFE45B85.toInt()),Triple("▣","Photos",0xFFA58BFF.toInt()),Triple("☷","Control",0xFF6C86FF.toInt()),Triple("◉","Camera",0xFF626B80.toInt()))
        apps.forEach { a ->
            val cell=LinearLayout(this).apply {
                orientation=LinearLayout.VERTICAL; gravity=Gravity.CENTER; setPadding(dp(2),dp(8),dp(2),dp(8))
                setOnClickListener {
                    try {
                        when(a.second) {
                            "Settings" -> startActivity(Intent(Settings.ACTION_SETTINGS))
                            "Phone" -> startActivity(Intent(Intent.ACTION_DIAL))
                            "Email" -> startActivity(Intent(Intent.ACTION_SENDTO,android.net.Uri.parse("mailto:")))
                            "Browser" -> startActivity(Intent(Intent.ACTION_VIEW,android.net.Uri.parse("https://www.google.com")))
                            "Control" -> AlertDialog.Builder(this@MainActivity).setTitle("Control Center").setMessage("System controls open Android settings for safety.").setPositiveButton("Wi-Fi") { _, _ -> startActivity(Intent(Settings.ACTION_WIFI_SETTINGS)) }.setNegativeButton("Bluetooth") { _, _ -> startActivity(Intent(Settings.ACTION_BLUETOOTH_SETTINGS)) }.show()
                            else -> AlertDialog.Builder(this@MainActivity).setMessage("No shortcut configured for "+a.second).setPositiveButton("OK",null).show()
                        }
                    } catch (_: Exception) {}
                }
            }
            cell.addView(label(a.first,25f).apply { background=rounded(a.third); layoutParams=LinearLayout.LayoutParams(dp(56),dp(56)) })
            cell.addView(label(a.second,11f))
            val lp=GridLayout.LayoutParams(GridLayout.spec(GridLayout.UNDEFINED,1f),GridLayout.spec(GridLayout.UNDEFINED)); lp.width=0
            grid.addView(cell,lp)
        }
        page.addView(grid)
        page.addView(View(this),LinearLayout.LayoutParams(1,dp(16)))
        val dock=LinearLayout(this).apply { gravity=Gravity.CENTER; background=rounded(0x44FFFFFF); setPadding(dp(8),dp(8),dp(8),dp(8)) }
        listOf("☎","◉","✉","▦").forEach { s -> dock.addView(label(s,24f).apply { background=rounded(0x553B75FF); layoutParams=LinearLayout.LayoutParams(dp(50),dp(50)).apply { setMargins(dp(5),0,dp(5),0) } }) }
        page.addView(dock)
        page.addView(View(this),LinearLayout.LayoutParams(1,dp(12)))
        page.addView(Button(this).apply { text="معاينة شاشة القفل"; setOnClickListener { startActivity(Intent(this@MainActivity,LockPreviewActivity::class.java)) } },LinearLayout.LayoutParams(-1,dp(52)))
    }
    private fun widget(icon:String,title:String,sub:String)=LinearLayout(this).apply {
        orientation=LinearLayout.VERTICAL; gravity=Gravity.CENTER_VERTICAL; setPadding(dp(12),dp(10),dp(12),dp(10)); background=rounded(0x33FFFFFF)
        addView(label(icon,22f)); addView(label(title,14f)); addView(label(sub,10f,0xFFD8E1FF.toInt()))
    }
}
