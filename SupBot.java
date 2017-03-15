
package saxion;
import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;

import java.util.ArrayList;


public class SupBot extends AdvancedRobot {
    double previousEnergy = 100;
    int movementDirection = 1;
    int strafeDirection = 1;

    ArrayList<Enemy> enemies = new ArrayList<>();
    Enemy enemy = new Enemy();
    public void run() {
        while (true) {
            setAdjustRadarForRobotTurn(true);
            setAdjustGunForRobotTurn(true);
            setAdjustRadarForGunTurn(true);
            turnRadarRight(360);
            scan();

            execute();
        }
    }

    public void onScannedRobot(ScannedRobotEvent robot) {
//        double moveAmount = Math.max(getBattleFieldWidth(), getBattleFieldHeight());
//        boolean peek = false;


        double radarTurn = getHeadingRadians() + robot.getBearingRadians() - getRadarHeadingRadians();

        setTurnRadarRightRadians(Utils.normalRelativeAngle(radarTurn));

        // Stay at right angles to the opponent
        turnRight(robot.getBearing() + 90 - 30 * movementDirection);

        // If the bot has small energy drop,
        // assume it fired
        double changeInEnergy = previousEnergy - robot.getEnergy();

        setTurnRight(robot.getBearing() + 90);
        System.out.println(robot.getName());
        if (robot.getName().equals("sample.Crazy (1)")) {
            System.out.println("WOOOHOOW!");
        }
        else if (robot.getName().equals("sample.Crazy (2)")){
            System.out.println("WOOOHOOW2!");
        }

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
        setTurnGunRight(getHeading() - getGunHeading() + robot.getBearing());
        setFire(3);
        execute();

        {

            // Track the energy level
            previousEnergy = robot.getEnergy();
            execute();
        }


    }


}



