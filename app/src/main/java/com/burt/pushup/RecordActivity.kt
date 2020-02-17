package com.burt.pushup

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarView
import kotlinx.android.synthetic.main.activity_record.*


/**
 * 记录界面
 * xubobo
 * 2020-02-15
 * */
class RecordActivity : CalendarView.OnCalendarSelectListener,
    CalendarView.OnYearChangeListener,
    View.OnClickListener,
    AppCompatActivity() {

    private var mYear = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)
        supportActionBar?.hide()
        init()
        initData()
    }


    private fun init() {

        this.tv_year.setText(this.calendarView.curYear.toString())
        tv_month_day.setText("${calendarView.curMonth}月${calendarView.curDay}日")
        tv_lunar.setText("今日")
        tv_current_day.setText("${calendarView.curDay}")

        mYear = calendarView.curYear

        tv_month_day.setOnClickListener(this)
        fl_current.setOnClickListener(this)
        calendarView.setOnCalendarSelectListener(this)
        calendarView.setOnYearChangeListener(this)


    }

    private fun initData() {
        val year = calendarView.curYear
        val month = calendarView.curMonth

        val map: HashMap<String, Calendar> = HashMap()


        map[getSchemeCalendar(year, month, 3, resources.getColor(R.color.a), "20").toString()] =
            getSchemeCalendar(year, month, 3, resources.getColor(R.color.a), "20")

        map.put(
            getSchemeCalendar(year, month, 6, resources.getColor(R.color.b), "33").toString(),
            getSchemeCalendar(year, month, 6, resources.getColor(R.color.b), "33")
        )
        map.put(
            getSchemeCalendar(year, month, 9, resources.getColor(R.color.c), "25").toString(),
            getSchemeCalendar(year, month, 9, resources.getColor(R.color.c), "25")
        )
        map.put(
            getSchemeCalendar(year, month, 13, resources.getColor(R.color.d), "50").toString(),
            getSchemeCalendar(year, month, 13, resources.getColor(R.color.d), "50")
        )
        map.put(
            getSchemeCalendar(year, month, 14, resources.getColor(R.color.e), "80").toString(),
            getSchemeCalendar(year, month, 14, resources.getColor(R.color.e), "80")
        )
        map.put(
            getSchemeCalendar(year, month, 15, resources.getColor(R.color.f), "20").toString(),
            getSchemeCalendar(year, month, 15, resources.getColor(R.color.f), "20")
        )
        map.put(
            getSchemeCalendar(year, month, 18, resources.getColor(R.color.g), "70").toString(),
            getSchemeCalendar(year, month, 18, resources.getColor(R.color.g), "70")
        )
        map.put(
            getSchemeCalendar(year, month, 19, resources.getColor(R.color.b), "35").toString(),
            getSchemeCalendar(year, month, 19, resources.getColor(R.color.b), "35")
        )
        map.put(
            getSchemeCalendar(year, month, 25, resources.getColor(R.color.h), "36").toString(),
            getSchemeCalendar(year, month, 25, resources.getColor(R.color.h), "36")
        )
        map.put(
            getSchemeCalendar(year, month, 27, resources.getColor(R.color.i), "95").toString(),
            getSchemeCalendar(year, month, 27, resources.getColor(R.color.i), "95")
        )


        //此方法在巨大的数据量上不影响遍历性能，推荐使用
        calendarView.setSchemeDate(map)

    }

    private fun getSchemeCalendar(
        year: Int,
        month: Int,
        day: Int,
        color: Int,
        text: String
    ): Calendar {
        val calendar = Calendar()
        calendar.year = year
        calendar.month = month
        calendar.day = day
        calendar.schemeColor = color //如果单独标记颜色、则会使用这个颜色
        calendar.scheme = text
        return calendar
    }


    override fun onCalendarSelect(calendar: Calendar?, isClick: Boolean) {

        tv_lunar.visibility = View.VISIBLE
        tv_year.visibility = View.VISIBLE

        tv_month_day.setText("${calendar!!.month}月${calendar.day}日")
        tv_year.setText(calendar!!.year.toString())
        tv_lunar.setText(calendar.lunar.toString())

        mYear = calendar.year
    }

    override fun onCalendarOutOfRange(calendar: Calendar?) {


    }

    override fun onYearChange(year: Int) {
        tv_month_day.setText(year.toString())
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_month_day -> {
                if (!calendarLayout.isExpand) {
                    calendarLayout.expand()
                    return
                }
                calendarView.showYearSelectLayout(mYear)
                tv_year.visibility = View.GONE
                tv_lunar.visibility = View.GONE
                tv_month_day.setText(mYear.toString())
            }
            R.id.fl_current -> {
                calendarView.scrollToCurrent()
            }
        }
    }
}
