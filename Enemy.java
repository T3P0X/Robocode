package saxion.Supbot.Robocode;
import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;

/**
 * Created by heyim on 15-Mar-17.
 */
public class Enemy extends AdvancedRobot{
    private boolean Locked;
    private String name;
    private double bearing,distance,heading,velocity;

    public Enemy() {
    }


    public Enemy(boolean locked){
        Locked = locked;
        resetEnemy();
    }

    public boolean none(){
        if (name.equals("")){
            return true;
        }
        else{
            return false;
        }
    }
    public void updateEnemy( ScannedRobotEvent enemy){

        bearing = enemy.getBearing();
        distance = enemy.getDistance();
        heading = enemy.getHeading();
        velocity = enemy.getVelocity();
        name = enemy.getName();

    }


    public void resetEnemy(){
        bearing = 0.0;
        distance = 0.0;
        heading = 0.0;
        velocity = 0.0;
        name = "";
    }

    public boolean isLocked() {
        return Locked;
    }

    public String getName() {
        return name;
    }

    public double getBearing() {
        return bearing;
    }

    public double getDistance() {
        return distance;
    }

    public double getHeading() {
        return heading;
    }

    public double getVelocity() {
        return velocity;
    }
}
