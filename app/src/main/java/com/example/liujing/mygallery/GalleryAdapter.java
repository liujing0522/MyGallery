package com.example.liujing.mygallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class GalleryAdapter extends BaseAdapter
{
	private Context mContext;
	private int mSelectItem = (Integer.MAX_VALUE/2+3);
	private String[] titles ;
	private int itemIconBg;
	private int[] itemIcon ;
	private CustomGallery mCustomGallery= null;
	private int mItemWidth = 0;
	private int mItemBgWidth = 0;
	private int mItemBgHeight = 0;
	private int mGalleryHeight = 0;
	private int mHighLightItemExtraWidth = 0;
	private int mHighLightItemBgExtraWidth = 0;

	public GalleryAdapter(Context context,CustomGallery gallery,int titlesSrcId,int itemIconBg,int[] itemIcon)
	{
		this.mContext = context;
		this.mCustomGallery = gallery;
		this.mItemWidth = mCustomGallery.getItemWidth();
		this.mItemBgWidth = mCustomGallery.getGalleryItemBgWidth();
		this.mItemBgHeight = mCustomGallery.getGalleryItemBgHeight();
		this.mGalleryHeight = mCustomGallery.getGalleryHeight();
		this.mHighLightItemExtraWidth = mCustomGallery.getHighLightItemExtraWidth();
		this.mHighLightItemBgExtraWidth = mCustomGallery.getHighLightItemBgExtraWidth();
		this.titles = context.getResources().getStringArray(titlesSrcId);
		this.itemIconBg = itemIconBg;
		this.itemIcon = itemIcon;
		/*int screenWidth = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
		if(titles.length >= 7){
			itemWidth = screenWidth/7;
		}else{
			itemWidth = screenWidth/titles.length;
		}*/
	}


	public int getCount()
	{
		return Integer.MAX_VALUE;
	}

	public Object getItem(int position)
	{
		return position;
	}

	public long getItemId(int position)
	{
		return position;
	}

	public void setSelectItem(int selectItem)
	{
		if (this.mSelectItem != selectItem)
		{
			this.mSelectItem = selectItem;
			notifyDataSetChanged();
		}
	}

	@SuppressWarnings("deprecation")
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder viewHolder = null;
		if (convertView == null)
		{
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.gallery_edit_channel_item, null);
			viewHolder.image = (PosterView) convertView.findViewById(R.id.imageViewFlag);
			convertView.setTag(viewHolder);

		}
		else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}

		if(mSelectItem == position)
		{
			viewHolder.image.setText(titles[position % itemIcon.length]);
			convertView.setLayoutParams(new CustomGallery.LayoutParams(mItemWidth+mHighLightItemExtraWidth, mGalleryHeight));
			viewHolder.image.setSelectedLayoutParams(mItemBgWidth+mHighLightItemBgExtraWidth,mItemBgHeight+mHighLightItemBgExtraWidth);
		}
		else
		{
			viewHolder.image.setText("");
			convertView.setLayoutParams(new CustomGallery.LayoutParams(mItemWidth, mGalleryHeight));
			viewHolder.image.setUnSelectLayoutParams(mItemBgWidth,mItemBgHeight);
		}

		viewHolder.image.setPosterBackground(itemIconBg);
		viewHolder.image.setImageDrawable(itemIcon[position % itemIcon.length]);

		return convertView;
	}

	private class ViewHolder
	{
		private PosterView image;
	}
}
