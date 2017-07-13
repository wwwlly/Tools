package com.kemp.tools

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.kemp.tools.utils.TimeUtils
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by wangkp on 2017/7/10.
 */
class MainActivity : AppCompatActivity() {

    val currentStr = "当前日期是第%d周"
    val selectStr = "%s是第%d周"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    fun init() {
        setDateToWeeksBlock()
        setWeeksToDateBlock()
    }

    fun setDateToWeeksBlock() {
        val btnSelect: Button = findViewById(R.id.btn_select) as Button
        val btnCurrent: Button = findViewById(R.id.btn_current) as Button

        val c = Calendar.getInstance()
        c.firstDayOfWeek = Calendar.SUNDAY
        c.minimalDaysInFirstWeek = 1
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dialog: DatePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { datePicker, y, m, dayOfMonth ->
            val temp = m + 1
            val date = "$y 年 $temp 月 $dayOfMonth 日"
            var weeks = TimeUtils.getWeeks(y, m, dayOfMonth)
            if(temp == 12 && weeks == 1){//一年中最后一周
                weeks = c.getMaximum(Calendar.WEEK_OF_YEAR)
            }
            setShowStr1(String.format(selectStr, date, weeks))
        }, year, month, day)

        btnSelect.setOnClickListener {
            if (!dialog.isShowing) {
                dialog.show()
            }
        }

        btnCurrent.setOnClickListener {
            setShowStr1()
        }

        setShowStr1()
    }

    fun setWeeksToDateBlock() {
        val etYear: EditText = findViewById(R.id.et_year) as EditText
        val etWeek: EditText = findViewById(R.id.et_week) as EditText
        val btnSure: Button = findViewById(R.id.btn_sure) as Button

        btnSure.setOnClickListener {
            val year = Integer.parseInt(etYear.text.toString())
            val week = Integer.parseInt(etWeek.text.toString())
            setShowStr2(year, week)
        }
    }

    fun setShowStr1() {
        setShowStr1(String.format(currentStr, TimeUtils.getWeeks()))
    }

    fun setShowStr2(year: Int, week: Int) {
        if (year < 1999 || year > 2999) {
            setShowStr2("请输入正确的年份")
            return
        }
        if (week < 1) {
            setShowStr2("请输入正确的周")
            return
        }
        val c: Calendar = Calendar.getInstance()
        val sumWeeks = c.getMaximum(Calendar.WEEK_OF_YEAR)
        val minWeeks = c.getMinimum(Calendar.WEEK_OF_YEAR)
        Log.d("====","" + minWeeks)
        if (week > sumWeeks) {
            setShowStr2(String.format("%d年只有%d周", year, sumWeeks))
            return
        }
        val dateStart = TimeUtils.getDateStr("yyyy-MM-dd", year, week)
        c.time = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).parse(dateStart)
        c.add(Calendar.DAY_OF_YEAR, 6)//不能使用roll,如果使用roll，2017-12-31是周日roll 6天，结果是2017-01-06而不是2018-01-06
        val dateEnd = TimeUtils.getDateStr("yyyy-MM-dd", c.time)
        val str = "$year 年第$week 周是$dateStart 到$dateEnd"
        setShowStr2(str)
    }

    fun setShowStr1(str: String) {
        val tvShow: TextView = findViewById(R.id.tv_show1) as TextView
        tvShow.text = str
    }

    fun setShowStr2(str: String) {
        val tvShow: TextView = findViewById(R.id.tv_show2) as TextView
        tvShow.text = str
    }
}