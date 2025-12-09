package com.example.ppbo;

public class AutoboxingUnboxing {
    public static void main(String[] args) {
        // Autoboxing -  primitive ke wrapper
        int primitiveInt = 100;
        Integer wrapperInt = primitiveInt; // Autoboxing
        System.out.println("Wrapper Integer: " + wrapperInt);

        // Unboxing - wrapper ke primitive
        Integer anotherWrapperInt = 200;
        int anotherPrimitiveInt = anotherWrapperInt; // Unboxing
        System.out.println("Primitive Integer: " + anotherPrimitiveInt);
    }
}
