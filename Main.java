import java.util.Scanner;

public class Main 
{
    public static void main(String[] args) 
    {
        Scanner scanner = new Scanner(System.in);
        boolean gameOver = false;
        Player player1 = new Player("Hero", 90, 10, 0);
        Goblin goblin1 = new Goblin("Goblin", 30, 5, 20, 3);
        Sikuler sekuler1 = new Sikuler("Siuler", 500);

        System.out.println("Player Stats:");
        System.out.println("Player Name: " + player1.namaPlayer);
        System.out.println("Player Health: " + player1.healthPoint);
        System.out.println("Player Attack Power: " + player1.atkPower);
        System.out.println("Player Experience: " + player1.experiencePoint);
        System.out.println("=========================================");
        
        System.out.println("Enemy Stats:");
        System.out.println("\nEnemy Name: " + goblin1.namaEnemy);
        System.out.println("Enemy Health: " + goblin1.healthPoint);
        System.out.println("Enemy Attack Power: " + goblin1.atkPower);
        System.out.println("Enemy Experience: " + goblin1.experiencePoint);
        System.out.println("\nEnemy Special Move Power: " + goblin1.specialMove);
        System.out.println("=========================================");

        do {
            if (player1.isDead() || goblin1.healthPoint <= 0) 
            {
                gameOver = true;
                System.out.println("Game Over!");
                break;
            }
            System.out.println("Start playing");
            System.out.println("1. Attack");
            System.out.println("2. Heal");
            System.out.println("3. End game...");
            System.out.println("==================");
            System.out.print("Choose: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) 
            {
                case 1:
                    System.out.println("Damage Player " + player1.atkPower);
                    sekuler1.attack(player1);
                    break;
        
                default:
                    break;
            }
        } while (!gameOver);

    }
}
 