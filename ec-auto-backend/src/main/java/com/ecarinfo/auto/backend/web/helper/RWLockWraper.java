package com.ecarinfo.auto.backend.web.helper;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RWLockWraper {
	final ReadWriteLock lock = new ReentrantReadWriteLock();
	final Lock r = lock.readLock();
	final Lock w = lock.writeLock();
	public ReadWriteLock getLock() {
		return lock;
	}
	public Lock getR() {
		return r;
	}
	public Lock getW() {
		return w;
	}	
}
