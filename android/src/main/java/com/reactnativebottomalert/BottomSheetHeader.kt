package com.reactnativebottomalert

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import com.facebook.react.uimanager.PixelUtil

@SuppressLint("ViewConstructor")
class BottomSheetHeader(context: Context, attrs: AttributeSet?): LinearLayout(context, attrs) {
  private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG)
  private fun addTitle(text: String?, @ColorInt color: Int, layout: LinearLayout) {
    if (text == null) return
    val textView = layout.findViewById<TextView>(R.id.title)
    textView.text = text
    textView.visibility = View.VISIBLE
    textView.setTextColor(color)
  }

  private fun addMessage(text: String?, @ColorInt color: Int, layout: LinearLayout) {
    if (text == null) return
    val textView = layout.findViewById<TextView>(R.id.message)
    textView.text = text
    textView.visibility = View.VISIBLE
    textView.setTextColor(color)
  }

  fun configure(
    title: String?,
    message: String?,
    isDark: Boolean
  ) {
    var color = Color.LTGRAY
    if (isDark) color = Color.argb((255 * 0.6).toInt(), 225, 225, 225)
    borderPaint.color = Color.parseColor("#F4F4F4")
    if (isDark) borderPaint.color = Color.parseColor("#2E2E2E")
    addTitle(title, color, this)
    addMessage(message, color, this)
    setWillNotDraw(false)
  }

  override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)
    canvas.drawLine(0f, height - PixelUtil.toDIPFromPixel(1f), width.toFloat(), height.toFloat(), borderPaint)
  }
}
