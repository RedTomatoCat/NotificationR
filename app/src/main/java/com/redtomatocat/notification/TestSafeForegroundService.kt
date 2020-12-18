package com.redtomatocat.notification

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.redtomatocat.notificationr.service.SafeForegroundService
import java.util.*

/**
 * Create on 2020/12/12
 *
 * @author redtomatocat
 * @version 1.0.0
 **/
class TestSafeForegroundService : SafeForegroundService() {

    private val timer = Timer()
    private val handler = Handler(Looper.getMainLooper())

    override fun runSafeThing() {
        timer.schedule(object : TimerTask(){
            override fun run() {
                handler.post { Toast.makeText(applicationContext, "Service Running ...", Toast.LENGTH_SHORT).show() }
            }
        }, 200, 2000)
    }

    override fun onDestroy() {
        timer.cancel()
        super.onDestroy()
    }


}