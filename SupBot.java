package saxion.Supbot.Robocode;

import robocode.AdvancedRobot;
import robocode.RobotDeathEvent;
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

        turnRadarRight(360);
        while (true) {
            turnRadarRight(360);
            scan();

            execute();

        }
        }

    public void onRobotDeath(RobotDeathEvent robot) {
        // see if the robot we were tracking died
        if (robot.getName().equals(enemy.getName())) {
            enemy.resetEnemy();
        }
    }

    public void onScannedRobot(ScannedRobotEvent robot) {
//        double moveAmount = Math.max(getBattleFieldWidth(), getBattleFieldHeight());
//        boolean peek = false;
        if ( enemy.none() || robot.getDistance() < enemy.getDistance() - 70 ||
                robot.getName().equals(enemy.getName())) {

            // track him using the NEW update method
            enemy.updateEnemy(robot , this);
        }

        double radarTurn = getHeadingRadians() + robot.getBearingRadians() - getRadarHeadingRadians();

        setTurnRadarRightRadians(Utils.normalRelativeAngle(radarTurn));

        // Stay at right angles to the opponent
        turnRight(robot.getBearing() + 90 - 30 * movementDirection);

        // If the bot has small energy drop,
        // assume it fired
        double changeInEnergy = previousEnergy - robot.getEnergy();

        setTurnRight(robot.getBearing() + 90);
//        System.out.println(robot.getName());
//        if (robot.getName().equals("sample.Crazy (1)")) {
//            System.out.println("WOOOHOOW!");
//        }
//        else if (robot.getName().equals("sample.Crazy (2)")){
//            System.out.println("WOOOHOOW2!");
//        }

        // strafe ever 10 ticks.
        if (getTime() % 10 == 0) {
            strafeDirection *= -1;
            setAhead(150 * strafeDirection);
        }
        //dodge bullet by checking energy.
        if(changeInEnergy >0&& changeInEnergy <=3)
            movementDirection = -movementDirection;
        if (robot.getDistance() < 100)
        {
            back(100);
        }
        setAhead((robot.getDistance() / 4 + 40) * movementDirection);
        shoot();


            // Track the energy level
            previousEnergy = robot.getEnergy();
            execute();
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
        double turn = getHeading() - getGunHeading() + enemy.getBearing();
        // normalize the turn to take the shortest path there
        setTurnGunRight(normalizeBearing(turn));
        // shoot!
        setFire(Math.min(400 / enemy.getDistance(), 3));
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



