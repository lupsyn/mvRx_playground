package com.playground.features.views

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.playground.R

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_MATCH_HEIGHT)
class FullScreenMessageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val titleView by lazy { findViewById<TextView>(R.id.title) }
    private val retryView by lazy { findViewById<Button>(R.id.retry) }

    init {
        inflate(context, R.layout.full_screen_message, this)
    }

    @TextProp
    fun setTitle(title: CharSequence) {
        titleView.text = title
    }

    @CallbackProp
    fun onClickListener(listener: OnClickListener?) {
        retryView.setOnClickListener(listener)
    }
}