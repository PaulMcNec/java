

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.out;

public class Main {

    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        out.println("Что считаем?:");
        String stringExpression = in.nextLine().replaceAll(" ", "").toUpperCase();

        String regex = "^((([1-9]|10)[\\-+*/]([1-9]|10))|((((((I|II|III)|IV)|V)|V(I{0,3}))|IX|X)[\\-+*/](((((I|II|III)|IV)|V)|V(I{0,3}))|IX|X)))$";
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(stringExpression);

        if (matcher.find()){

            String[] operands = stringExpression.split("[\\-+*/]");
            String operator = stringExpression.replaceAll("[0-9IVX]","");

            String regexRoma = "^(((((I|II|III)|IV)|V)|V(I{0,3}))|IX|X)$";
            final Pattern patternRoma = Pattern.compile(regexRoma);
            final Matcher matcherRoma = patternRoma.matcher(operands[0]);
            boolean isRoman = matcherRoma.find();

            int numb1 = (isRoman) ? romanToArabic(operands[0]) : Integer.parseInt(operands[0]);
            int numb2 = (isRoman) ? romanToArabic(operands[1]) : Integer.parseInt(operands[1]);

            int resultCalc = switch (operator) {
                case ("+") -> numb1 + numb2;
                case ("-") -> numb1 - numb2;
                case ("*") -> numb1 * numb2;
                case ("/") -> (numb2 != 0) ? numb1 / numb2 : 0;
                default -> 0;
            };
            String answer = Integer.toString(resultCalc);

            if (isRoman) {
                if (resultCalc > 0) {
                    answer = arabicToRoman(resultCalc);
                } else throw new Exception("Ошибка вычисления!");
            }

            out.println("Ответ: " + answer); // выведем результат расчета хотя по условию задания это не требуется

        } else throw new Exception("Ошибка в выражении!");

    }

    private static int romanToArabic(String numberRoman) {

        int numberArabic = 0;

        String c1 = "I", c5 = "V", c10 = "X", c50 = "L", c100 ="C", c500 = "D", c1000 = "M";

        int numOfChar = numberRoman.length()-1;

        for (int i = 0; i <= numOfChar; i++) {
            String c0 = numberRoman.substring(i,i+1);
            String c01 = (i < numOfChar) ? numberRoman.substring(i+1,i+2) : "";

            if (c0.equals(c1000)) {
                numberArabic = numberArabic + 1000;
            }
            else if (c0.equals(c500)) {
                numberArabic = numberArabic + 500;
            }
            else if (c0.equals(c100)) {
                if ((i < numOfChar) & ((c01.equals(c500)) || (c01.equals(c1000)))) {
                    numberArabic = numberArabic - 100;
                }
                else {
                    numberArabic = numberArabic + 100;
                }
            }
            else if (c0.equals(c50)) {
                numberArabic = numberArabic + 50;
            }
            else if (c0.equals(c10)) {
                if ((i < numOfChar) & ((c01.equals(c50)) || (c01.equals(c100)))) {
                    numberArabic = numberArabic - 10;
                }
                else {
                    numberArabic = numberArabic + 10;
                }
            }
    		else if (c0.equals(c5)) {
                    numberArabic = numberArabic + 5;
            }
            else if (c0.equals(c1)) {
                if ((i < numOfChar) & ((c01.equals(c5)) || (c01.equals(c10)))) {
                    numberArabic = numberArabic - 1;
                }
                else {
                    numberArabic = numberArabic + 1;
                }
            }
        }

        return numberArabic;
    }
    private static String arabicToRoman(int intArabic) {
        String numberRoman = "";
        String c1 = "I", c5 = "V", c10 = "X", c50 = "L", c100 ="C", c500 = "D", c1000 = "M";
        String numberArabic = "000" + intArabic;
        numberArabic = numberArabic.substring(numberArabic.length()-3);
        String c_001 = numberArabic.substring(2,3);
        String c_010 = numberArabic.substring(1,2);
        String c_100 = numberArabic.substring(0,1);

        numberRoman = numberRoman + intToRoman(c_100, c100, c500, c1000);
        numberRoman = numberRoman + intToRoman(c_010, c10, c50, c100);
        numberRoman = numberRoman + intToRoman(c_001, c1, c5, c10);

        return numberRoman;
    }
    private static String intToRoman(String intNum, String c_001, String c_010, String c_100) {
        return switch (intNum) {
            case ("1") -> c_001;
            case ("2") -> c_001 + c_001;
            case ("3") -> c_001 + c_001 + c_001;
            case ("4") -> c_001 + c_010;
            case ("5") -> c_010;
            case ("6") -> c_010 + c_001;
            case ("7") -> c_010 + c_001 + c_001;
            case ("8") -> c_010 + c_001 + c_001 + c_001;
            case ("9") -> c_001 + c_100;
            default -> "";
        };
    }
}

