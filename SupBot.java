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

    public void run() {

        setAdjustRadarForRobotTurn(true);
        setAdjustRadarForGunTurn(true);
        setAdjustGunForRobotTurn(true);

        turnRadarRight(360);
        while (true) {
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

    }




