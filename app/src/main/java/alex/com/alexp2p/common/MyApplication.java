package alex.com.alexp2p.common;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import cn.sharesdk.framework.ShareSDK;

/**
 * Created by Administrator on 2017/2/7.
 */

public class MyApplication extends Application {

    public static Context context;//上下文
    public static Handler handler;//需要使用的handler
    public static Thread mainThread;//提供主线程对象
    public static int mainThreadId;//提供主线程的ID


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        handler = new Handler();
        mainThread = Thread.currentThread();//实例化当前Application的线程即为主线程
        mainThreadId = android.os.Process.myTid();//获取当前线程的ID

        //初始化当前的异常处理器
//        CrashHandler.getInstance().init();
        //初始化ShareSDK
        ShareSDK.initSDK(this);
    }
}
