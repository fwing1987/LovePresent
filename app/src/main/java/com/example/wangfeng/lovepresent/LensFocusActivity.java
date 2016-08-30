package com.example.wangfeng.lovepresent;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class LensFocusActivity extends BaseActivity implements View.OnClickListener {


	private ImageView imageView_pic;
	private TextView textView_desc ;
    private TextView textView_desc_bottom ;

	/**
	 * 三个切换的动画
	 */
	private Animation mFadeIn;
	private Animation mFadeInScale;
	private Animation mFadeOut;

	/**
	 * 三个图片
	 */
	private Drawable mPicture_1;
	private Drawable mPicture_2;
	private Drawable mPicture_3;

    private int mStatus;


	@Override
	public void setView() {
		setContentView(R.layout.activity_splash_lens_focus);
	}

	@Override
	public void initView() {
		imageView_pic = (ImageView) findViewById(R.id.imageView_pic);
		textView_desc = (TextView) findViewById(R.id.textView_desc);
        textView_desc_bottom = (TextView) findViewById(R.id.textView_desc_bottom);

        imageView_pic.setOnClickListener(this);

		Intent intent = getIntent();
		if(intent == null) return;
		mStatus = intent.getIntExtra("status",0);
		init();
	}

	private void init() {
		initAnim();
		initPicture();
		/**
		 * 界面刚开始显示的内容
		 */
		imageView_pic.setImageDrawable(mPicture_1);
        if(mStatus == MainActivity.STATUS.START) {
            textView_desc.setText("从这里开始...");
        }else{
            textView_desc_bottom.setText("西湖之心已经结束...");
        }
		imageView_pic.startAnimation(mFadeIn);
	}



	/**
	 * 初始化动画
	 */
	private void initAnim() {
		mFadeIn = AnimationUtils.loadAnimation(this, R.anim.welcome_fade_in);
		mFadeIn.setDuration(1000);
		mFadeInScale = AnimationUtils.loadAnimation(this, R.anim.welcome_fade_in_scale);
		mFadeInScale.setDuration(6000);
		mFadeOut = AnimationUtils.loadAnimation(this, R.anim.welcome_fade_out);
		mFadeOut.setDuration(1000);
	}

	/**
	 * 初始化图片
	 */

	private void initPicture() {
        if(mStatus == MainActivity.STATUS.START) {
            mPicture_1 = getResources().getDrawable(R.drawable.main_1);
            mPicture_2 = getResources().getDrawable(R.drawable.main_2);
            mPicture_3 = getResources().getDrawable(R.drawable.main_3);
        }else{
            mPicture_1 = getResources().getDrawable(R.drawable.main_4);
            mPicture_2 = getResources().getDrawable(R.drawable.main_5);
            mPicture_3 = getResources().getDrawable(R.drawable.main_6);
        }
	}


	/**
	 * 监听事件
	 */
	public void setListener() {
		/**
		 * 动画切换原理:开始时是用第一个渐现动画,当第一个动画结束时开始第二个放大动画,当第二个动画结束时调用第三个渐隐动画,
		 * 第三个动画结束时修改显示的内容并且重新调用第一个动画,从而达到循环效果
		 */
		mFadeIn.setAnimationListener(new AnimationListener() {

			public void onAnimationStart(Animation animation) {

			}

			public void onAnimationRepeat(Animation animation) {

			}

			public void onAnimationEnd(Animation animation) {
				imageView_pic.startAnimation(mFadeInScale);
			}
		});
		mFadeInScale.setAnimationListener(new AnimationListener() {

			public void onAnimationStart(Animation animation) {

			}

			public void onAnimationRepeat(Animation animation) {

			}

			public void onAnimationEnd(Animation animation) {
				imageView_pic.startAnimation(mFadeOut);
			}
		});
		mFadeOut.setAnimationListener(new AnimationListener() {

			public void onAnimationStart(Animation animation) {

			}

			public void onAnimationRepeat(Animation animation) {

			}

			public void onAnimationEnd(Animation animation) {
				/**
				 * 这里其实有些写的不好,还可以采用更多的方式来判断当前显示的是第几个,从而修改数据,
				 * 我这里只是简单的采用获取当前显示的图片来进行判断。
				 */
				if (imageView_pic.getDrawable().equals(mPicture_1)) {
                    if(mStatus == MainActivity.STATUS.START) {
                        textView_desc.setText("在这里相知...");
                    }else{
                        textView_desc_bottom.setText("属于我们的路才刚刚开始...");
                    }
                    imageView_pic.setImageDrawable(mPicture_2);
				} else if (imageView_pic.getDrawable().equals(mPicture_2)) {
                    if(mStatus == MainActivity.STATUS.START) {
                        textView_desc.setText("今天，我要把西湖送给你...");
                    }else{
                        textView_desc_bottom.setText("所以，我想对你说...");
                    }
                    imageView_pic.setImageDrawable(mPicture_3);
				} else if (imageView_pic.getDrawable().equals(mPicture_3)) {
					finish();
				}
				imageView_pic.startAnimation(mFadeIn);
			}
		});

	}

    @Override
    public void onClick(View v) {

    }
}
