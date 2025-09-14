package com.example.ppbo;

public class Continue {
    public static void main(String[] args) 
    {
        
        for(int e = 1; e <=15; e++){
            if (e % 2 == 0) {
                continue;
            }
            System.out.println("Bilangan ganjil : " +e);
        }

    }
}
