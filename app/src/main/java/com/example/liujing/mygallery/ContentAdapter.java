package com.example.liujing.mygallery;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by liujing on 17-1-9.
 */

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.MyViewHolder>
{
    private Context mContext = null;
    private int mPostion = 0;
    public ContentAdapter(Context context)
    {
        mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.content_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    public void setSelect(int position)
    {
        mPostion = position;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        if(mPostion == position )
        {
            holder.itemBackground.setBackgroundResource(R.drawable.list_bar_sel);
        }
        else
            holder.itemBackground.setBackgroundResource(R.drawable.list_bar);
     }

    @Override
    public int getItemCount()
    {
        return 20;
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        RelativeLayout itemBackground;

        public MyViewHolder(View view)
        {
            super(view);
            itemBackground = (RelativeLayout) view.findViewById(R.id.item_content);
        }
    }
}
