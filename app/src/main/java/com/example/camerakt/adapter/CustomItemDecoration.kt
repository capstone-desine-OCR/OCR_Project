package com.example.camerakt.adapter

import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.camerakt.R

class CustomItemDecoration(private val verticalSpaceHeight: Int) : RecyclerView.ItemDecoration() {


    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        outRect.bottom = verticalSpaceHeight // 아래쪽 간격 추가
    }

    //viewHolder 그려지기전 호출 -> viewHolder 아래
    // recycler :  viewGroup -> 부모
    //viewHolder : 재사용되는 것 -> 자식
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        // drawable 의 line_divider을 가져와서 구분선 그림
        val divider = ContextCompat.getDrawable(parent.context, R.drawable.line_divider)

        // 자식 view 들의 수
        val childCount = parent.childCount

        // 모든 자식에 반복
        // 반복문 형태 신기함 -> list arry range 일때만 for in [ ] 형태
        // ~ 만큼의 반복 until
        for (i in 0 until childCount) {
            // 현재 단계의 자식 view 가져옴
            val child = parent.getChildAt(i)

            val params = child.layoutParams as RecyclerView.LayoutParams

            val top = child.bottom + params.bottomMargin
            val bottom = top + (divider?.intrinsicHeight ?: 0)

            divider?.setBounds(parent.paddingLeft, top, parent.width - parent.paddingRight, bottom)
            divider?.draw(c)
        }
    }

    // ViewHolder가 그려진 후에 호출된다. => ViewHolder 위에
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
    }
}