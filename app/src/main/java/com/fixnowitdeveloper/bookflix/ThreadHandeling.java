package com.fixnowitdeveloper.bookflix;

class ThreadHandeling implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        e.printStackTrace();
    }
}
