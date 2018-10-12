package xxl.com.json.view

import android.content.Context
import android.content.res.TypedArray
import androidx.core.content.ContextCompat
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.ViewFlipper
import xxl.com.json.R

/**
 * Created by xxl on 2018/5/2.
 * 文字垂直跑马灯
 */
class UPMarqueeView : ViewFlipper {
    var mInterval: Int = 3000
    //存放滚动字符串的集合

    var mList: List<CharSequence> = mutableListOf() //存放滚动字符串的集合 不可变
    var mNoticesMutableList: MutableList<CharSequence> = mutableListOf() //存放滚动字符串的集合 可变集合


    var mPosition = 0//文字当前位置

    var mInDuration: Int = 1000
    var mOutDuration: Int = 1000  //动画退出和进入时间 默认值
    var mTextColor : Int = ContextCompat.getColor(context,R.color.color_999) //文字默认颜色
    var mTextSize : Int = 12 //跑马灯默认字体大小
    var mGravity : Int = Gravity.LEFT //文字对齐方式 默认左对齐

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attributeSet: AttributeSet?) : super(context, attributeSet) {
        initAttribute(context, attributeSet)
    }

    private fun initAttribute(context: Context?, attributeSet: AttributeSet?) {
        var typeArray: TypedArray? = context?.obtainStyledAttributes(attributeSet, R.styleable.UPMarqueeView)

        mInterval = typeArray?.getInteger(R.styleable.UPMarqueeView_interval, mInterval)!!
        mInDuration = typeArray?.getInteger(R.styleable.UPMarqueeView_inDuration, mInDuration)
        mOutDuration = typeArray?.getInteger(R.styleable.UPMarqueeView_outDuration, mOutDuration)
        mTextColor = typeArray?.getColor(R.styleable.UPMarqueeView_upTextColor,mTextColor)

        mTextSize = typeArray?.getDimensionPixelSize(R.styleable.UPMarqueeView_upTextSize,mTextSize)

        mGravity = typeArray?.getInt(R.styleable.UPMarqueeView_upGravity,mGravity)
        when(mGravity) {
            0 -> {
                mGravity = Gravity.CENTER
            }
            -1 -> {
                mGravity = Gravity.START
            }
            1 -> {
                mGravity = Gravity.END
            }
        }
        typeArray?.recycle()
        setFlipInterval(mInterval)
    }

    /**
     * 设置数据
     */
    fun setNotices(notices: List<CharSequence>) {
        this.mList = notices
    }

    /**
     *开始滚动
     */
    fun start() {
        post {
            Runnable {
                kotlin.run {
                    postStart()
                }
            }.run()
        }

    }

    private fun postStart() {
        removeAllViews()
        clearAnimation()

        mNoticesMutableList.clear()
        for (char in mList) {
            mNoticesMutableList.add(char)
        }

        mPosition = 0

        addView(createTextView(mNoticesMutableList[mPosition]))

        if (mNoticesMutableList.size > 0) {
            setInAndOutAnimation()
            startFlipping()
        }else{
            return
        }

        inAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                mPosition++//一次动画结束 位置 ++ 创建下一个View继续执行动画
                if (mPosition >= mNoticesMutableList.size) {
                    mPosition = 0
                }
                var textView: TextView = createTextView(mNoticesMutableList[mPosition])
                if (textView.parent == null) {
                    addView(textView)
                }
            }

            override fun onAnimationStart(animation: Animation?) {
            }

        })
    }

    /**
     * 创建View
     */
    private fun createTextView(text: CharSequence): TextView {
        var textView: TextView
        var childView: View? = null
        childView = getChildAt((displayedChild + 1) % 3)
        if (childView != null) {
            textView = childView as TextView
        } else {
            textView = TextView(context)
            textView.setSingleLine(true)
            textView.gravity = mGravity
            textView.setTextColor(mTextColor)
            textView.ellipsize = TextUtils.TruncateAt.END
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,mTextSize.toFloat())
        }
        textView.text = text
        textView.setTag(mPosition)
        textView.setOnClickListener({
            mListener.invoke(getPosition(), textView)
        })
        return textView
    }

    /**
     * 当前显示的View的位置
     */
    fun getPosition(): Int {
        return currentView.getTag() as Int
    }

    lateinit var mListener: (position: Int, textView: TextView) -> Unit

    /**
     * 回调监听
     */
    fun setOnItemClickListener(listener: (position: Int, textView: TextView) -> Unit) {
        this.mListener = listener
    }

    /**
     * 进入退出动画设置
     */
    fun setInAndOutAnimation() {
        val inAnimation = AnimationUtils.loadAnimation(context, R.anim.anim_anim_bottom_in)
        val outAnimation = AnimationUtils.loadAnimation(context, R.anim.anim_anim_top_out)
        inAnimation.duration = mInDuration.toLong()
        outAnimation.duration = mOutDuration.toLong()
        setInAnimation(inAnimation)
        setOutAnimation(outAnimation)
    }

}