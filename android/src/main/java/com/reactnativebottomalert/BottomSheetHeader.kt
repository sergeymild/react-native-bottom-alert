package com.reactnativebottomalert

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import android.widget.LinearLayout
import android.widget.TextView
import android.view.Gravity
import android.util.TypedValue
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import com.facebook.react.uimanager.PixelUtil

@SuppressLint("ViewConstructor")
class BottomSheetHeader(
  context: Context?,
  title: String?,
  message: String?,
  isDark: Boolean
) : FrameLayout(context!!) {
  private val borderPaint: Paint
  private val density: Float = getContext().resources.displayMetrics.density
  private fun addTitle(text: String?, @ColorInt color: Int, layout: LinearLayout) {
    val textView = TextView(context)
    textView.gravity = Gravity.CENTER_VERTICAL
    textView.setPadding(0, 0, (density * 16).toInt(), 0)
    textView.text = text
    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
    textView.setTextColor(color)
    val layoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
    layoutParams.weight = 1f
    layout.addView(textView, layoutParams)
  }

  private fun addMessage(text: String?, @ColorInt color: Int, layout: LinearLayout) {
    val textView = TextView(context)
    textView.gravity = Gravity.CENTER_VERTICAL
    textView.setPadding(0, 0, (density * 16).toInt(), 0)
    textView.text = text
    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
    textView.setTextColor(color)
    val layoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
    layoutParams.weight = 1f
    layout.addView(textView, layoutParams)
  }

  init {
    var color = Color.LTGRAY
    if (isDark) color = Color.argb((255 * 0.6).toInt(), 225, 225, 225)
    borderPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    borderPaint.color = Color.parseColor("#F4F4F4")
    if (isDark) borderPaint.color = Color.parseColor("#2E2E2E")
    val linearLayout = LinearLayout(getContext())
    linearLayout.orientation = LinearLayout.VERTICAL
    addTitle(title, color, linearLayout)
    addMessage(message, color, linearLayout)
    val padding = (density * 16).toInt()
    val verticalPadding = (density * 8).toInt()
    addView(linearLayout, LayoutParams(MATCH_PARENT, MATCH_PARENT))
    linearLayout.setPadding(padding, verticalPadding, padding, verticalPadding)
    setWillNotDraw(false)
  }

  override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)
    canvas.drawLine(0f, height - PixelUtil.toDIPFromPixel(1f), width.toFloat(), height.toFloat(), borderPaint)
  }
}
