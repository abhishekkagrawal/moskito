/*
 * $Id$
 * 
 * This file is part of the MoSKito software project
 * that is hosted at http://moskito.dev.java.net.
 * 
 * All MoSKito files are distributed under MIT License:
 * 
 * Copyright (c) 2006 The MoSKito Project Team.
 * 
 * Permission is hereby granted, free of charge,
 * to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), 
 * to deal in the Software without restriction, 
 * including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit 
 * persons to whom the Software is furnished to do so, 
 * subject to the following conditions:
 * 
 * The above copyright notice and this permission notice
 * shall be included in all copies 
 * or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY
 * OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT 
 * LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS 
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, 
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, 
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR
 * THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package net.java.dev.moskito.core.stats.impl;

import java.util.concurrent.atomic.AtomicLong;

import net.java.dev.moskito.core.stats.Interval;

/**
 * This class implements a ValueHolder that is able to process long values.
 * 
 * @author dvayanu
 * @see ValueHolder
 */
class LongValueHolder extends AbstractValueHolder {

	/**
	 * This is the value that will be used as defaultValue if no other one was specified.
	 */
	public static final long DEFAULT_DEFAULT_VALUE = 0;

	/**
	 * This attribute stores the current value supporting concurrent access.
	 */
	private AtomicLong currentValue;

	/**
	 * The last value will be stored here.
	 */
	private long lastValue;

	/**
	 * This is the default value that this ValueHolder contains after a reset.
	 */
	private long defaultValue;

	/**
	 * The Constructor.
	 * 
	 * @param aInterval this is the Interval this value will be updated
	 */
	public LongValueHolder(Interval aInterval) {
		super(aInterval);
		defaultValue = DEFAULT_DEFAULT_VALUE;
		currentValue = new AtomicLong(defaultValue);
	}

	/**
	 * @see net.java.dev.moskito.core.stats.IIntervalListener#intervalUpdated(net.java.dev.moskito.core.stats.impl.IntervalImpl)
	 */
	public void intervalUpdated(Interval caller) {
		lastValue = currentValue.get();
		currentValue.set(defaultValue);
	}

	/**
	 * This method returns the lastValue.
	 * 
	 * @return the lastValue
	 */
	public long getLastValue() {
		return lastValue;
	}

	/**
	 * @see net.java.dev.moskito.core.stats.impl.AbstractValueHolder#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + " L " + lastValue + " / "
				+ currentValue.get();
	}

	/**
	 * @see net.java.dev.moskito.core.stats.ValueHolder#decrease()
	 */
	public void decrease() {
		currentValue.decrementAndGet();

	}

	/**
	 * @see net.java.dev.moskito.core.stats.ValueHolder#increase()
	 */
	public void increase() {
		currentValue.incrementAndGet();
	}

	/**
	 * @see net.java.dev.moskito.core.stats.ValueHolder#decreaseByDouble(double)
	 */
	public void decreaseByDouble(double aValue) {
		decreaseByLong((long) aValue);
	}

	/**
	 * @see net.java.dev.moskito.core.stats.ValueHolder#decreaseByInt(int)
	 */
	public void decreaseByInt(int aValue) {
		decreaseByLong(aValue);
	}

	/**
	 * @see net.java.dev.moskito.core.stats.ValueHolder#decreaseByLong(long)
	 */
	public void decreaseByLong(long aValue) {
		currentValue.addAndGet(-aValue);
	}

	/**
	 * @see net.java.dev.moskito.core.stats.ValueHolder#getValueAsDouble()
	 */
	public double getValueAsDouble() {
		return (double) getValueAsLong();
	}

	/**
	 * @see net.java.dev.moskito.core.stats.ValueHolder#getValueAsInt()
	 */
	public int getValueAsInt() {
		return (int) getValueAsLong();
	}

	/**
	 * @see net.java.dev.moskito.core.stats.ValueHolder#getValueAsLong()
	 */
	public long getValueAsLong() {
		return lastValue;
	}

	/**
	 * @see net.java.dev.moskito.core.stats.ValueHolder#increaseByDouble(double)
	 */
	public void increaseByDouble(double aValue) {
		increaseByLong((long) aValue);

	}

	/**
	 * @see net.java.dev.moskito.core.stats.ValueHolder#increaseByInt(int)
	 */
	public void increaseByInt(int aValue) {
		increaseByLong((long) aValue);
	}

	/**
	 * @see net.java.dev.moskito.core.stats.ValueHolder#increaseByLong(long)
	 */
	public void increaseByLong(long aValue) {
		currentValue.addAndGet(aValue);
	}

	/**
	 * @see net.java.dev.moskito.core.stats.ValueHolder#setValueAsDouble(double)
	 */
	public void setValueAsDouble(double aValue) {
		setValueAsLong((long) aValue);

	}

	/**
	 * @see net.java.dev.moskito.core.stats.ValueHolder#setValueAsInt(int)
	 */
	public void setValueAsInt(int aValue) {
		setValueAsLong((long) aValue);
	}

	/**
	 * @see net.java.dev.moskito.core.stats.ValueHolder#setValueAsLong(long)
	 */
	public void setValueAsLong(long aValue) {
		currentValue.set(aValue);
	}

	/**
	 * @see net.java.dev.moskito.core.stats.ValueHolder#setDefaultValueAsLong(long)
	 */
	public void setDefaultValueAsLong(long aValue) {
		defaultValue = aValue;
	}

	/**
	 * @see net.java.dev.moskito.core.stats.ValueHolder#setDefaultValueAsInt(int)
	 */
	public void setDefaultValueAsInt(int aValue) {
		setDefaultValueAsLong((long) aValue);
	}

	/**
	 * @see net.java.dev.moskito.core.stats.ValueHolder#setDefaultValueAsDouble(double)
	 */
	public void setDefaultValueAsDouble(double aValue) {
		setDefaultValueAsLong((long) aValue);
	}

	/**
	 * @see net.java.dev.moskito.core.stats.ValueHolder#reset()
	 */
	public void reset() {
		currentValue.set(defaultValue);
		lastValue = defaultValue;
	}

	/**
	 * @see net.java.dev.moskito.core.stats.ValueHolder#getCurrentValueAsDouble()
	 */
	public double getCurrentValueAsDouble() {
		return (double) getCurrentValueAsLong();
	}

	/**
	 * @see net.java.dev.moskito.core.stats.ValueHolder#getCurrentValueAsInt()
	 */
	public int getCurrentValueAsInt() {
		return (int) getCurrentValueAsLong();
	}

	/**
	 * @see net.java.dev.moskito.core.stats.ValueHolder#getCurrentValueAsLong()
	 */
	public long getCurrentValueAsLong() {
		return currentValue.get();
	}

}