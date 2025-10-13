public class Enemy 
{
    String namaEnemy;
    int healthPoint;
    int atkPower;
    int experiencePoint;

    public Enemy(String namaEnemy, int healthPoint, int atkPower, int experiencePoint) 
    {
        this.namaEnemy = namaEnemy;
        this.healthPoint = healthPoint;
        this.atkPower = atkPower;
        this.experiencePoint = experiencePoint;
    }

    public int enemyAttack()
    {
        return atkPower = 5;
    }

}