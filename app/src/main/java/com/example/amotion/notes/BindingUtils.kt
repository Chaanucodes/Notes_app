package com.example.amotion.notes

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.amotion.convertLongToDateString
import com.example.amotion.convertToSearchableText
import com.example.amotion.database.MyNotes

@BindingAdapter("titleText")
fun TextView.setTextTitle(note : MyNotes){
    text = note.title
}

@BindingAdapter("contentText")
fun TextView.setTextContent(note : MyNotes){
    text = if(note.text.isNotEmpty()) note.text
    else "[No Content]"
}

@BindingAdapter("timeText")
fun TextView.setTextTime(note : MyNotes){
    text = note.timeStamp.convertLongToDateString()
}

@BindingAdapter("resultText", "resultData")
fun TextView.setResultText(searchKey : String, note : MyNotes){

    if(!note.text.contains(searchKey) && note.title.contains(searchKey)){
//        setText(note.title.convertToSearchableText(searchKey), TextView.BufferType.SPANNABLE)
        text = note.text
    }else{
        setText(note.text.convertToSearchableText(searchKey), TextView.BufferType.SPANNABLE)
    }

}

@BindingAdapter("set_img_src")
fun ImageView.setImgSrc(drawable : Int){
    setImageResource(drawable)
}

//@BindingAdapter("resultTitle", "resultTitleData")
//fun TextView.setResultTitle(searchKey : String, note : MyNotes){
//
//    setText(note.title.convertToSearchableText(searchKey), TextView.BufferType.SPANNABLE)
//
//}