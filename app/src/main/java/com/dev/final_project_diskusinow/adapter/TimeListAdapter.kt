package com.dev.final_project_diskusinow.adapter

import android.content.Context
import android.widget.ArrayAdapter

class TimeListAdapter(context: Context, private val timeList: Array<String>) : ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, timeList)
