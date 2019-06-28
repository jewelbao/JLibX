package com.jewel.libx;

import android.util.Log;

/**
 * 全局异常处理类，一般在Application中初始化，通过
 *
 * <pre class="prettyprint">
 *      Thread.UncaughtExceptionHandler handler = CrashHandler.getInstance();
 *      Thread.setDefaultUncaughtExceptionHandler(handler);
 * </pre>
 * 或者用户自己设置处理异常
 * <pre class="prettyprint">
 *      Thread.UncaughtExceptionHandler handler = CrashHandler.getInstance();
 *      handler.setHandlerType(CrashHandler.HANDLER_SELF)
 *      handler.setHandlerException(new CrashHandler.HandlerException() {
 *
 *          public void handlerUncaughtException(Thread thread, Throwable e) {
 *                 // 处理异常
 *          }
 *      });
 *      Thread.setDefaultUncaughtExceptionHandler(handler);
 * </pre>
 *
 * @author jewel
 * @email jewelbao88@gmail.com
 * @gitsite https://github.com/jewelbao
 * @since 2019/5/7
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    /**
     * 默认异常处理
     */
    public static final int HANDLER_DEFAULT = 0;
    /**
     * 用户自定义处理异常
     */
    public static final int HANDLER_SELF = 1;

    private static CrashHandler INSTANCE;
    private HandlerException handlerException;
    private int handlerType = HANDLER_DEFAULT;

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CrashHandler();
        }
        return INSTANCE;
    }

    /**
     * 设置异常处理类型，{@linkplain #HANDLER_DEFAULT 默认处理} | {@linkplain #HANDLER_SELF 用户自定义}
     *
     * @param handlerType {@link #HANDLER_DEFAULT } | {@link #HANDLER_SELF }
     */
    public CrashHandler setHandlerType(int handlerType) {
        this.handlerType = handlerType;
        return INSTANCE;
    }

    /**
     * 当{@link #setHandlerType(int)} type为{@link #HANDLER_SELF}时有效。
     *
     * @param handlerException 用户自定义异常处理接口
     */
    public CrashHandler setHandlerException(HandlerException handlerException) {
        this.handlerException = handlerException;
        return INSTANCE;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Log.e(TAG.TAG, "app crash!");
        if (handlerType == HANDLER_DEFAULT) {
            android.os.Process.killProcess(android.os.Process.myPid());
        } else if (handlerException != null) {
            handlerException.handlerUncaughtException(t, e);
        }
    }

    public interface HandlerException {
        void handlerUncaughtException(Thread thread, Throwable e);
    }
}
