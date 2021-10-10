package com.quantcast;

import com.quantcast.utils.Utils;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.List;
import java.util.concurrent.Callable;

@Command(description = "Find most active cookies from your command line", name = "MostActiveCookies", mixinStandardHelpOptions = true, version = "1.0") public class MostActiveCookiesApplication
    implements Callable<Integer> {
  @Option(names = { "-f", "--f" }, description = "logfile path") String logFile;
  @Option(names = { "-d", "--d" }, description = "Input Date to search Active Cookies") String date;

  //  Logger logger = Logger.getLogger(MostActiveCookiesApplication.class);

  public static void main(String[] args) {
    int exitCode = new CommandLine(new MostActiveCookiesApplication()).execute(args);
    System.exit(exitCode);
  }

  public Integer call() {
    if (Utils.isEmptyString(this.logFile)) {
      //      logger.error("****** File name can't be empty of null ******");
      System.out.println("****** File name can't be empty of null ******");

      return 1;
    }

    if (Utils.isEmptyString(this.date)) {
      //      logger.error(" ****** Input Search Date can't be empty of null ******");
      System.out.println(" ****** Input Search Date can't be empty of null ******");

      return 1;
    }

    if (!Utils.isValidDateFormat(this.date)) {
      //      logger.error(" ****** Input Search Date is invalid format ****** ");
      System.out.println(" ****** Input Search Date is invalid format ****** ");
      return 1;
    }

    String[] cookiesList;
    // validate folder, file and file extension
    if (Utils.isValidFileExtension(logFile) && Utils.isValidFile(logFile)) {

      // load file content into list (validate every line)
      cookiesList = Utils.loadCSVFile(logFile);

      String[] matchedCookies = Utils.getMatchedCookiesByDate(cookiesList, date);

      if (matchedCookies.length > 0) {
        List<String> finalCookies = Utils.getMostActiveCookies(matchedCookies);

        //        logger.info("****** Most active cookies are ******");
        System.out.println("****** Most active cookies are ******");

        for (String cookie : finalCookies) {
          //          logger.info(cookie);
          System.out.println(cookie);
        }
      } else {
        //        logger.error("****** No Data matched with given Date ******");
        System.out.println("****** No Data matched with given Date ******");
      }
    } else {
      //      logger.error("****** Log file is not valid ******");
      System.out.println("****** Log file is not valid ******");

    }
    return 0;
  }
}
