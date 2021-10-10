package com.quantcast.utils;

import com.quantcast.model.MostActiveCookieCount;

public class CookieStack {
  private MostActiveCookieCount[] arr;
  private int top;
  private int capacity;

  CookieStack(int size)
  {
    arr = new MostActiveCookieCount[size];
    capacity = size;
    top = -1;
  }

  public void push(MostActiveCookieCount cookieCount) {

    if (!isFull()) {
      arr[++top] = cookieCount;
    } else {
      resize(capacity * 2);
      push(cookieCount);
    }

  }

  private void resize(int newSize) {
    MostActiveCookieCount[] transferArray = new MostActiveCookieCount[newSize];

    for (int i = 0; i < arr.length; i++) {
      transferArray[i] = arr[i];
      arr = transferArray;
    }
    capacity = newSize;
  }

  public MostActiveCookieCount pop() {
    if (isEmpty()) {
      System.exit(1);
    }
    return arr[top--];
  }

  // Utility function to return the size of the stack
  public int size() {
    return top + 1;
  }

  // Utility function to check if the stack is empty or not
  public boolean isEmpty() {
    return top == -1;
  }

  // Utility function to empty  the stack
  public void setEmpty() {
    top = -1;
  }

  // Utility function to check whether stack is full or not
  public boolean isFull() {
    return (top + 1 == capacity);
  }

}
