/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tuanpla.utils;

/**
 *
 * @author centurion
 */
public class TimeWrapper {

    /** Object to be held in this wrapper instance. */
    /** Time at which this object expires. */
    private long expiryTime = 0;
    /** Last access time (updated by method call). */
    private long accessed = System.currentTimeMillis();

    /**
     * Creates a new wrapped object.
     * @param expiryTime object's idle time before death in milliseconds (0 - eternal)
     */
    public TimeWrapper(long expiryTime) {
        if (expiryTime > 0) {
            this.expiryTime = System.currentTimeMillis() + expiryTime;
        }
    }

    /**
     * Whether this item has expired.
     * (Expiry of zero indicates that it will never expire.)
     * @return 
     */
    public synchronized boolean isExpired() {
        return expiryTime > 0 && System.currentTimeMillis() > expiryTime;
    }

    /**
     * Sets idle time allowed before this item expires.
     * @param expiryTime idle time before expiry (0 = eternal)
     */
    synchronized void setLiveTime(long expiryTime) {
        if (expiryTime < 0) {
            throw new IllegalArgumentException("Invalid expiry time");
        } else if (expiryTime > 0) {
            this.expiryTime = System.currentTimeMillis() + expiryTime;
        } else {
            expiryTime = 0;
        }
    }

    /**
     * Updates the time this object was last accessed.
     */
    synchronized void updateAccessed() {
        accessed = System.currentTimeMillis();
    }

    /**
     * Returns the time this object was last accessed.
     */
    long getAccessed() {
        return accessed;
    }
}
