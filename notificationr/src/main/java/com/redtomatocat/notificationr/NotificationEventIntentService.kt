package com.redtomatocat.notificationr

import android.app.IntentService
import android.content.Intent

/**
 * Create on 2020/12/12
 *
 * @author redtomatocat
 * @version 1.0.0
 **/
class NotificationEventIntentService : IntentService("NotificationEventIntentService") {

    override fun onHandleIntent(intent: Intent?) {
        val event = intent?.action
        event?.let {
            NotificationRManager.instance.cacheNt.apply {
                for (notificationId in keys) {
                    val listener = this[notificationId]
                    NotificationRManager.instance.postUIThread(Runnable { listener?.onClick(it) })
                }
            }
        }
    }
}