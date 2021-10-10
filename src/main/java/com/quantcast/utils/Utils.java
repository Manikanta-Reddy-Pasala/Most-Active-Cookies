package com.quantcast.utils;

import com.quantcast.model.MostActiveCookieCount;
//import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Utils {

  private static final String CSV_FORMAT = "csv";
  public static final String COMMA_DELIMITER = ",";
  public static final String DOT_DELIMITER = ".";
  public static final String DATE_FORMAT = "uuuu-M-d";
  public static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd";

//  private static final Logger logger = Logger.getLogger(Utils.class);

  /*
   * Checks if yes it return true else false
   * @param string
   * @return true if given string is an empty or null type else false
   */
  public static boolean isEmptyString(String string) {

    return string == null || string.isEmpty() || string.trim().isEmpty();
  }

  public static boolean isValidFileExtension(String filename) {

    boolean result = false;
    Optional<String> fileExtension = getFileExtension(filename);

    if (fileExtension.isPresent() && (fileExtension.get().equals(CSV_FORMAT))) {
      result = true;
    }
    return result;
  }

  public static boolean isValidFile(String filename) {

    boolean result = false;
    File file = new File(filename);
    if (!file.isDirectory())
      file = file.getParentFile();
    if (file.exists()) {
      result = true;
    }
    return result;
  }

  public static boolean isValidDateFormat(String date) {
    boolean result;

    try {
      // ResolverStyle.STRICT for 30, 31 days checking, and also leap year.
      LocalDate.parse(date, DateTimeFormatter.ofPattern(DATE_FORMAT).withResolverStyle(ResolverStyle.STRICT));
      result = true;
    } catch (DateTimeParseException e) {
//      logger.debug("Invalid Date format found:", e);
      result = false;
    }
    return result;
  }

  public static Optional<String> getFileExtension(String filename) {
    return Optional.ofNullable(filename).filter(f -> f.contains(DOT_DELIMITER))
        .map(f -> f.substring(filename.lastIndexOf(DOT_DELIMITER) + 1));
  }

  public static String[] loadCSVFile(String filename) {
    List<String> result = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
      String line;
      //to handle header
      boolean isRecord = false;
      while ((line = br.readLine()) != null) {
        if (!isRecord) {
          isRecord = true;
        } else {
          String[] values = line.split(COMMA_DELIMITER);
          if (values.length > 1) {

            if (!Utils.isEmptyString(values[0]) && !Utils.isEmptyString(values[1])) {
              //check for valid date in line
              String formattedDate = convertUTCDateToSimpleDate(values[1]);

              if (formattedDate != null) {
                String sb = values[0] + COMMA_DELIMITER + formattedDate;
                result.add(sb);
              } else {
//                logger.debug("Skipping invalid date format record in the file");
              }
            } else {
//              logger.debug("Skipping the null/empty data record in the file");
            }
          }
        }
      }
    } catch (FileNotFoundException e) {
//      logger.warn("Given file: " + filename + " not found:");
      System.out.println("Given file: " + filename + " not found:");
    } catch (IOException e) {
//      logger.warn("Exception  while parsing the record in the file:" + filename + ". Exception ", e);
    }

    Object[] resultObjArr = result.toArray();
    return Arrays.copyOf(resultObjArr, resultObjArr.length, String[].class);
  }

  private static String convertUTCDateToSimpleDate(String utcDate) {

    String result = null;
    try {
      Date date = Date.from(ZonedDateTime.parse(utcDate).toInstant());
      result = new SimpleDateFormat(SIMPLE_DATE_FORMAT).format(date);
    } catch (DateTimeParseException exception) {
//      logger.debug("Exception  while parsing the Date:" + utcDate + ". Exception: ", exception);

    }
    return result;
  }

  public static String[] getMatchedCookiesByDate(String[] cookiesList, String date) {
    //search using binary search and get matched data
    return BinarySearchUtil.getMatchedCookies(cookiesList, date, cookiesList.length);
  }

  public static List<String> getMostActiveCookies(String[] matchedCookies) {

    List<String> mostActiveCookies = new ArrayList<>();
    Map<String, Integer> result = new HashMap<>();
    CookieStack stack = new CookieStack(10);
    int maxCount = 1;

    for (String line : matchedCookies) {

      String cookie = line.split(Utils.COMMA_DELIMITER)[0];

      int count = 1;
      if (result.containsKey(cookie)) {
        count = result.get(cookie);
        count++;
      }
      result.put(cookie, count);

      if (count >= maxCount) {

        if (count > maxCount) {
          maxCount = count;
          stack.setEmpty();
        }
        MostActiveCookieCount cookieEntry = new MostActiveCookieCount(cookie, maxCount);
        stack.push(cookieEntry);
      }
    }

    for (int i = stack.size(); i > 0; i--) {
      MostActiveCookieCount mostActiveCookie = stack.pop();
      mostActiveCookies.add(mostActiveCookie.getCookieName());
    }

    return mostActiveCookies;
  }

  static int compareDates(String inputDate, String cookieObject) {

    int result = 0;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
    String cookieDate = cookieObject.split(Utils.COMMA_DELIMITER)[1];
    try {
      Date input = simpleDateFormat.parse(inputDate);
      Date cookie = simpleDateFormat.parse(cookieDate);

      if (input.compareTo(cookie) > 0) {
        result = 1;
      } else if (input.compareTo(cookie) < 0) {
        result = -1;
      }
    } catch (ParseException exception) {
//      logger.debug("Exception  while parsing the Date:" + inputDate + ". Exception: ", exception);
    }
    return result;
  }
}
