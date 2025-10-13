public class Player 
{
    String namaPlayer;
    int healthPoint;
    int atkPower;
    int experiencePoint;

    public Player(String namaPlayer, int healthPoint, int atkPower, int experiencePoint) 
    {
        this.namaPlayer = namaPlayer;
        this.healthPoint = healthPoint;
        this.atkPower = atkPower;
        this.experiencePoint = experiencePoint;
    }

    public int playerAttack()
    {
        return atkPower = 10;
    }

    public void isDead()
    {
        if (healthPoint <= 0)
        {
            System.out.println(namaPlayer + " is dead");
        } else {
            System.out.println(namaPlayer + " is still alive");
        }
    }

}