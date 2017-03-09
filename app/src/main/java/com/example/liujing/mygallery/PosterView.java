package com.example.liujing.mygallery;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PosterView extends FrameLayout
{
	private ImageView mPosterIconImageV;
	private TextView mPosterTextV;
	private RelativeLayout mPosterBackground;
	private ImageView mPosterLine;

	public PosterView(Context context)
	{
		this(context, null);
	}

	public PosterView(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	public PosterView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		initView();
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.poster);
		if (mPosterTextV != null)
		{
			String text = a.getString(R.styleable.poster_text);
			if (text != null)
				mPosterTextV.setText(text);
			int color = a.getColor(R.styleable.poster_textColor, Color.BLACK);
			mPosterTextV.setTextColor(color);
			float size = a.getDimensionPixelOffset(R.styleable.poster_textSize, 24);
			mPosterTextV.setTextSize(size);
		}
		
		if (mPosterIconImageV != null)
		{
			/*Drawable d = a.getDrawable(R.styleable.poster_icon);
			if(d != null)
				mPosterIconImageV.setImageDrawable(d);*/
			int width = a.getDimensionPixelOffset(R.styleable.poster_iconW, 50);
			int height = a.getDimensionPixelOffset(R.styleable.poster_iconH, 50);
			ViewGroup.LayoutParams lp = mPosterIconImageV.getLayoutParams();
			lp.width = width;
			lp.height = height;
			mPosterIconImageV.setLayoutParams(lp);
		}
		
		if(mPosterBackground != null)
		{
			Drawable d = a.getDrawable(R.styleable.poster_background);
			if(d != null)
				mPosterBackground.setBackgroundDrawable(d);
		}
		if(mPosterLine != null)
		{
			Drawable d = a.getDrawable(R.styleable.poster_lineSrc);
			if(d != null)
				mPosterLine.setImageDrawable(d);
			ViewGroup.LayoutParams lp = mPosterLine.getLayoutParams();
			int width = a.getDimensionPixelOffset(R.styleable.poster_lineWidth, 20);
			lp.width = width;
			mPosterLine.setLayoutParams(lp);
		}

		a.recycle();
	}

	private void initView()
	{
		LayoutInflater.from(getContext()).inflate(R.layout.poster_view_layout, this, true);
		mPosterBackground = (RelativeLayout)findViewById(R.id.poster);
		mPosterIconImageV = (ImageView) findViewById(R.id.imageView1);
		mPosterTextV = (TextView) findViewById(R.id.textView1);
		mPosterLine = (ImageView) findViewById(R.id.poster_line);
	}

	public void setUnSelectLayoutParams(int width,int height)
	{
		ViewGroup.LayoutParams lp = mPosterBackground.getLayoutParams();
		lp.width = width;
		lp.height = height;
		mPosterBackground.setLayoutParams(lp);
	}

	public void setSelectedLayoutParams(int width,int height)
	{
		ViewGroup.LayoutParams lp = mPosterBackground.getLayoutParams();
		lp.width = width;
		lp.height = height;
		mPosterBackground.setLayoutParams(lp);
	}

	public void setImageDrawable(Drawable d)
	{
		if(mPosterIconImageV != null)
		{
			mPosterIconImageV.setImageDrawable(d);
		}
	}
	
	public void setImageDrawable(int resId)
	{
		if(mPosterIconImageV != null)
		{
			mPosterIconImageV.setImageResource(resId);
		}
	}

	public void setText(CharSequence text)
	{
		if(mPosterTextV != null)
		{
			mPosterTextV.setText(text);
		}
	}
	
	public void setText(int resId)
	{
		if(mPosterTextV != null)
		{
			mPosterTextV.setText(resId);
		}
	}
	
	public ImageView getIconImageView()
	{
		return mPosterIconImageV;
	}
	
	public TextView getTextView()
	{
		return mPosterTextV;
	}
	
	public void setTextVisibility(int visibility)
	{
		if(mPosterTextV != null)
		{
			mPosterTextV.setVisibility(visibility);
		}
	}
	
	public void setIconVisibility(int visibility)
	{
		if(mPosterIconImageV != null)
		{
			mPosterIconImageV.setVisibility(visibility);
		}
	}

	public void setPosterBackground(Drawable d)
	{
		if(mPosterBackground != null)
		{
			mPosterBackground.setBackgroundDrawable(d);
		}
	}
	
	public void setPosterBackground(int resId)
	{
		if(mPosterBackground != null)
		{
			mPosterBackground.setBackgroundResource(resId);
		}
	}
}
