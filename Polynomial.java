import java.security.Policy;
import java.util.Scanner;

public class Polynomial
{
    // Fields
    private String[][] polynomialArray;
    private int degree;
    int index_Array;

    // Constructor
    public Polynomial()
    {
        polynomialArray = new String[100][2];
        degree = -1;
        index_Array = 0;
    }

    public void insert(String in)
    {
        String a = "";
        String b = "";
        int index = 0;
        boolean flag = false;
        boolean flag2;
        int i, j;
        for(i = 0 ; i < in.length() ; i++)
        {
            flag = false;
            char ch = in.charAt(i);
            if( ch == 'x')
            {
                b = "1";
                flag = true;
                flag2 = false;
                if(index == i)
                    a = "1";
                else
                    a = in.substring(index, i);
                for(j = i ; j < in.length() ; j++)
                {
                    char ch2 = in.charAt(j);
                    if(ch2 == '^')
                    {
                        flag2 = true;
                        index = j + 1;
                    }

                    if(ch2 == '+' || ch2 == '-')
                    {
                        break;
                    }
                }
                if(flag2)
                    b = in.substring(index, j);
                index = j;
                i = j;
                polynomialArray[index_Array][0] = a;
                polynomialArray[index_Array][1] = b;
                index_Array++;
            }
            else if ( ch == '+' || ch == '-')
            {
                a = in.substring(index, i);
                b = "0";
                polynomialArray[index_Array][0] = a;
                polynomialArray[index_Array][1] = b;
                index_Array++;
                index = i;
            }
        }
        if(flag == false)
        {
            a = in.substring(index, i);
            b = "0";
            polynomialArray[index_Array][0] = a;
            polynomialArray[index_Array][1] = b;
            index_Array++;
        }

        for(i = 0 ; i < index_Array ; i++)
        {
            for(j = i + 1 ; j < index_Array ; j++)
            {
                if(Integer.parseInt(polynomialArray[i][1]) < Integer.parseInt(polynomialArray[j][1]))
                {
                    String temp1 = polynomialArray[i][0];
                    String temp2 = polynomialArray[i][1];
                    polynomialArray[i][0] = polynomialArray[j][0];
                    polynomialArray[i][1] = polynomialArray[j][1];
                    polynomialArray[j][0] = temp1;
                    polynomialArray[j][1] = temp2;
                }
            }
        }
        degree = Integer.parseInt(polynomialArray[0][1]);
    }

    public String differentiate()
    {
        String dif = "";
        for(int i = 0 ; i < index_Array ; i++)
        {
            int a = Integer.parseInt(polynomialArray[i][0]);
            int b = Integer.parseInt(polynomialArray[i][1]);

            if(b > 0)
            {
                if (a >= 0)
                {
                    if((b - 1) > 0)
                        dif += "+" + a * b + "x^" + (b - 1);
                    else
                        dif += "+" + a * b;
                }
                else
                {
                    if((b - 1) > 0)
                        dif += a * b + "x^" + (b - 1);
                    else
                        dif += a * b;
                }
            }
        }
        return dif;
    }

    public String integrate()
    {
        String integ = "";
        for(int i = 0 ; i < index_Array ; i++)
        {
            int a = Integer.parseInt(polynomialArray[i][0]);
            int b = Integer.parseInt(polynomialArray[i][1]);
            if((double)(a/(b+1)) > 0)
                integ += "+";
            else
                integ += "-";
            if((b + 1) > 1)
                integ += "(" + Math.abs(a) + "/" + Math.abs(b + 1) + ")" + "x^" + (b + 1);
            else
                integ += "(" + Math.abs(a) + "/" + Math.abs(b + 1) + ")" + "x";
        }
        return integ + "+C";
    }

    public double calculate(double x)
    {
        double cal = 0;
        for(int i = 0 ; i < index_Array ; i++)
        {
            int a = Integer.parseInt(polynomialArray[i][0]);
            int b = Integer.parseInt(polynomialArray[i][1]);
            cal += a * Math.pow(x, b);
        }
        return cal;
    }
    public static String multiply(Polynomial p1, Polynomial p2)
    {
        String mul = "";
        for(int i = 0 ; i < p1.index_Array ; i++)
        {
            int a1 = Integer.parseInt(p1.polynomialArray[i][0]);
            int b1 = Integer.parseInt(p1.polynomialArray[i][1]);
            for(int j = 0 ; j < p2.index_Array ; j++)
            {
                int a2 = Integer.parseInt(p2.polynomialArray[j][0]);
                int b2 = Integer.parseInt(p2.polynomialArray[j][1]);
                if((a1 * a2) > 0)
                    mul += "+";
                mul += (a1 * a2) + "x^" + (b1 + b2);
            }
        }
        return mul;
    }
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        Scanner input2 = new Scanner(System.in);
        int request;
        String data;
        String data2;
        double x;
        System.out.print("Your Polynomial Expression = ? ");
        data = input.nextLine();
        Polynomial p = new Polynomial();
        Polynomial p2 = new Polynomial();
        p.insert(data);
        do
        {
            System.out.println("1) Differentiate");
            System.out.println("2) Integrate");
            System.out.println("3) Multiply");
            System.out.println("4) Calculate P(x)");
            System.out.println("5) Exit");
            System.out.print("Please Enter Your Option ? ");

            request = input.nextInt();
            switch (request)
            {
                case 1:
                    System.out.println(p.differentiate());
                    break;
                case 2:
                    System.out.println(p.integrate());
                    break;
                case 3:
                    System.out.print("Your Second Polynomial Expression = ? ");
                    data2 = input2.nextLine();
                    p2.insert(data2);
                    System.out.println(multiply(p, p2));
                    break;
                case 4:
                    System.out.print("x0 = ? ");
                    x = input.nextInt();
                    System.out.println(p.calculate(x));
                    break;
                case 5:
                    System.exit(0);
                    break;
                default:
                    System.out.print("\nError : Please Enter Number Between 1 To 5!");
                    break;
            }
        } while (true);
    }
}