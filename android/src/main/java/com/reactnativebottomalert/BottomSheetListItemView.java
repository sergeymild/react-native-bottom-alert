package com.reactnativebottomalert;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.react.uimanager.PixelUtil;

public class BottomSheetListItemView extends LinearLayout {
  private final float density;

  public BottomSheetListItemView(Context context, int tag, String title, int textColor, boolean isBold) {
    super(context);
    density = getContext().getResources().getDisplayMetrics().density;
    setTag(tag);
    setOrientation(HORIZONTAL);
    //setPadding(padding, 0, padding, 0);

    createButton(title, textColor, isBold);
  }

  private void createButton(String text, int color, boolean isBold) {
    int padding = (int) (16 * density);
    TextView textView = new TextView(getContext());
    textView.setGravity(Gravity.CENTER_VERTICAL);
    textView.setText(text);
    TypedValue outValue = new TypedValue();
    this.getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
    textView.setBackgroundResource(outValue.resourceId);
    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
    textView.setTypeface(isBold ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
    textView.setTextColor(color);
    textView.setPadding(padding, 0, padding, 0);
    LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, (int) (density * 56));
    layoutParams.weight = 1;
    addView(textView, layoutParams);
  }
}
