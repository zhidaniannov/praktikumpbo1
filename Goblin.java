public class Goblin extends Enemy 
{
    int specialMove;

    public Goblin(String namaEnemy, int healthPoint, int atkPower, int experiencePoint, int specialMove) 
    {
        super(namaEnemy, healthPoint, atkPower, experiencePoint);
        this.specialMove += atkPower;
    }

    @Override
    public int enemyAttack()
    {
        return atkPower = 7;
    }

    public int specialMove()
    {
        return specialMove = 3;
    }

    public void isDead()
    {
        if (healthPoint <= 0)
        {
            System.out.println(namaEnemy + " is dead");
        } else {
            System.out.println(namaEnemy + " is still alive");
        }
    }

}