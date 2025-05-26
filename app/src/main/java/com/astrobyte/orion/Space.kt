package com.astrobyte.orion
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class Space(private val space: Int, private val spanCount: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        if (parent.getChildLayoutPosition(view) < spanCount) {
            outRect.top = space
        }
        outRect.right = space
        outRect.left = space
        outRect.bottom = space
    }
}
