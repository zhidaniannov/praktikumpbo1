package com.example.ppbo;

public class OperatorLogika {
    public static void main(String[] args) {
        boolean p = true, q = false;
        System.out.println("p && q: " + (p && q)); // AND: false
        System.out.println("p || q: " + (p || q)); // OR: true
        System.out.println("!p: " + (!p)); // NOT: false

        // Short-circuit evaluation
        int a = 5, b = 0;
        boolean result = (b != 0) && (a / b > 2); // Tidak error karena short-circuit
        System.out.println("ResultL " + result);  // False
    }
}