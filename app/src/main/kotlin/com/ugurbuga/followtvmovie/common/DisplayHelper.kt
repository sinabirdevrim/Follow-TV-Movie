package com.ugurbuga.followtvmovie.common

import android.content.Context
import androidx.annotation.DimenRes

class DisplayHelper {
    companion object {
        fun getHeightFromRatio(
            context: Context?,
            @DimenRes paddingDp: Int?,
            ratio: Double = 1.0,
            spanCount: Int = 1
        ): Double? {

            val screenWidth = context?.resources?.displayMetrics?.widthPixels

            val screenPaddings = paddingDp?.let { context?.resources?.getDimensionPixelSize(it) }

            val width =
                screenPaddings?.let { screenWidth?.minus(it)?.div((spanCount)) } ?: screenWidth

            return width?.times(ratio)
        }
    }
}
