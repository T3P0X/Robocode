package saxion.Supbot.Robocode;

import robocode.Robot;
import robocode.ScannedRobotEvent;

/**
 * Created by heyim on 22-Mar-17.
 */
public class AdvancedEnemy extends Enemy {

    private double x_axis = 0.0;
    private double y_axis = 0.0;
    private double futureX_axis = 0.0;
    private double futureY_axis = 0.0;

    public AdvancedEnemy(){
    }

    public AdvancedEnemy(boolean locked) {
        super(locked);
        resetEnemy();
    }

    public double getFutureX_axis(long when) {
        return x_axis + Math.sin(Math.toRadians(getHeading())) * getVelocity() * when;
    }

    public double getFutureY_axis(long when) {
        return y_axis + Math.sin(Math.toRadians(getHeading())) * getVelocity() * when;
    }

    public double getX_axis() {
        return x_axis;
    }

    public double getY_axis() {
        return y_axis;
    }

    @Override
    public void resetEnemy() {
        super.resetEnemy();
        x_axis = 0.0;
        y_axis = 0.0;
    }


    public void updateEnemy(ScannedRobotEvent enemy , Robot robot) {
        super.updateEnemy(enemy);
        double absBearingDeg = (robot.getGunHeading() + enemy.getBearing());
        if (absBearingDeg < 0) absBearingDeg += 360;

        System.out.println(x_axis);
        x_axis = robot.getX() + Math.cos(Math.toRadians(absBearingDeg)) * enemy.getDistance();
        y_axis = robot.getY() + Math.cos(Math.toRadians(absBearingDeg)) * enemy.getDistance();
        System.out.println(x_axis);
    }
}
