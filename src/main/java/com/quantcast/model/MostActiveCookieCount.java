package com.quantcast.model;

public class MostActiveCookieCount {

  private final String cookieName;
  private final int count;

  public MostActiveCookieCount(String cookieName, int count) {
    this.cookieName = cookieName;
    this.count = count;
  }

  public String getCookieName() {
    return cookieName;
  }

  public int getCount() {
    return count;
  }
}
