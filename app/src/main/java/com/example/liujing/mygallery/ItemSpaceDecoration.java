package com.example.liujing.mygallery;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by liujing on 17-1-10.
 */

public class ItemSpaceDecoration extends RecyclerView.ItemDecoration
{
    private int mSpace = 0;
    public ItemSpaceDecoration(int space)
    {
        this.mSpace = space;
    }
    @Override
    public void getItemOffsets(Rect outRect, View view,RecyclerView recyclerView,RecyclerView.State state )
    {
        if(recyclerView.getChildPosition(view) != 0)
        {
            outRect.top = mSpace;
        }
    }

}
