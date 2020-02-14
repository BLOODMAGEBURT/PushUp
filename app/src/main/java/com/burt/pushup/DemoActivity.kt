package com.burt.pushup

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import kotlinx.android.synthetic.main.activity_demo.*


class DemoActivity : AppCompatActivity() {

    lateinit var orientationUtils: OrientationUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)
        supportActionBar?.hide()

        init()
    }

    private fun init() {

//        val source = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4"
        val source = "https://mkdown-1256191338.cos.ap-beijing.myqcloud.com/pushup.flv"

        video_player.setUp(source, true, "测试测试")

        //增加封面
        val imageView = ImageView(this)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        imageView.setImageResource(R.mipmap.pant)

        video_player.thumbImageView = imageView
        //增加title
        video_player.titleTextView.visibility = View.VISIBLE
        //设置返回键
        video_player.backButton.visibility = View.VISIBLE
        //设置旋转
        orientationUtils = OrientationUtils(this, video_player)
        //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
        video_player.fullscreenButton.setOnClickListener { orientationUtils.resolveByClick() }
        //是否可以滑动调整
        video_player.setIsTouchWiget(true)
        //设置返回按键功能
        video_player.backButton.setOnClickListener { onBackPressed() }
        video_player.startPlayLogic()

    }


    override fun onPause() {
        super.onPause()
        video_player.onVideoPause()
    }

    override fun onResume() {
        super.onResume()
        video_player.onVideoResume();
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoManager.releaseAllVideos()
        orientationUtils.releaseListener()
    }

    override fun onBackPressed() {

        //先返回正常状态
        if (orientationUtils.getScreenType() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            video_player.getFullscreenButton().performClick();
            return
        }
        //释放所有
        video_player.setVideoAllCallBack(null)

        super.onBackPressed()

    }

}
