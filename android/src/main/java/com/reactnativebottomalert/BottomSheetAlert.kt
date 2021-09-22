package com.reactnativebottomalert

import android.app.Activity
import android.graphics.Color
import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider
import com.facebook.react.bridge.ReadableMap
import com.google.android.material.bottomsheet.BottomSheetDialog
import androidx.core.widget.NestedScrollView
import android.widget.LinearLayout
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.Callback
import com.google.android.material.bottomsheet.BottomSheetBehavior

class BottomSheetAlert(private val context: Activity, private val options: ReadableMap) {
  private val density = context.resources.displayMetrics.density
  fun create(isDark: Boolean, actionCallback: Callback): BottomSheetDialog? {
    val backgroundColor = if (isDark) Color.parseColor("#121212") else Color.WHITE

    val dialog = BottomSheetDialog(context)
    dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED

    val baseLayout = LinearLayout(context)

    baseLayout.orientation = LinearLayout.VERTICAL
    baseLayout.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT).also {
      it.bottomMargin = (density * 16).toInt()
      it.marginEnd = (density * 16).toInt()
      it.marginStart = (density * 16).toInt()
    }

    val buttonsContainer = LinearLayout(context)
    buttonsContainer.setBackgroundColor(backgroundColor)

    buttonsContainer.orientation = LinearLayout.VERTICAL
    val buttons = options.getArray("buttons") ?: return null
    val hasHeader = options.hasKey("title") || options.hasKey("message")
    val layoutParams = LinearLayout.LayoutParams(
      LinearLayout.LayoutParams.MATCH_PARENT,
      LinearLayout.LayoutParams.WRAP_CONTENT
    )
    if (hasHeader) {
      val header = BottomSheetHeader(
        context,
        options.getString("title"),
        options.getString("message"),
        isDark
      )
      header.outlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline) {
          val left = 0
          val top = 0;
          val right = view.width
          val bottom = view.height
          val cornerRadius = density * 20
          outline.setRoundRect(left, top, right, (bottom + cornerRadius).toInt(), cornerRadius)
        }
      }
      header.setBackgroundColor(backgroundColor)
      baseLayout.addView(header, layoutParams)
    }
    val clickListener = View.OnClickListener { v: View ->
      val tag = v.tag as Int
      dialog.dismiss()
      actionCallback.invoke(Arguments.fromList(listOf(tag)))
    }
    var cancelButtonIndex = -1
    for (i in 0 until buttons.size()) {
      val readableMap = buttons.getMap(i)
      val style = readableMap!!.getString("style")
      val text = readableMap.getString("text")
      val isCancel = style != null && style == "cancel"
      if (isCancel) {
        cancelButtonIndex = i
        continue
      }
      val isDestructive = style != null && style == "destructive"
      var color = if (isDark) Color.WHITE else Color.BLACK
      if (isDestructive) {
        color = if (isDark) Color.argb((255 * 0.4).toInt(), 176, 0, 32) else Color.rgb(176, 0, 32)
      }
      val listItemView = BottomSheetListItemView(context, i, text, color, false)
      listItemView.setOnClickListener(clickListener)
      buttonsContainer.addView(listItemView, layoutParams)
    }

    buttonsContainer.outlineProvider = object : ViewOutlineProvider() {
      override fun getOutline(view: View, outline: Outline) {
        val left = 0
        val top = 0;
        val right = view.width
        val bottom = view.height
        val cornerRadius = density * 20
        if (hasHeader || cancelButtonIndex != -1) {
          outline.setRoundRect(left, (top - cornerRadius).toInt(), right, bottom, cornerRadius)
        } else {
          outline.setRoundRect(left, top, right, bottom, cornerRadius)
        }
      }
    }
    buttonsContainer.clipToOutline = true
    baseLayout.addView(buttonsContainer)

    if (cancelButtonIndex != -1) {
      val readableMap = buttons.getMap(cancelButtonIndex)
      val text = readableMap!!.getString("text")
      val color = if (isDark) Color.WHITE else Color.BLACK
      val listItemView = BottomSheetListItemView(context, cancelButtonIndex, text, color, true)
      listItemView.setOnClickListener(clickListener)
      listItemView.setBackgroundColor(backgroundColor)
      baseLayout.addView(listItemView, LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
      ).also {
        it.topMargin = (density * 16).toInt()
      })

      listItemView.outlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline) {
          val left = 0
          val top = 0;
          val right = view.width
          val bottom = view.height
          val cornerRadius = density * 20
          outline.setRoundRect(left, top, right, bottom, cornerRadius)
        }
      }
      listItemView.clipToOutline = true
    }

    dialog.setContentView(baseLayout)
    val bottomSheet = dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
    bottomSheet?.setBackgroundColor(Color.TRANSPARENT)
    baseLayout.outlineProvider = object : ViewOutlineProvider() {
      override fun getOutline(view: View, outline: Outline) {
        val left = 0
        val top = 0;
        val right = view.width
        val bottom = view.height
        val cornerRadius = density * 20
        outline.setRoundRect(left, top, right, (bottom + cornerRadius).toInt(), cornerRadius)
      }
    }
    baseLayout.clipToOutline = true
    return dialog
  }

}
