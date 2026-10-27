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
    private fun dp(v:Int)=(v*resources.displayMetrics.density).toInt()
    private fun label(s:String,size:Float)=TextView(this).apply{text=s;textSize=size;setTextColor(Color.WHITE);gravity=Gravity.CENTER}
    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor=0xFF293D79.toInt(); window.navigationBarColor=0xFF0B123A.toInt()
        val page=LinearLayout(this).apply {
            orientation=LinearLayout.VERTICAL;gravity=Gravity.CENTER_HORIZONTAL;setPadding(dp(22),dp(24),dp(22),dp(24))
            background=GradientDrawable(GradientDrawable.Orientation.TL_BR,intArrayOf(0xFF5B82DC.toInt(),0xFF49449D.toInt(),0xFF0C173A.toInt()))
        }
        page.addView(label("● ● ●     4G     ▰",13f))
        page.addView(View(this),LinearLayout.LayoutParams(1,dp(48)))
        page.addView(label(SimpleDateFormat("EEEE, d MMMM",Locale.getDefault()).format(Date()),19f))
        page.addView(label(SimpleDateFormat("HH:mm",Locale.getDefault()).format(Date()),76f))
        page.addView(View(this),LinearLayout.LayoutParams(1,0,1f))
        val notice=LinearLayout(this).apply { orientation=LinearLayout.VERTICAL;gravity=Gravity.CENTER;setPadding(dp(12),dp(12),dp(12),dp(12));background=GradientDrawable().apply{setColor(0x44FFFFFF);cornerRadius=dp(22).toFloat()} }
        notice.addView(label("الإشعارات",16f)); notice.addView(label("معاينة فقط — لا تعرض إشعارات حقيقية",12f))
        page.addView(notice,LinearLayout.LayoutParams(-1,dp(88)))
        page.addView(View(this),LinearLayout.LayoutParams(1,dp(26)))
        page.addView(label("◉                         ☼",26f))
        page.addView(label("حماية Android الأصلية لم تتغير",11f))
        page.setOnClickListener { finish() }; setContentView(page)
    }
}
