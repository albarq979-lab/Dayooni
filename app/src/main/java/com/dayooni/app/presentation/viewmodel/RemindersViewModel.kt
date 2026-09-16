package com.dayooni.app.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.dayooni.app.notification.ReminderScheduler
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class RemindersViewModel @Inject constructor(@ApplicationContext private val context: Context) : ViewModel() {
    fun schedule() = ReminderScheduler.schedule(context)
    fun cancel() = ReminderScheduler.cancel(context)
}
