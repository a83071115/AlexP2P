package alex.com.alexp2p.common;

import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import alex.com.alexp2p.util.UIUtils;

/**
 * Created by Administrator on 2017/2/8.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    //系统默认的处理未捕获异常的处理器
    private Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler;

    //单例模式：（懒汉式）
    //本身实例化未捕获异常的处理器的操作就是系统在一个单独的线程中完成的，所有不涉及到
    //多线程的问题，所以使用懒汉式更好！
    private CrashHandler(){

    }


    private static CrashHandler crashHandler = null;
    public static CrashHandler getInstance(){
        if(crashHandler == null){
            crashHandler = new CrashHandler();
        }
        return crashHandler;
    }


    public void init(){
        defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        //将当前类设置为出现未捕获异常的处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 出现异常后 调用此方法
     * @param thread 线程
     * @param ex 异常信息
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {

//        Log.e("TAG", "亲，出现了未捕获的异常了！" + ex.getMessage());
        new Thread(){
            public void run(){
                //prepare()和loop()之间的操作就是在主线程中执行的！
                //在android系统中，默认情况下，一个线程中是不可以调用Looper进行消息的处理的。除非是主线程
                Looper.prepare();
                Toast.makeText(UIUtils.getContext(), "亲，出现了未捕获的异常了！", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();
        //收集异常信息
        collectionException(ex);

        try {
            Thread.sleep(2000);

            //移除当前activity
            ActivityManager.getInstance().removeCurrentA();
            //结束当前的进程
            android.os.Process.killProcess(android.os.Process.myPid());
            //结束虚拟机
            System.exit(0);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    /**
     * 联网收集异常
     * @param ex  异常信息
     */
    private void collectionException(Throwable ex){
        final String exMessage = ex.getMessage();
        //收集具体的客户的手机、系统的信息
        final String message = Build.DEVICE + ":" + Build.MODEL + ":" + Build.PRODUCT + ":" + Build.VERSION.SDK_INT;

        //发送给后台此异常信息
        new Thread(){
            public void run(){
                //需要按照指定的url，访问后台的sevlet,将异常信息发送过去
                Log.e("TAG", "exception = " + exMessage);
                Log.e("TAG", "message = " + message);
            }
        }.start();

    }
}
