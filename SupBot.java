package saxion.Supbot.Robocode;

import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;

import java.awt.geom.Point2D;
import java.util.ArrayList;


public class SupBot extends AdvancedRobot {
    private double previousEnergy = 100;
    private int movementDirection = 1;
    private int strafeDirection = 1;

    AdvancedEnemy enemy = new AdvancedEnemy();
    ArrayList<AdvancedEnemy> enemies = new ArrayList<>();


    public void run() {

        enemy.resetEnemy();
        setAdjustRadarForRobotTurn(true);
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);

            turnRadarRight(360);
            scan();
            execute();
        }



    public void onScannedRobot(ScannedRobotEvent robot) {

        while (true) {
            if (enemies.size() == 0) {
                enemy = new AdvancedEnemy(false);
                enemies.add(enemy);
            } else if (enemyIsUnique(robot.getName())) {
                enemy = new AdvancedEnemy(false);
                enemies.add(enemy);
            }

            // names added in the console
            for (Enemy enemy1 : enemies) {
                System.out.println(enemy1.getName());
                System.out.println(enemies.size());
            }
            System.out.println("------------");


            double radarTurn = getHeadingRadians() + robot.getBearingRadians() - getRadarHeadingRadians();
            setTurnRadarRightRadians(Utils.normalRelativeAngle(radarTurn));
            turnRight(robot.getBearing() + 90 - 30 * movementDirection);
            double changeInEnergy = previousEnergy - robot.getEnergy();
            setTurnRight(robot.getBearing() + 90);
            execute();




            // strafe ever 10 ticks.
            if (getTime() % 10 == 0) {
                strafeDirection *= -1;
                setAhead(150 * strafeDirection);
            }
            //dodge bullet by checking energy.
            if (changeInEnergy > 0 && changeInEnergy <= 3)
                movementDirection = -movementDirection;
            setAhead((robot.getDistance() / 4 + 40) * movementDirection);
            {
                InfoRobot();

                // Track the energy level
                previousEnergy = robot.getEnergy();
                execute();
            }

        }
    }

    private void InfoRobot() {
        //All info about our Robot
        System.out.println("===== Robot Status =====");
        System.out.println("Name: " + getName());
        System.out.println("Energy: " + getEnergy());
        System.out.println("Body Head: " + getHeading());
        System.out.println("Gun Head: " + getGunHeading());
        System.out.println("Gun Heat: " + getGunHeat());
        System.out.println("Adjust Gun: " + isAdjustGunForRobotTurn());
        System.out.println("Adjust Radar: " + isAdjustRadarForGunTurn());
        System.out.println("Adjust Body: " + isAdjustRadarForRobotTurn());
        System.out.println("X axis: " + getX());
        System.out.println("Y axis: " + getY());
        System.out.println("Enemies Alive:" + getOthers());
        System.out.println("=======================");
    }

    private boolean enemyIsUnique(String name) {
        boolean ok = true;
        for (Enemy enemy : enemies) {
            System.out.println("test loop");
            if (name.equals(enemy.getName())) {
                ok = false;
            }
        }
        return ok;
    }

    public void shoot(){
        if (enemy.none()) {

            return;
        }
        double firePower = Math.min(500 / enemy.getDistance(), 3);
        double bulletSpeed = 20 - firePower * 3;
        long time = (long) (enemy.getDistance() / bulletSpeed);

        double futureX = enemy.getFutureX_axis(time);
        double futureY = enemy.getFutureY_axis(time);
        double absDeg = absoluteBearing(getX(), getY(), futureX, futureY);
        setTurnGunRight(normalizeBearing(absDeg - getGunHeading()));

        setFire((3 / (robot.getDistance() / 100)));

        execute();
    }

    private double normalizeBearing(double angle) {
        while (angle >  180) angle -= 360;
        while (angle < -180) angle += 360;
        return angle;
    }
    private double absoluteBearing(double x1, double y1, double x2, double y2) {
        double xo = x2-x1;
        double yo = y2-y1;
        double hyp = Point2D.distance(x1, y1, x2, y2);
        double arcSin = Math.toDegrees(Math.asin(xo / hyp));
        double bearing = 0;

        if (xo > 0 && yo > 0) {
            bearing = arcSin;
        } else if (xo < 0 && yo > 0) {
            bearing = 360 + arcSin;
        } else if (xo > 0 && yo < 0) {
            bearing = 180 - arcSin;
        } else if (xo < 0 && yo < 0) {
            bearing = 180 - arcSin;
        }

        return bearing;
    }



}



