import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SafeInput {
    public static String getNonZeroLenString(Scanner scanner, String prompt) {
        String temp = "";
        do {
            System.out.printf(prompt);

            temp = scanner.nextLine();
        } while (temp.length() == 0);

        return temp;
    }

    public static int getInt(Scanner scanner, String prompt)
    {
        while (true)
        {
            System.out.printf(prompt);

            String str = scanner.nextLine();

            try
            {
                return Integer.parseInt(str);
            }

            catch (NumberFormatException e)
            {
                System.out.println("Invalid input: " + str);
            }
        }
    }

    public static double getDouble(Scanner scanner, String prompt)
    {
        while (true)
        {
            System.out.printf(prompt);

            String str = scanner.nextLine();

            try
            {
                return Double.parseDouble(str);
            }

            catch (NumberFormatException e)
            {
                System.out.println("Invalid input: " + str);
            }
        }
    }

    public static int getRangedInt(Scanner scanner, String prompt, int lower, int higher)
    {
        while (true)
        {
            System.out.printf(prompt);

            String str = scanner.nextLine();

            try
            {
                int temp = Integer.parseInt(str);

                if (temp < lower || temp > higher)
                {
                    throw new NumberFormatException();
                }

                return temp;
            }

            catch (NumberFormatException e)
            {
                System.out.println("Invalid input: " + str);
            }
        }
    }

    public static double getRangedDouble(Scanner scanner, String prompt, double lower, double higher)
    {
        while (true)
        {
            System.out.printf(prompt);

            String str = scanner.nextLine();

            try
            {
                double temp = Double.parseDouble(str);

                if (temp < lower || temp > higher)
                {
                    throw new NumberFormatException();
                }

                return temp;
            }

            catch (NumberFormatException e)
            {
                System.out.println("Invalid input: " + str);
            }
        }
    }

    public static boolean getYN(Scanner scanner, String prompt)
    {
        String s = "";

        do {
            s = getNonZeroLenString(scanner, prompt);

            if (s.equalsIgnoreCase("y"))
            {
                return true;
            }

            if (s.equalsIgnoreCase("n"))
            {
                return false;
            }

            System.out.println("Invalid input: " + s);
        } while (true);
    }

    public static String getRegEx(Scanner scanner, String prompt, String expr)
    {
        String s = "";

        Pattern p = Pattern.compile(expr);

        do {
            s = getNonZeroLenString(scanner, prompt);

            Matcher m = p.matcher(s);

            if (m.find())
            {
                return s;
            }

            System.out.println("Invalid input: " + s);
        } while (true);
    }

    private static void bar(String message)
    {
        int length = 60 - message.length() % 2;
        for (int i = 0; i < length; ++i)
        {
            System.out.printf("*");
        }
        System.out.println("");
    }

    public static void prettyHeader(String message)
    {
        int length = 60 - message.length() % 2;

        bar(message);

        System.out.printf("***");

        int middle = length - 6;
        int spaces = (middle - message.length()) / 2;

        for (int i = 0; i < spaces; ++i)
        {
            System.out.printf(" ");
        }

        System.out.printf(message);

        for (int i = 0; i < spaces; ++i)
        {
            System.out.printf(" ");
        }

        System.out.println("***");
        bar(message);
    }

    public static void header(Scanner scanner)
    {
        prettyHeader(getNonZeroLenString(scanner, "What is your message: "));
    }
}