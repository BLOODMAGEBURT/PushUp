package com.burt.pushup.base

import android.app.Activity
import android.app.Application
import me.jessyan.autosize.AutoSizeConfig
import me.jessyan.autosize.onAdaptListener
import me.jessyan.autosize.unit.Subunits
import java.util.*

class MyApplication: Application (){
    override fun onCreate() {
        super.onCreate()
        /**
         * 以下是 AndroidAutoSize 可以自定义的参数, {@link AutoSizeConfig} 的每个方法的注释都写的很详细
         * 使用前请一定记得跳进源码，查看方法的注释, 下面的注释只是简单描述!!!
         */
        /**
         * 以下是 AndroidAutoSize 可以自定义的参数, [AutoSizeConfig] 的每个方法的注释都写的很详细
         * 使用前请一定记得跳进源码，查看方法的注释, 下面的注释只是简单描述!!!
         */
        AutoSizeConfig.getInstance() //是否让框架支持自定义 Fragment 的适配参数, 由于这个需求是比较少见的, 所以须要使用者手动开启
            //如果没有这个需求建议不开启
            //                .setCustomFragment(true)
            //是否屏蔽系统字体大小对 AndroidAutoSize 的影响, 如果为 true, App 内的字体的大小将不会跟随系统设置中字体大小的改变
            //如果为 false, 则会跟随系统设置中字体大小的改变, 默认为 false
            //                .setExcludeFontScale(true)
            //屏幕适配监听器
            .setOnAdaptListener(object : onAdaptListener {
                override fun onAdaptBefore(
                    target: Any,
                    activity: Activity
                ) { //使用以下代码, 可支持 Android 的分屏或缩放模式, 但前提是在分屏或缩放模式下当用户改变您 App 的窗口大小时
                    //系统会重绘当前的页面, 经测试在某些机型, 某些情况下系统不会重绘当前页面, ScreenUtils.getScreenSize(activity) 的参数一定要不要传 Application!!!
                    //                        AutoSizeConfig.getInstance().setScreenWidth(ScreenUtils.getScreenSize(activity)[0]);
                    //                        AutoSizeConfig.getInstance().setScreenHeight(ScreenUtils.getScreenSize(activity)[1]);
//                    me.jessyan.autosize.utils.LogUtils.d(
//                        String.format(
//                            Locale.ENGLISH,
//                            "%s onAdaptBefore!",
//                            target.javaClass.name
//                        )
//                    )
                }

                override fun onAdaptAfter(target: Any, activity: Activity) {
//                    me.jessyan.autosize.utils.LogUtils.d(
//                        String.format(
//                            Locale.ENGLISH,
//                            "%s onAdaptAfter!",
//                            target.javaClass.name
//                        )
//                    )
                }
            }).unitsManager
            .setSupportDP(false)
            .setSupportSP(false).supportSubunits = Subunits.MM
    }
}