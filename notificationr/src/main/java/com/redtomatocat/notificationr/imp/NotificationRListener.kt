package com.redtomatocat.notificationr.imp

import com.redtomatocat.notificationr.bean.NotificationViewData

/**
 * Create on 2020/12/12
 *
 * @author redtomatocat
 * @version 1.0.0
 **/
interface NotificationRListener {

    fun onClick(action: String)
    fun makeButtonsThing(): MutableList<NotificationViewData>

}