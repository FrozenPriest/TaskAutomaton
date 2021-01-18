package ru.frozenpriest.taskautomaton.utils

import android.content.Context
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.CountDownTimer
import android.text.Html
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.TextView
import ru.frozenpriest.taskautomaton.R

class HtmlViewBuilder(private val applicationContext: Context, private val windowManager: WindowManager) {
    private val params = WindowManager.LayoutParams(
        WindowManager.LayoutParams.WRAP_CONTENT,
        WindowManager.LayoutParams.WRAP_CONTENT,
        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
        PixelFormat.TRANSLUCENT
    )

    private var html: String = ""
    private var backgroundColor: Int = Color.WHITE
    private var textColor: Int = Color.BLACK
    private var time = -1L

    fun setGravity(gravity: Int) : HtmlViewBuilder {
        params.gravity = gravity
        return this
    }

    fun setBackgroundColor(hexColor: String): HtmlViewBuilder {
        backgroundColor = Color.parseColor(hexColor)
        return this
    }

    fun setTextColor(hexColor: String): HtmlViewBuilder {
        textColor = Color.parseColor(hexColor)
        return this
    }

    fun setHtml(html: String): HtmlViewBuilder {
        this.html = html
        return this
    }

    fun setExpireTime(time: Long): HtmlViewBuilder {
        this.time = time
        return this
    }

    fun build() {
        params.x = 0
        params.y = 0


        val view = LayoutInflater.from(applicationContext).inflate(R.layout.view_html_popup, null)
        val textView: TextView = view.findViewById(R.id.textViewHtml)
        textView.text = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
        textView.setOnClickListener { windowManager.removeView(view) }
        textView.setBackgroundColor(backgroundColor)
        textView.setTextColor(textColor)
        if(time > 0) {
            val timer = object: CountDownTimer(time, time) {
                override fun onTick(millisUntilFinished: Long) {}

                override fun onFinish() {windowManager.removeView(view)}
            }
            timer.start()
        }

        windowManager.addView(view, params)
    }
}