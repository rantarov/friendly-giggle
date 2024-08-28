import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Input:");
    String inputText = scanner.nextLine().trim();

    try {
      InputCheck constructor = new InputCheck(inputText);
      StringCalculator calculator = new StringCalculator();
      String result = calculator.calculate(constructor.getStr1(), constructor.getOperator(),
          constructor.getSecondArgument());
      System.out.println(result);
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }
}

class InputCheck {

  private String str1;
  private char operator;
  private String secondArgument;

  public InputCheck(String inputText) throws Exception {
    checkInput(inputText);
  }

  private void checkInput(String inputText) throws Exception {
    inputText = inputText.replaceAll("\\s", "");
    if (!inputText.startsWith("\"") || inputText.indexOf("\"", 1) == -1) {
      throw new Exception("Первым аргументом должна быть строка в кавычках");
    }

    int lengthFirstIndex = inputText.indexOf("\"", 1);
    str1 = inputText.substring(1, lengthFirstIndex);
    if (str1.length() > 10) {
      throw new Exception("Строка не должна содержать более 10 символов");
    }

    operator = inputText.charAt(lengthFirstIndex + 1);
    if (operator != '+' && operator != '-' && operator != '*' && operator != '/') {
      throw new Exception("Неподдерживаемая операция");
    }
    secondArgument = inputText.substring(lengthFirstIndex + 2);
  }

  public String getStr1() {
    return str1;
  }

  public char getOperator() {
    return operator;
  }

  public String getSecondArgument() {
    return secondArgument;
  }
}

class StringCalculator {

  public String calculate(String str1, char operator, String secondPart) throws Exception {
    String result = switch (operator) {
      case '+' -> additionString(str1, secondPart);
      case '-' -> subtractStrings(str1, secondPart);
      case '*' -> multiplyString(str1, secondPart);
      case '/' -> divideString(str1, secondPart);
      default -> throw new Exception("Неподдерживаемый оператор");
    };
    return formatResult(result);
  }

  private String additionString(String str1, String secondPart) throws Exception {
    if (!secondPart.startsWith("\"") || !secondPart.endsWith("\"")) {
      throw new Exception("Вторым аргументом должна быть строка в кавычках");
    }
    String str2 = secondPart.substring(1, secondPart.length() - 1);
    if (str2.length() > 10) {
      throw new Exception("Строка не должна содержать более 10 символов");
    }
    return str1 + str2;
  }

  private String subtractStrings(String str1, String secondPart) throws Exception {
    if (!secondPart.startsWith("\"") || !secondPart.endsWith("\"")) {
      throw new Exception("Вторым аргументом должна быть строка в кавычках");
    }
    String str2 = secondPart.substring(1, secondPart.length() - 1);
    return str1.replaceAll(str2, "");
  }

  private String multiplyString(String str1, String secondPart) throws Exception {
    int number = checkNumber(secondPart);
    return str1.repeat(number);
  }

  private String divideString(String str1, String secondPart) throws Exception {
    int number = checkNumber(secondPart);
    int length = str1.length() / number;
    return str1.substring(0, length);
  }

  private int checkNumber(String inputText) throws Exception {
    int number;
    try {
      number = Integer.parseInt(inputText);
      if (number < 1 || number > 10) {
        throw new Exception("Число должно быть от 1 до 10");
      }
    } catch (NumberFormatException e) {
      throw new Exception("Ожидалось целое число");
    }
    return number;
  }

  private String formatResult(String result) {
    if (result.length() > 40) {
      result = result.substring(0, 40) + "...";
    }
    return "\"" + result + "\"";
  }
}