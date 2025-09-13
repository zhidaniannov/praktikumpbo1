package com.example.ppbo;

public class KonversiString {
    public static void main(String[] args) {
        // String ke wrapper/primitif
        String strNumber = "123";
        String strDouble = "45.67";
        String strBoolean = "true";

        // Parse methods
        int num = Integer.parseInt(strNumber);
        double decimal = Double.parseDouble(strDouble);
        boolean flag = Boolean.parseBoolean(strBoolean);

        System.out.println("Parsed Integer: " + num);
        System.out.println("Parsed Double: " + decimal);
        System.out.println("Parsed Boolean: " + flag);

        Integer wrapperNum = Integer.valueOf(strNumber);
        Double wrapperDecimal = Double.valueOf(strDouble);

        System.out.println("Wrapper Integer: " + wrapperNum);
        System.out.println("Wrapper Double: " + wrapperDecimal);

        // Wrapper/primitif ke String
        int value = 789;
        String strValue1 = Integer.toString(value);
        String strValue2 = String.valueOf(value);

        System.out.println("(toString): " + strValue1);
        System.out.println("(valueOf): " + strValue2);

        // Integer methods
        System.out.println("Max Integer: " + Integer.MAX_VALUE);
        System.out.println("Min Integer: " + Integer.MIN_VALUE);

        // Konversi sistem bilangan
        int decimalNumber = 255;
        System.out.println("Binary: " + Integer.toBinaryString(decimalNumber));
        System.out.println("Octal: " + Integer.toOctalString(decimalNumber));
        System.out.println("Hexadecimal: " + Integer.toHexString(decimalNumber));

    }
}
