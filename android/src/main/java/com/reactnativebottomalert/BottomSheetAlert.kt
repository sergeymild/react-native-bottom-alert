package com.reactnativebottomalert

import android.app.Activity
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.Callback
import com.facebook.react.bridge.ReadableMap
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class BottomSheetAlert(private val context: Activity, private val options: ReadableMap) {
  private val density = context.resources.displayMetrics.density
  fun create(isDark: Boolean, actionCallback: Callback): BottomSheetDialog? {
    val backgroundColor = if (isDark) Color.parseColor("#121212") else Color.WHITE

    val dialog = BottomSheetDialog(context)
    dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED

    val baseLayout = LinearLayout(context)

    baseLayout.orientation = LinearLayout.VERTICAL
    baseLayout.clipChildren = false
    baseLayout.clipToPadding = false
    baseLayout.setPadding(0, 0, 0, (density * 16).toInt())
    baseLayout.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT).also {
      it.marginEnd = (density * 16).toInt()
      it.marginStart = (density * 16).toInt()
    }

    val buttonsContainer = LayoutInflater.from(context).inflate(R.layout.base_layout, baseLayout, false) as CardView
    buttonsContainer.setCardBackgroundColor(backgroundColor)
    val verticalContainer = buttonsContainer.findViewById<LinearLayout>(R.id.vertical_container)

    val buttons = options.getArray("buttons") ?: return null
    val hasHeader = options.hasKey("title") || options.hasKey("message")
    val layoutParams = LinearLayout.LayoutParams(
      LinearLayout.LayoutParams.MATCH_PARENT,
      LinearLayout.LayoutParams.WRAP_CONTENT
    )
    if (hasHeader) {
      val header = LayoutInflater.from(context).inflate(R.layout.sheet_header, baseLayout, false) as BottomSheetHeader
      header.configure(
        options.getString("title"),
        options.getString("message"),
        isDark
      )
      header.setBackgroundColor(backgroundColor)
      verticalContainer.addView(header)
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
        color = if (isDark) Color.argb((255 * 0.8).toInt(), 176, 0, 32) else Color.rgb(176, 0, 32)
      }
      val listItemView = LayoutInflater.from(context).inflate(R.layout.sheet_button, verticalContainer, false) as TextView
      listItemView.tag = i
      listItemView.setTextColor(color)
      listItemView.text = text
      listItemView.setOnClickListener(clickListener)
      verticalContainer.addView(listItemView, layoutParams)
    }

    baseLayout.addView(buttonsContainer)

    if (cancelButtonIndex != -1) {
      val readableMap = buttons.getMap(cancelButtonIndex)
      val text = readableMap!!.getString("text")
      val color = if (isDark) Color.WHITE else Color.BLACK
      val listItemView = LayoutInflater.from(context).inflate(R.layout.sheet_cancel_button, baseLayout, false) as CardView
      val titleView = listItemView.findViewById<TextView>(R.id.title)
      titleView.setTextColor(color)
      titleView.text = text
      listItemView.tag = cancelButtonIndex
      listItemView.setOnClickListener(clickListener)
      listItemView.setCardBackgroundColor(backgroundColor)
      baseLayout.addView(listItemView)
    }

    dialog.setContentView(baseLayout)
    val bottomSheet = dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
    bottomSheet?.setBackgroundColor(Color.TRANSPARENT)
    return dialog
  }

}
