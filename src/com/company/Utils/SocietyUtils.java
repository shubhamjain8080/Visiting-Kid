package com.company.Utils;

import com.company.House;
import com.company.Path;
import com.company.Society;

import java.util.Scanner;

public class SocietyUtils {
    public static final String WRONG_INPUT_MESSAGE = "You have Entered Wrong input!";
    public static Society society = new Society();
    private static final Scanner scanner = new Scanner(System.in);

    public static Society fillSocietyWithInput(String input) {
        String[] housesWithDistanceList = input.split(", ");
        for (String housesWithDistance :
                housesWithDistanceList) {
            System.out.println(housesWithDistance);
            String[] housesWithDistanceArray = housesWithDistance.split("");
            String houseFrom = housesWithDistanceArray[0];
            String houseTo = housesWithDistanceArray[1];
            int distance = Integer.parseInt(housesWithDistanceArray[2]);
            if (!houseFrom.equals(houseTo))
                society.addHouses(houseFrom, houseTo, distance);
            else
                System.out.println("Path can not have same source and destination : " + houseFrom);
        }
        society.updatePathList();
        society.printPaths();
        return society;
    }

    public static void getDistanceOfPath() {
        System.out.println("(Enter Path without space, Example - ABC : )");
        String pathName = scanner.next();
        if (pathName.matches("[A-Z][A-Z]+")) {
            Path path = new Path(pathName);
            society.getPathDistance(path);
        }
        else
            System.out.println(WRONG_INPUT_MESSAGE);
    }

    public static void getTwoWayRouteWithNumberOfHops(boolean exact) {
        String housesName = getStartingHouseName();
        String numberOfHops = getNumberOfHops();
        if (housesName.matches("[A-Z]") && numberOfHops.matches("\\d+")){
            House house = new House(housesName);
            society.printTwoWayRouteWithHops(house, Integer.parseInt(numberOfHops), exact);
        }
        else
            System.out.println(WRONG_INPUT_MESSAGE);

    }

    public static void getRouteWithNumberOfHopsBetweenTwoHouses(boolean exact) {
        String firstHouseName = getStartingHouseName();
        String lastHouseName = getLastHouseName();
        String numberOfHops = getNumberOfHops();
        boolean isHousesNameCorrect = firstHouseName.matches("[A-Z]") && lastHouseName.matches("[A-Z]");
        boolean isInputCorrect = isHousesNameCorrect && numberOfHops.matches("\\d+");
        if (isInputCorrect){
            House firstHouse = new House(firstHouseName);
            House lastHouse = new House(lastHouseName);
            int numOfHops = Integer.parseInt(numberOfHops);
            society.getRouteBetweenHousesWithHops(firstHouse, lastHouse, numOfHops, exact);
        }
        else
            System.out.println(WRONG_INPUT_MESSAGE);

    }

    public static void getMinimumDistancePathBetweenTwoHouses() {
        String startingHouse = getStartingHouseName();
        String lastHouse = getLastHouseName();
        if (startingHouse.matches("[A-Z]") && lastHouse.matches("[A-Z]"))
            society.getMinimumDistancePathBetween(new House(startingHouse), new House(lastHouse));
        else
            System.out.println(WRONG_INPUT_MESSAGE);

    }

    public static void getMinimumDistancePathOfTwoWayRoute() {
        String startingHouse = getStartingHouseName();
        if (startingHouse.matches("[A-Z]"))
            society.getMinimumDistanceTwoWayPath(new House(startingHouse));
        else
            System.out.println(WRONG_INPUT_MESSAGE);
    }


    public static void getAllTheRoutesBetweenTwoHouses() {
        String startingHouse = getStartingHouseName();
        String lastHouse = getLastHouseName();
        if (startingHouse.matches("[A-Z]") && lastHouse.matches("[A-Z]"))
            society.getAllTheRoutesBetween(new House(startingHouse), new House(lastHouse));
        else
            System.out.println(WRONG_INPUT_MESSAGE);
    }

    public static void getAllTwoWayRoutesWithMaxDistance() {
        String startingHouseName = getStartingHouseName();
        System.out.println("(Enter Maximum Desired distance of route, Example - 30 :)");
        String distance =  scanner.next();
        if (startingHouseName.matches("[A-Z]") && distance.matches("\\d+")){
            House startingHouse = new House(startingHouseName);
            society.printAllTheTwoWayRoutesWithDistanceLessThan(startingHouse, Integer.parseInt(distance));
        }
    }


    private static String getLastHouseName() {
        System.out.println("(Enter Last House, Example - B :)");
        return scanner.next();
    }


    private static String getStartingHouseName() {
        System.out.println("(Enter Starting House, Example - A :)");
        return scanner.next();
    }

    private static String getNumberOfHops() {
        System.out.println("(Enter number of hops, Example - 2 : )");
        return scanner.next();
    }

}
