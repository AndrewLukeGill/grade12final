import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.lang.Math;
import java.util.ArrayList;
import java.awt.Color;
public class Game extends JComponentWithEvents
{
    int xLoc = 230;
    int yLoc = 750;
    int x = 0;
    int hp;

    ArrayList <Projectile> fired = new ArrayList <Projectile>();
    ArrayList <Projectile> enemyFire = new ArrayList <Projectile>();
    ArrayList <Enemy> enemy = new ArrayList <Enemy>();
    Score track = new Score();
    String points = track.score +"";

    public static void main(String [] args)
    {
        launch(500,900);
    }

    public void paint(Graphics2D g)
    {
        for(int i = 0; i < enemy.size(); i++)
        {            
            g.fillRect(enemy.get(i).getXLocation(), enemy.get(i).getYLocation(), 20, 30);
        }

        for(int i = 0; i < fired.size(); i++)
        {
            g.setColor(Color.BLUE);
            g.fillRect(fired.get(i).getXLocation(), fired.get(i).getYLocation(), 5, 15);
            g.setColor(Color.BLACK);
        }

        for(int i = 0; i < enemyFire.size(); i++)
        {
            g.setColor(Color.GREEN);
            g.fillRect(enemyFire.get(i).getXLocation(), enemyFire.get(i).getYLocation() + 30, 5, 15);
            g.setColor(Color.BLACK);
        }

        for(int p = 0; p < fired.size(); p++)
        {
            for(int e = 0; e < enemy.size(); e++)
            {
                if(fired.get(p).getYLocation() >= enemy.get(e).getYLocation() && fired.get(p).getYLocation() <= (enemy.get(e).getYLocation() + 30))
                {
                    if(fired.get(p).getXLocation() >= enemy.get(e).getXLocation() && fired.get(p).getXLocation() <= (enemy.get(e).getXLocation() + 20))
                    {
                        g.clearRect(enemy.get(e).getXLocation(), enemy.get(e).getYLocation(), 20, 30);
                        enemy.remove(e);
                        this.increaseScore();
                        fired.get(p).setYLocation(fired.get(p).getYLocation() - 500);
                    }
                }
            }
        }

        for(int i = 0; i < enemyFire.size(); i++)
        {
            if(enemyFire.get(i).getXLocation() >= xLoc && enemyFire.get(i).getXLocation() <= (xLoc + 40))
            {
                if(enemyFire.get(i).getYLocation() == (yLoc - 30))
                { 
                    enemyFire.get(i).setYLocation(enemyFire.get(i).getYLocation() + 500);
                    this.decreaseHealth();
                }
            }
        }

        if (track.health == 3)
        {
            g.setColor(Color.RED);
            g.fillOval(400, 10, 25, 25);
            g.fillOval(435, 10, 25, 25);
            g.fillOval(470, 10, 25, 25);
            g.setColor(Color.BLACK);
            g.fillRect(xLoc, yLoc, 40, 60);
            g.fillOval(xLoc - 1, yLoc - 30, 41, 60);
        }

        if (track.health == 2)
        {
            g.setColor(Color.RED);
            g.fillOval(435, 10, 25, 25);
            g.fillOval(470, 10, 25, 25);
            g.setColor(Color.BLACK);
            g.fillRect(xLoc, yLoc, 40, 60);
            g.fillOval(xLoc - 1, yLoc - 30, 41, 60);
        }

        if (track.health == 1)
        {
            g.setColor(Color.RED);
            g.fillOval(470, 10, 25, 25);
            g.setColor(Color.BLACK);
            g.fillRect(xLoc, yLoc, 40, 60);
            g.fillOval(xLoc - 1, yLoc - 30, 41, 60);
        }

        if (track.health == 0)
        {
            g.clearRect(xLoc, yLoc, 40, 60);
            g.clearRect(xLoc - 1, yLoc - 30, 41, 60);
        }

        g.drawString(points, 10, 10);
    }

    public void keyPressed(char key) 
    {
        if (key == 'w' && yLoc > 600)
        {
            yLoc -= 10;
        }
        if (key == 's' && yLoc < 840)
        {
            yLoc += 10;
        }
        if (key == 'a' && xLoc > 5)
        {
            xLoc -= 10;
        }
        if (key == 'd' && xLoc < 460)
        {
            xLoc += 10;
        }
        if (key == SPACE)
        {
            fired.add(new Projectile());
            fired.get(fired.size() - 1).setXLocation(xLoc + 18);
            fired.get(fired.size() - 1).setYLocation(yLoc - 30);
        }
    }

    public void timerFired()
    {
        setTimerDelay(5);

        for(int i = 0; i < fired.size(); i ++)
        {
            fired.get(i).setYLocation(fired.get(i).getYLocation() - 2);
        }
        for(int i = 0; i < enemyFire.size(); i ++)
        {
            enemyFire.get(i).setYLocation(enemyFire.get(i).getYLocation() + 2);
        }

        this.spawnEnemy(); 
        if (x % 400 == 0)
        {
            this.enemyShoot();
            x += 2;
        } else 
        {
            x += 2;
        }

        hp = track.health;
        points = track.score +"";

    }

    public void spawnEnemy()
    {
        if(enemy.size() == 0 && track.score <= 3)
        {
            enemy.add(new Enemy());
            enemy.get(0).setXLocation(100);
            enemy.get(0).setYLocation(50);

            enemy.add(new Enemy());
            enemy.get(1).setXLocation(240);
            enemy.get(1).setYLocation(50);

            enemy.add(new Enemy());
            enemy.get(2).setXLocation(380);
            enemy.get(2).setYLocation(50);
        }
        if(enemy.size() == 0 && track.score > 4)
        {
            if (track.score <= 10)
            {
                enemy.add(new Enemy());
                enemy.get(0).setXLocation(50);
                enemy.get(0).setYLocation(50);

                enemy.add(new Enemy());
                enemy.get(1).setXLocation(150);
                enemy.get(1).setYLocation(50);

                enemy.add(new Enemy());
                enemy.get(2).setXLocation(250);
                enemy.get(2).setYLocation(50);

                enemy.add(new Enemy());
                enemy.get(3).setXLocation(350);
                enemy.get(3).setYLocation(50);

                enemy.add(new Enemy());
                enemy.get(4).setXLocation(450);
                enemy.get(4).setYLocation(50);
            } else if (track.score > 10 && track.score <= 20)
            {
                enemy.add(new Enemy());
                enemy.get(0).setXLocation(50);
                enemy.get(0).setYLocation(50);

                enemy.add(new Enemy());
                enemy.get(1).setXLocation(150);
                enemy.get(1).setYLocation(50);

                enemy.add(new Enemy());
                enemy.get(2).setXLocation(250);
                enemy.get(2).setYLocation(50);

                enemy.add(new Enemy());
                enemy.get(3).setXLocation(350);
                enemy.get(3).setYLocation(50);

                enemy.add(new Enemy());
                enemy.get(4).setXLocation(450);
                enemy.get(4).setYLocation(50);
            }            
        }
    }

    public void enemyShoot()
    {
        for(int i = 0; i < enemy.size(); i++)
        {
            enemyFire.add(new Projectile());
            enemyFire.get(enemyFire.size() - 1).setXLocation(enemy.get(i).getXLocation() + 7);
            enemyFire.get(enemyFire.size() - 1).setYLocation(enemy.get(i).getYLocation());
        }
    }

    public void decreaseHealth()
    {
        track.decreaseHealth();
    }

    public void increaseScore()
    {
        track.increaseScore();
    }
}