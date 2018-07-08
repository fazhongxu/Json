package xxl.com.json.ui

import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_marquee_test.*
import xxl.com.json.R
import xxl.com.json.ui.base.BaseActivity

class MarqueeTestActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marquee_test)
        initView()
    }


    private fun initView() {
        //不用findViewByID  直接 拿到ID 使用即可 不用加 R.id.xxx  直接用ID
        //val mMarqueeView = findViewById(R.id.activity_marquee_test_upmv) as UPMarqueeView

        val mMarqueeView = activity_marquee_test_upmv

        val list: MutableList<String> = mutableListOf("2018","Spring","SpringMVC","My Batis","MySql","C","Html","Learning makes me happy")

        mMarqueeView.setNotices(list)

        mMarqueeView.start()

        mMarqueeView.setOnItemClickListener { position, textView -> Unit
           Toast.makeText(this, "position:"+position.toString()+ ",text:" + textView.text.toString(), Toast.LENGTH_SHORT).show()
        }
    }

}
