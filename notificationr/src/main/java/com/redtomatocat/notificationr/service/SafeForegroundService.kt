package com.redtomatocat.notificationr.service

import android.app.Service
import android.content.Intent
import com.redtomatocat.notificationr.NotificationR
import com.redtomatocat.notificationr.R

/**
 * Create on 2020/12/12
 *
 * @author redtomatocat
 * @version 1.0.0
 **/
abstract class SafeForegroundService : Service() {

    override fun onBind(intent: Intent?) = null

    override fun onDestroy() {
        startForeground(ID, NotificationR.Builder(this).setSmallIcon(R.drawable.icon_safe_service_logo).make())
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        runSafeThing()
        startForeground(ID, NotificationR.Builder(this).setSmallIcon(R.drawable.icon_safe_service_logo).make())
        stopForeground(true)
        return START_STICKY
    }

    companion object{
        private const val ID = 0X111
    }

    abstract fun runSafeThing()

}