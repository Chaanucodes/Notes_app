package com.example.amotion

import android.graphics.Color
import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.core.text.toSpannable
import java.text.SimpleDateFormat

fun Long.convertLongToDateString(): String {
    return SimpleDateFormat("EEEE MMM-dd-yyyy HH:mm")
        .format(this).toString()
}

fun String.convertToSearchableText(searchKey : String) : SpannableString {

    if(!this.contains(" ")){
        val spannable = SpannableString(searchKey)
        spannable.setSpan(ForegroundColorSpan(Color.RED), 0, searchKey.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return spannable
    }else{
        var start = this.toLowerCase().replace("\n"," ").substringBefore(searchKey, "")
        var end = this.toLowerCase().replace("\n"," ").substringAfter(searchKey)
        start = if(start.length>11){
            "..${start.substring(start.length-11, start.length)}"
        }else{
            start
        }

        end = if (end.length>30){
            "${end.substring(0, 31)}.."
        }else{

            end
        }

        val finalString = "$start$searchKey$end"

        val span = SpannableString(finalString)
        span.setSpan(ForegroundColorSpan(Color.RED), start.length, start.length + searchKey.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return span
    }

}