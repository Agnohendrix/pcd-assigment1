package model;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SourceFile {
    private String path;
    private int length;

    private Lock mutex;

    public SourceFile(String path, int length){
        this.path = path;
        this.length = length;
        this.mutex = new ReentrantLock();
    }

    public int getLength() throws InterruptedException {
        try {
            mutex.lock();
            return length;
        } finally {
            mutex.unlock();
        }
    }

    public void setLength(int length) throws InterruptedException {
        try {
            mutex.lock();
            this.length = length;
        } finally {
            mutex.unlock();
        }
    }

    public String getPath() throws InterruptedException {
        try {
            mutex.lock();
            return path;
        } finally {
            mutex.unlock();
        }
    }

    public void setPath(String path) throws InterruptedException {
        try {
            mutex.lock();
            this.path = path;
        } finally {
            mutex.unlock();
        }
    }
}
