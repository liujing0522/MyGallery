package com.example.liujing.mygallery;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Transformation;
import android.widget.Gallery;
import android.widget.LinearLayout;

public class CustomGallery extends Gallery
{
	private Camera mCamera = new Camera();
	private int mMaxRotationAngle = 0;
	private int mMaxZoom = -200;
	private int mCoveflowCenter;
	private boolean mAlphaMode = true;
	private boolean mCircleMode = false;
	private int galleryWidth = 0;
	private int mItemCount = 0;
	private int mItemWidth = 0;
	private int mItemBgWidth = 0;
	private int mItemBgHeight = 0;
	private int mGalleryHeight = 0;
	private int mHighLightItemWidth = 0;
	private int mHighLightItemBgWidth = 0;

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
	{
		// TODO Auto-generated method stub
		return super.onScroll(e1, e2, distanceX, distanceY);
	}

	public CustomGallery(Context context)
	{
		super(context);
		this.setStaticTransformationsEnabled(true);//设置为true,getChildStaticTransformation()方法才有用到
	}

	public CustomGallery(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		this.setStaticTransformationsEnabled(true);
		int screenWidth = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
		TypedArray mTypedArray = context.obtainStyledAttributes(attrs,R.styleable.gallery);
		int Margin = mTypedArray.getDimensionPixelOffset(R.styleable.gallery_galleryMargin,0);
		galleryWidth = screenWidth - 2 * Margin;
		mItemCount = mTypedArray.getInt(R.styleable.gallery_galleryItems,7);
		mItemWidth = galleryWidth / mItemCount;
		mGalleryHeight = mTypedArray.getDimensionPixelOffset(R.styleable.gallery_galleryHeight,150);
		mItemBgHeight = mTypedArray.getDimensionPixelSize(R.styleable.gallery_galleryItemBgHeight, ViewGroup.LayoutParams.WRAP_CONTENT);
		mItemBgWidth = mTypedArray.getDimensionPixelSize(R.styleable.gallery_galleryItemBgWidth,70);
	}

	public CustomGallery(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		this.setStaticTransformationsEnabled(true);
	}

	public void setHighLightItemExtraWidth(int width)
	{
		 mHighLightItemWidth = width;
	}

	public int getHighLightItemExtraWidth()
	{
		 return mHighLightItemWidth;
	}
	public void setHighLightItemBgExtraWidth(int width)
	{
		mHighLightItemBgWidth = width;
	}

	public int getHighLightItemBgExtraWidth()
	{
		return mHighLightItemBgWidth;
	}
	public int getItemWidth()
	{
		return mItemWidth;
	}

	public int getGalleryHeight()
	{
		return mGalleryHeight;
	}

	public int getGalleryWidth()
	{
		return galleryWidth;
	}

	public int getGalleryItemBgWidth()
	{
		return mItemBgWidth;
	}

	public int getGalleryItemBgHeight()
	{
		return mItemBgHeight;
	}
	public int getMaxRotationAngle()
	{
		return mMaxRotationAngle;
	}

	public void setMaxRotationAngle(int maxRotationAngle)
	{
		mMaxRotationAngle = maxRotationAngle;
	}

	public boolean getCircleMode()
	{
		return mCircleMode;
	}

	public void setCircleMode(boolean isCircle)
	{
		mCircleMode = isCircle;
	}

	public boolean getAlphaMode()
	{
		return mAlphaMode;
	}

	public void setAlphaMode(boolean isAlpha)
	{
		mAlphaMode = isAlpha;
	}

	public int getMaxZoom()
	{
		return mMaxZoom;
	}

	public void setMaxZoom(int maxZoom)
	{
		mMaxZoom = maxZoom;
	}

	private int getCenterOfCoverflow()  //获得屏幕中点
	{
		return (getWidth() - getPaddingLeft() - getPaddingRight()) / 2 + getPaddingLeft();
	}

	private static int getCenterOfView(View view) //获得view的中点
	{
		return view.getLeft() + view.getWidth() / 2;
	}

	@Override
	protected boolean getChildStaticTransformation(View child, Transformation t)
	{
		final int childCenter = getCenterOfView(child);
		final int childWidth = child.getWidth();
		int rotationAngle = 0;
		t.clear();
		t.setTransformationType(Transformation.TYPE_MATRIX);
		if (childCenter == mCoveflowCenter)
		{
			transformImageBitmap((LinearLayout) child, t, 0);
		}
		else
		{
			rotationAngle = (int) (((float) (mCoveflowCenter - childCenter) / childWidth) * mMaxRotationAngle);
			if (Math.abs(rotationAngle) > mMaxRotationAngle)
			{
				rotationAngle = (rotationAngle < 0) ? -mMaxRotationAngle : mMaxRotationAngle;
			}
			transformImageBitmap((LinearLayout) child, t, rotationAngle);//这个rotationAngle 其实可以设置child相对y轴的旋转角度，本例并不用旋转
		}
		return true;
	}

	/**
	 * @param w
	 *            Current width of this view.
	 * @param h
	 *            Current height of this view.
	 * @param oldw
	 *            Old width of this view.
	 * @param oldh
	 *            Old height of this view.
	 */
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		mCoveflowCenter = getCenterOfCoverflow();
		super.onSizeChanged(w, h, oldw, oldh);
	}

	/**
	 * The bitmap image rotary around by the y-axis
	 *
	 *            ImageView the ImageView whose bitmap we want to rotate
	 * @param t
	 *            transformation
	 * @param rotationAngle
	 *            the Angle by which to rotate the Bitmap
	 */
	private void transformImageBitmap(LinearLayout child, Transformation t, int rotationAngle)
	{
		mCamera.save();
		final Matrix imageMatrix = t.getMatrix();
		final int imageHeight = child.getLayoutParams().height;
		final int imageWidth = child.getLayoutParams().width;
		final int rotation = Math.abs(rotationAngle);
		mCamera.translate(0.0f, 0.0f, 100.0f);

		if (rotation <= mMaxRotationAngle)
		{
			float zoomAmount = (float) (mMaxZoom + (rotation * 1.5));
			mCamera.translate(0.0f, 0.0f, zoomAmount);//zoomAmount 是相对z轴的位置，负数表示离你越近，y轴正数是向上，x轴正数向右
			if (mCircleMode)
			{
				if (rotation < 40)
					mCamera.translate(0.0f, 155, 0.0f);
				else
					mCamera.translate(0.0f, (255 - rotation * 2.5f), 0.0f);
			}
			if (mAlphaMode)
			{
				(child).setAlpha((int) (255 - rotation * 2.5));
			}
		}
		mCamera.rotateY(rotationAngle);
		mCamera.getMatrix(imageMatrix);
		imageMatrix.postTranslate((imageWidth / 2), (imageHeight / 2));
		imageMatrix.preTranslate(-(imageWidth / 2), -(imageHeight / 2));

		mCamera.restore();
	}
}
