package com.example.fastwork.utils.value

import android.app.ActivityManager
import android.content.Context
import android.support.multidex.BuildConfig
import com.example.fastwork.utils.log.Lg

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Stack
import java.util.UUID


object ValueUtil {
    private val DEBUG = true

    //    private static ThreadLocal<Long> sTime=new ThreadLocal<>();
    private val sTimes = ThreadLocal<Stack<Long>>()

    var dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    var sSimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
    var timeFormat = SimpleDateFormat("HH:mm:ss")

    init {
        sTimes.set(Stack())
    }

    fun formatDate(date: Date): String {
        return dateFormat.format(date)
    }

    fun formatDate(date: Long): String {
        return dateFormat.format(date)
    }

    fun formatTime(date: Long): String {
        return timeFormat.format(date)
    }

    fun compare(lhs: Int, rhs: Int): Int {
        return if (lhs < rhs) -1 else if (lhs == rhs) 0 else 1
    }

    fun compare(lhs: Long, rhs: Long): Int {
        return if (lhs < rhs) -1 else if (lhs == rhs) 0 else 1
    }

    fun parseDate(date: String?): Date? {
        if (date == null) return null
        try {
            return dateFormat.parse(date)
        } catch (e: ParseException) {
            //            Lg.e("ValueUtil", "parseDate", e);
            return null
        }

    }

    fun toInteger(value: Any): Int? {
        val v = toDouble(value)
        return v?.toInt()
    }

    fun toDouble(value: Any?): Double? {
        if (value == null) return null
        if (value is Number) {
            return value.toDouble()
        }
        try {
            return java.lang.Double.parseDouble(value.toString())
        } catch (e: NumberFormatException) {

        }

        return null
    }


    fun getStr(map: Map<String, Any>, key: String, def: String): String {
        val value = map[key] ?: return def
        return value.toString()
    }

    fun getInt(map: Map<String, Any>, key: String, def: Int): Int {
        val value = map[key] ?: return def
        val ret = toInteger(value)
        return ret ?: def
    }

    fun getFloat(map: Map<String, Any>, key: String, def: Double): Double {
        val value = map[key] ?: return def
        val ret = toDouble(value)
        return ret ?: def
    }


    fun getBool(map: Map<String, Any>, key: String, def: Boolean): Boolean {
        val value = map[key] ?: return def
        return java.lang.Boolean.parseBoolean(value.toString())
    }


    fun dip2px(context: Context?, dipValue: Float): Int {
        var scale = context?.resources?.displayMetrics?.density ?: 1 as Float
        return (dipValue * scale + 0.5f).toInt()
    }


    fun startTime() {
        if (!BuildConfig.DEBUG) return

        //        sTime.set(System.nanoTime());
        var stack = sTimes.get()
        if (stack == null) {
            sTimes.set(Stack<Long>())

        }
        stack!!.push(System.nanoTime())
    }

    fun endTime(msg: String?): Float {
        if (!BuildConfig.DEBUG) return 0f
        val stack = sTimes.get()
            ?: //            Lg.w("ValueUtil", "endTime:please invoke startTime before endTime");
            return -1f
        val start = stack.pop() ?: return 0f
        val use = (System.nanoTime() - start) / 1000000f
        if (msg != null) {
                        Lg.i("endTime", msg + " : " + use + "ms");
        }
        if (stack.size == 0) {
            sTimes.set(null)
        }
        return use
    }

    fun uuid(): String {
        return UUID.randomUUID().toString().replace("\\-".toRegex(), "")
    }

    @JvmOverloads
    fun parseInt(s: String, def: Int = 0): Int {
        try {
            return Integer.parseInt(s)
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }

        return def
    }

    fun parseFloat(name: String): Float {
        try {
            return java.lang.Float.parseFloat(name)
        } catch (e: NumberFormatException) {
            return 0f
        }

    }
}
