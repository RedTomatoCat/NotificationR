### NotificationR 

Android Notifiction 全面SDK兼容，提供前台服务Notification隐藏Service

### 核心类

```
NotificationR   //完全封装Notifiction 
```

```
SafeForegroundService  //前台服务Notification隐藏Service
```

### Exp:

```
NotificationR.Builder(this)
        .setTitle("Test")                //标题
        .setContentText("Text Content")  //内容
        .build().show(12121)             //通知ID
```