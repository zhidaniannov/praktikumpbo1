package com.example.ppbo;

public class Main {
    public static void main(String[] args) {
        // Object disini ya
        // 1. Buat object 1 dengan constructor default
        Music Music1 = new Music();
        Music1.tampilkanInfo();

        // 2. Buat object 2 dengan constructor berparameter
        Music Music2 = new Music("Thinking Out Loud", "Artis A", "Pop");
        Music2.tampilkanInfo(true);

        // 3. Buat object 3 dengan constructor berparameter
        Music Music3 = new Music("The Scientist",  "Artis B", "Alternative Rock");
        Music3.tampilkanInfo(false);
    }
 }




        
    

        