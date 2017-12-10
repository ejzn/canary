package canary;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class CSVUtil {

  private static final char DEFAULT_DELIMITER = ',';

  public static void writeLine(Writer w, List<String> values) {
    writeLine(w, values, DEFAULT_DELIMITER, ' ');
  }

  public static void writeLine(Writer w, List<String> values, char delimiter) {
    writeLine(w, values, delimiter, ' ');
  }

  // https://tools.ietf.org/html/rfc4180
  private static String followCVSformat(String value) {
    String result = value;
    if (result.contains("\"")) {
      result = result.replace("\"", "\"\"");
    }
    return result;
  }

  public static void writeLine(Writer w, List<String> values, char delimiter, char customQuote) {

    boolean first = true;

    if (delimiter == ' ') {
      delimiter = DEFAULT_DELIMITER;
    }

    StringBuilder sb = new StringBuilder();
    for (String value : values) {
      if (!first) {
        sb.append(delimiter);
      }
      if (customQuote == ' ') {
        sb.append(followCVSformat(value));
      } else {
        sb.append(customQuote).append(followCVSformat(value)).append(customQuote);
      }

      first = false;
    }
    sb.append("\n");

    try {
      w.append(sb.toString());
    } catch (IOException ioe) {
      System.out.println("Exception thrown from CSV Util!"); // TODO: Learn Exceptions and throw a proper Citrus error
    }

  }

}
