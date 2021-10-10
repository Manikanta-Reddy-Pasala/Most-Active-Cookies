package com.quantcast.utils;

import java.util.Arrays;

import static com.quantcast.utils.Utils.compareDates;

public class BinarySearchUtil {

  static String[] getMatchedCookies(String[] arr, String inputDate, int length) {
    // Index of the first matched index in input array
    int start;

    // Index of the last matched index in input array
    int end;

    /* Get the index of first occurrence of inputDate */
    start = first(arr, 0, length - 1, inputDate);

    /* If inputDate doesn't exist in arr[] then return -1 */
    if (start == -1)
      return new String[0];

      /* Else get the index of last occurrence of inputDate.
         Note that we are only looking in the
         sub-array after first occurrence */
    end = last(arr, start, length - 1, inputDate, length);

    /* Return array of matched data */
    return Arrays.copyOfRange(arr, start, end + 1);
  }

  /* If inputDate is present in input array then returns the
     index of FIRST occurrence of inputDate in input array,
     otherwise returns -1 */
  static int first(String[] arr, int low, int high, String inputDate) {
    if (high >= low) {

      int mid = (low + high) / 2;
      if ((mid == 0 || (compareDates(inputDate, arr[mid - 1]) < 0)) && (compareDates(inputDate, arr[mid]) == 0))
        return mid;
      else if ((compareDates(inputDate, arr[mid]) < 0))
        return first(arr, (mid + 1), high, inputDate);
      else
        return first(arr, low, (mid - 1), inputDate);
    }
    return -1;
  }

  /* If inputDate is present in input array then returns the
     index of LAST occurrence of inputDate in input array,
     otherwise returns -1 */
  static int last(String[] arr, int low, int high, String inputDate, int length) {
    if (high >= low) {
      int mid = (low + high) / 2;
      if ((mid == length - 1 || (compareDates(inputDate, arr[mid + 1]) > 0)) && (compareDates(inputDate,
          arr[mid]) == 0))
        return mid;
      else if (compareDates(inputDate, arr[mid]) > 0)
        return last(arr, low, (mid - 1), inputDate, length);
      else
        return last(arr, (mid + 1), high, inputDate, length);
    }
    return -1;
  }
}
