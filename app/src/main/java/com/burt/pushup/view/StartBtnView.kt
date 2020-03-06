package com.burt.pushup.view

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import com.burt.pushup.R


class StartBtnView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    /**
     * 默认圆角矩形半径
     */
    private val defaultRadius = 50F
    /**
     * 默认线条宽度
     * */
    private val defaultStrokeWidth = 5F
    /**
     * 按钮画笔
     * */
    var mPaint = Paint()

    /**
     * 进度圆环画笔
     * */
    var progressPaint = Paint()

    /**
     * 字体的画笔
     * */

    var textPaint = Paint()
    var textInCircle = "0"
    var progress = 0F  // 根据数值，算进度
    var totalNum = 1F // 总体的数量

    var totalPaint = Paint()

    var mHeight: Int = 0
    var mWidth: Int = 0
    var radius = defaultRadius
    private var roundRect = RectF()
    lateinit var anim: ValueAnimator

    /**
     * 中心点
     * */
    var centerX = 0F
    var centerY = 0F

    /**
     * 文字绘制点
     * */
    var baseLineY = 0F
    var bottom = 0F
    var top = 0F

    /**
     * 状态
     * */
    private var running = false


    private val padding = 20F

    private val mContext = context

    /**
     * 监听器
     * */
    lateinit var listener: StartBtnListener

    /**
     * 文字
     */
    var text = "开始"

    init {


        initPaint()  // 初始化paint

        val typedArray: TypedArray =context.obtainStyledAttributes(attrs,
            R.styleable.StartBtnView)

        text = typedArray.getString(R.styleable.StartBtnView_text)?:"开始"

        typedArray.recycle();

    }


    private fun initPaint() {
        mPaint.isAntiAlias = true
        mPaint.style = Paint.Style.STROKE
        mPaint.color = mContext.resources.getColor(R.color.colorPrimaryDark)
        mPaint.strokeWidth = defaultStrokeWidth


        progressPaint.isAntiAlias = true
        progressPaint.style = Paint.Style.STROKE
        progressPaint.color = mContext.resources.getColor(R.color.colorAccent)
        progressPaint.strokeWidth = defaultStrokeWidth * 1.5f


        textPaint.isAntiAlias = true
        textPaint.style = Paint.Style.FILL
        textPaint.textSize = 70F
        textPaint.color = mContext.resources.getColor(R.color.colorPrimary)
        textPaint.textAlign = Paint.Align.CENTER  // 设置基线的绘制点

        bottom = textPaint.fontMetrics.bottom
        top = textPaint.fontMetrics.top


        totalPaint.set(textPaint)
        totalPaint.textSize = 35F

    }

    private fun initAnim() {


        anim = ValueAnimator.ofFloat(defaultRadius, mHeight / 2f)

        anim.duration = 1500

        anim.interpolator = AccelerateDecelerateInterpolator()

        anim.addUpdateListener { animation ->
            radius = animation?.animatedValue as Float
            invalidate()
        }
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mHeight = h
        mWidth = w
        roundRect.left = padding
        roundRect.top = padding
        roundRect.right = mWidth.toFloat() - padding
        roundRect.bottom = mHeight.toFloat() - padding

        centerX = mWidth / 2f
        centerY = mHeight / 2f


        baseLineY = centerY - top / 2f - bottom / 2f


        initAnim()

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawRoundRect(roundRect, radius, radius, mPaint)

        if (running) {

            canvas?.drawArc(roundRect, -90F, 360 * progress, false, progressPaint)

            canvas?.drawText(textInCircle, centerX, baseLineY, textPaint)

            val width =getTextWidth(textInCircle)  //获取字的宽度
            Log.d("xu","加厚的{$width}")
            canvas?.drawText("/ ${totalNum.toInt()}", centerX + width, baseLineY, totalPaint)

        } else {

            canvas?.drawText(text, centerX, baseLineY, textPaint)

        }

    }

    private fun getTextWidth(textInCircle: String): Int {
        //2. 计算文字所在矩形，可以得到宽高
        val rect = Rect()
        textPaint.getTextBounds(textInCircle, 0, textInCircle.length, rect)
        val w = rect.width()
        Log.d("xu", "宽度是：{$w}")

        return (w + 15)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        when (event?.action) {

            MotionEvent.ACTION_DOWN -> {

                if (!running) {

                    listener.onStart()
                    running = true

                    Log.d("xu", "ceshi")
                    anim.start()

                }
            }
        }
        return true
    }


    fun reset() {


        /**
         * 逆序执行动画
         * */
        val anim = ValueAnimator.ofFloat(mHeight / 2f, defaultRadius)

        anim.duration = 1500

        anim.interpolator = AccelerateDecelerateInterpolator()

        anim.addUpdateListener { animation ->
            radius = animation?.animatedValue as Float
            invalidate()
        }


        anim.start()

        textInCircle = "0"

        progress = 0F

        running = false
    }


    fun updateTextInCircle(now: Int, total: Float) {

        progress = (now / total)

        totalNum = total

        textInCircle = now.toString()

        invalidate()

        if (progress == 1F) {

            listener.onStop()
        }
    }


    interface StartBtnListener {
        fun onStart()
        fun onStop()
    }

    fun setOnStartBtnListener(listener: StartBtnListener) {
        this.listener = listener
    }

}