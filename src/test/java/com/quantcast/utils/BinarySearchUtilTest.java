package com.quantcast.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class BinarySearchUtilTest {
  @Test void testGetMatchedCookies() {
    assertThrows(ArrayIndexOutOfBoundsException.class,
        () -> BinarySearchUtil.getMatchedCookies(new String[] {}, "2020-03-01", 3));
    assertEquals(0, BinarySearchUtil.getMatchedCookies(new String[] { "Arr" }, "2020-03-01", 0).length);
    assertThrows(ArrayIndexOutOfBoundsException.class,
        () -> BinarySearchUtil.getMatchedCookies(new String[] {}, "2020-03-01", 1));
  }

  @Test void testFirst() {
    assertThrows(ArrayIndexOutOfBoundsException.class,
        () -> BinarySearchUtil.first(new String[] {}, 1, 1, "2020-03-01"));
    assertEquals(-1, BinarySearchUtil.first(new String[] { "Arr" }, 2, 1, "2020-03-01"));
    assertThrows(ArrayIndexOutOfBoundsException.class,
        () -> BinarySearchUtil.first(new String[] {}, 0, 1, "2020-03-01"));
  }

  @Test void testLast() {
    assertThrows(ArrayIndexOutOfBoundsException.class,
        () -> BinarySearchUtil.last(new String[] { "Arr" }, 1, 1, "2020-03-01", 3));
    assertEquals(-1, BinarySearchUtil.last(new String[] { "Arr", "Arr", "Arr" }, 2, 1, "2020-03-01", 3));
    assertThrows(ArrayIndexOutOfBoundsException.class,
        () -> BinarySearchUtil.last(new String[] { Utils.DATE_FORMAT }, 1, 1, "2020-03-01", 3));
  }
}

