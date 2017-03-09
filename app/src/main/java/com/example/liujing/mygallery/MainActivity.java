package com.example.liujing.mygallery;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity implements onOtherKeyListener
{

    private CustomGallery galleryMenu = null;
    private GalleryAdapter galleryMenuAdapter = null;
    private RelativeLayout mainWin = null;
    private RecyclerView content = null;
    private int SELECT = Integer.MAX_VALUE/2+3;
    private int mGalleryWidth = 0;
    private int mGalleryHeight = 0;
    private int CONTENT_SELECT = 0;
    private boolean bFocusOnContent = false;
    ContentAdapter contentAdapter = null;
    private int itemIconBg =R.drawable.menu_background_selector;
    private int[] itemIcon = {R.drawable.icon_music, R.drawable.icon_movie, R.drawable.icon_game, R.drawable.icon_all,
            R.drawable.icon_pic, R.drawable.icon_software, R.drawable.icon_other};

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        int flags = WindowManager.LayoutParams.FLAG_FULLSCREEN;
//        getWindow().setFlags(flags,flags);
        setContentView(R.layout.activity_main);
        mainWin = (RelativeLayout) findViewById(R.id.main_win);
        content = (RecyclerView) findViewById(R.id.content);
        galleryMenu = (CustomGallery) findViewById(R.id.gallery_menu);
        mGalleryWidth = galleryMenu.getGalleryWidth();
        mGalleryHeight = galleryMenu.getGalleryHeight();
        ViewGroup.LayoutParams lp = galleryMenu.getLayoutParams();
        lp.width = mGalleryWidth;
        lp.height = mGalleryHeight;
        galleryMenu.setLayoutParams(lp);
        galleryMenu.setAnimationDuration(200);
        galleryMenu.setFocusable(false);
        galleryMenu.setFadingEdgeLength(0);
        galleryMenu.setSpacing(1);
        galleryMenu.setHighLightItemExtraWidth(50);
        galleryMenu.setHighLightItemBgExtraWidth(70);
        galleryMenuAdapter = new GalleryAdapter(this,galleryMenu,R.array.iconTitles,itemIconBg,itemIcon);
        galleryMenu.setAdapter(galleryMenuAdapter);
        galleryMenu.setOnItemSelectedListener(galleryItemSelectListener);
        galleryMenu.setSelection(SELECT);

        contentAdapter = new ContentAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        content.setLayoutManager(linearLayoutManager);
        content.addItemDecoration(new ItemSpaceDecoration(20));
        content.setAdapter(contentAdapter);
        content.setFocusable(true);

        /*contentAdapter.setOnItemSelectListener(new onItemSelectListener()
        {
            @Override
            public void onItemSelect(View view, int position)
            { Log.e("nimei","onItemSelect"+position);
                content.scrollToPosition(position);
            }
        });*/
        /*content.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState)
            {
                Log.e("nimei","000000000000000 onScrollStateChanged");
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                Log.e("nimei","00000000000000 onScrolled = "+content.isFocused());
                super.onScrolled(recyclerView, dx, dy);

            }
        });*/
        content.setOnKeyListener(recyclerViewOnKeyListener);
    }
    private AdapterView.OnItemSelectedListener galleryItemSelectListener = new AdapterView.OnItemSelectedListener()
    {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l)
        {
            galleryMenuAdapter.setSelectItem(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView)
        {

        }
    };
    private RecyclerView.OnKeyListener recyclerViewOnKeyListener = new RecyclerView.OnKeyListener()
    {

        @Override
        public boolean onKey(View view, int keyCode, KeyEvent keyEvent)
        {
            if(keyEvent.getAction() == KeyEvent.ACTION_DOWN)
            {
                switch (keyCode)
                {
                    case 67:
                        if(bFocusOnContent)
                        {
                            bFocusOnContent = false;
                            showGallery();
                            return true;
                        }

                    case KeyEvent.KEYCODE_DPAD_UP:
                    case KeyEvent.KEYCODE_DPAD_DOWN:
                        if(!bFocusOnContent)
                        {
                            bFocusOnContent = true;
                            hideGallery();
                            return true;
                        }
                        else
                        {
                            int Flag = 1;
                            if (keyCode == KeyEvent.KEYCODE_DPAD_UP)
                                Flag = -1;
                            CONTENT_SELECT += Flag;
                            if(CONTENT_SELECT < 0 )
                            {
                                bFocusOnContent = false;
                                CONTENT_SELECT = 0;
                                showGallery();
                                return true;
                            }
                            if(CONTENT_SELECT >= contentAdapter.getItemCount()-1)
                            {
                                CONTENT_SELECT = 0;
                            }
                            content.scrollToPosition(CONTENT_SELECT);
                            contentAdapter.setSelect(CONTENT_SELECT);
                            contentAdapter.notifyDataSetChanged();
                        }
                        //content.onKeyDown(i,keyEvent);
                        return true;
                    case KeyEvent.KEYCODE_DPAD_LEFT:
                    case KeyEvent.KEYCODE_DPAD_RIGHT:
                        galleryMenu.onKeyDown(keyCode,keyEvent);
                        return true;
                }
            }
            return false;
        }
    };
    private void hideGallery()
    {
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator animTop = ObjectAnimator.ofFloat(mainWin,"translationY",0,-100);
        ObjectAnimator animAlpha = ObjectAnimator.ofFloat(galleryMenu,"alpha",1.0f,0.0f);
        set.playTogether(animTop,animAlpha);
        set.setDuration(500);
        set.start();
    }

    private void showGallery()
    {
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator animTop = ObjectAnimator.ofFloat(mainWin,"translationY",-100,0);
        ObjectAnimator animAlpha = ObjectAnimator.ofFloat(galleryMenu,"alpha",0.0f,1.0f);
        set.playTogether(animTop,animAlpha);
        set.setDuration(500);
        set.start();
    }
    @Override
    public boolean onKeyDown(int keycode, KeyEvent event)
    {
        /*if(event.getAction() == KeyEvent.ACTION_DOWN)
        {
            int flag = 1;
            if(keycode == KeyEvent.KEYCODE_DPAD_RIGHT)
                flag = -1;
            SELECT = SELECT+flag;
            switch(keycode)
            {
                case KeyEvent.KEYCODE_DPAD_LEFT:
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    Log.e("nimei","case +++++++++ KeyEvent.KEYCODE_DPAD_RIGHT:");
                    galleryMenuAdapter.setSelectItem(SELECT);
                    return true;
                case KeyEvent.KEYCODE_DPAD_DOWN:
                    AnimatorSet setKeyDown = new AnimatorSet();
                    ObjectAnimator animTopKeyDown = ObjectAnimator.ofFloat(mainWin,"translationY",-100,0);
                    ObjectAnimator animAlphaKeyDown = ObjectAnimator.ofFloat(galleryMenu,"alpha",0.0f,1.0f);
                    setKeyDown.playTogether(animTopKeyDown,animAlphaKeyDown);
                    setKeyDown.setDuration(500);
                    setKeyDown.start();
                    return true;
                case KeyEvent.KEYCODE_DPAD_UP:
                    AnimatorSet setKeyUp = new AnimatorSet();
                    ObjectAnimator animTopKeyUp = ObjectAnimator.ofFloat(mainWin,"translationY",0,-100);
                    ObjectAnimator animAlphaKeyUp = ObjectAnimator.ofFloat(galleryMenu,"alpha",1.0f,0.0f);
                    setKeyUp.playTogether(animTopKeyUp,animAlphaKeyUp);
                    setKeyUp.setDuration(500);
                    setKeyUp.start();

                    return true;
                case KeyEvent.KEYCODE_DPAD_CENTER:
                    return true;
            }
        }*/
        return super.onKeyDown(keycode, event);
    }
}