package com.quantcast.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

class UtilsTest {
  @Test void testIsEmptyString() {
    assertFalse(Utils.isEmptyString("String"));
    assertTrue(Utils.isEmptyString(null));
    assertTrue(Utils.isEmptyString(""));
  }

  @Test void testIsValidFileExtension() {
    assertFalse(Utils.isValidFileExtension("foo.txt"));
    assertFalse(Utils.isValidFileExtension("foo"));
    assertTrue(Utils.isValidFileExtension("foo.csv"));
  }

  @Test void testIsValidFile() {
    assertTrue(Utils.isValidFile(Utils.DOT_DELIMITER));
  }

  @Test void testIsValidDateFormat() {
    assertTrue(Utils.isValidDateFormat("2020-03-01"));
    assertFalse(Utils.isValidDateFormat("foo"));
  }

  @Test void testGetFileExtension() {
    Optional<String> actualFileExtension = Utils.getFileExtension("foo.csv");
    assertTrue(actualFileExtension.isPresent());
    assertEquals("csv", actualFileExtension.get());
  }

  @Test void testGetFileExtension2() {
    assertFalse(Utils.getFileExtension("foo").isPresent());
  }

  @Test void testLoadCSVFile() {

    assertEquals(0, Utils.loadCSVFile("foo.txt").length);
  }

  @Test void testGetMatchedCookiesByDate() {
    assertThrows(ArrayIndexOutOfBoundsException.class,
        () -> Utils.getMatchedCookiesByDate(new String[] { "Cookies List" }, "2020-03-01"));
    assertEquals(0, Utils.getMatchedCookiesByDate(new String[] {}, "2020-03-01").length);
    assertThrows(ArrayIndexOutOfBoundsException.class,
        () -> Utils.getMatchedCookiesByDate(new String[] { Utils.DATE_FORMAT, Utils.DATE_FORMAT, Utils.DATE_FORMAT },
            "2020-03-01"));
  }

  @Test void testGetMostActiveCookies() {
    List<String> actualMostActiveCookies = Utils.getMostActiveCookies(new String[] { "Matched Cookies" });
    assertEquals(1, actualMostActiveCookies.size());
    assertEquals("Matched Cookies", actualMostActiveCookies.get(0));
  }

  @Test void testGetMostActiveCookies2() {
    assertThrows(ArrayIndexOutOfBoundsException.class,
        () -> Utils.getMostActiveCookies(new String[] { Utils.COMMA_DELIMITER }));
  }

  @Test void testGetMostActiveCookies3() {
    List<String> actualMostActiveCookies = Utils.getMostActiveCookies(
        new String[] { "Matched Cookies", "Matched Cookies" });
    assertEquals(1, actualMostActiveCookies.size());
    assertEquals("Matched Cookies", actualMostActiveCookies.get(0));
  }

  @Test void testCompareDates() {
    assertThrows(ArrayIndexOutOfBoundsException.class, () -> Utils.compareDates("2020-03-01", "Cookie Object"));
  }
}

