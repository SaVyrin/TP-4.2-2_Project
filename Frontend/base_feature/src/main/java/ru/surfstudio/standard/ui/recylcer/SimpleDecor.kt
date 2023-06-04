package ru.surfstudio.standard.ui.recylcer

import android.graphics.Rect
import android.view.View
import androidx.annotation.Px
import androidx.recyclerview.widget.RecyclerView
import ru.surfstudio.android.recycler.decorator.Decorator

class SimpleDecor(
    @Px private val left: Int = 0,
    @Px private val top: Int = 0,
    @Px private val right: Int = 0,
    @Px private val bottom: Int = 0
) : Decorator.OffsetDecor {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        recyclerView: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.set(left, top, right, bottom)
    }
}
