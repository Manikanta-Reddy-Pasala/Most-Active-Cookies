package com.quantcast.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.quantcast.model.MostActiveCookieCount;
import org.junit.jupiter.api.Test;

class CookieStackTest {
  @Test void testConstructor() {
    CookieStack actualCookieStack = new CookieStack(3);
    assertTrue(actualCookieStack.isEmpty());
    assertEquals(0, actualCookieStack.size());
    assertFalse(actualCookieStack.isFull());
  }

  @Test void testConstructor2() {
    CookieStack actualCookieStack = new CookieStack(3);
    assertTrue(actualCookieStack.isEmpty());
    assertFalse(actualCookieStack.isFull());
  }

  @Test void testConstructor3() {
    assertThrows(NegativeArraySizeException.class, () -> new CookieStack(-1));
  }

  @Test void testPush() {
    CookieStack cookieStack = new CookieStack(3);
    cookieStack.push(new MostActiveCookieCount("Cookie Name", 3));
    assertFalse(cookieStack.isEmpty());
  }

  @Test void testPush2() {
    CookieStack cookieStack = new CookieStack(1);
    cookieStack.push(new MostActiveCookieCount("Cookie Name", 3));
    assertFalse(cookieStack.isEmpty());
  }

  @Test void testPush3() {
    CookieStack cookieStack = new CookieStack(3);
    cookieStack.push(new MostActiveCookieCount("42", -1));
    assertFalse(cookieStack.isEmpty());
  }

  @Test void testPop() {
    CookieStack cookieStack = new CookieStack(3);
    MostActiveCookieCount mostActiveCookieCount = new MostActiveCookieCount("Cookie Name", 3);

    cookieStack.push(mostActiveCookieCount);
    assertSame(mostActiveCookieCount, cookieStack.pop());
    assertTrue(cookieStack.isEmpty());
  }

  @Test void testSize() {
    assertEquals(0, (new CookieStack(3)).size());
  }

  @Test void testIsEmpty() {
    assertTrue((new CookieStack(3)).isEmpty());
  }

  @Test void testIsEmpty2() {
    CookieStack cookieStack = new CookieStack(3);
    cookieStack.push(new MostActiveCookieCount("Cookie Name", 3));
    assertFalse(cookieStack.isEmpty());
  }

  @Test void testIsFull() {
    assertFalse((new CookieStack(3)).isFull());
    assertTrue((new CookieStack(0)).isFull());
  }
}

