package com.nst.yourname.miscelleneious;

import android.util.Log;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadControl {
    private boolean cancelled = false;
    private final Lock lock = new ReentrantLock();
    private Condition pauseCondition = this.lock.newCondition();
    private boolean paused = false;

    public void pause() {
        this.lock.lock();
        Log.v("ThreadControl", "Pausing");
        this.paused = true;
        this.lock.unlock();
    }

    public void resume() {
        this.lock.lock();
        try {
            Log.v("ThreadControl", "Resuming");
            if (this.paused) {
                this.paused = false;
                this.pauseCondition.signalAll();
                this.lock.unlock();
            }
        } finally {
            this.lock.unlock();
        }
    }

    public void cancel() {
        this.lock.lock();
        try {
            Log.v("ThreadControl", "Cancelling");
            if (!this.cancelled) {
                this.cancelled = true;
                this.pauseCondition.signalAll();
                this.lock.unlock();
            }
        } finally {
            this.lock.unlock();
        }
    }

    public void waitIfPaused() throws InterruptedException {
        this.lock.lock();
        while (this.paused && !this.cancelled) {
            try {
                Log.v("ThreadControl", "Going to wait");
                this.pauseCondition.await();
                Log.v("ThreadControl", "Done waiting");
            } finally {
                this.lock.unlock();
            }
        }
    }

    public boolean isCancelled() {
        return this.cancelled;
    }
}
