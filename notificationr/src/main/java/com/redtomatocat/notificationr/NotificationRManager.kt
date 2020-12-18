package com.redtomatocat.notificationr

import android.os.Handler
import android.os.Looper
import com.redtomatocat.notificationr.imp.NotificationRListener

/**
 * Create on 2020/12/12
 *
 * @author redtomatocat
 * @version 1.0.0
 **/
internal class NotificationRManager private constructor(){

    //<NotificationId, NotificationRListener>
    val cacheNt = LinkedHashMap<Int, NotificationRListener>()
    val handler = Handler(Looper.getMainLooper())

    fun postUIThread(r: Runnable) {
        handler.post(r)
    }

    companion object {
        private var sInstance: NotificationRManager? = null
        val instance: NotificationRManager
            get() {
                if (sInstance == null) sInstance = NotificationRManager()
                return sInstance as NotificationRManager
            }
    }

}