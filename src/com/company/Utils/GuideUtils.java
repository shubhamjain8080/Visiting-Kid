package com.company.Utils;

import java.util.Scanner;

public class GuideUtils {
    private static final Scanner scanner = new Scanner(System.in);

    public static void displayOptions(){
        boolean continued = true;
        while (continued){
            availableOptions();
            System.out.println("If you wish to continue please type 1");
            String inputForContinuation = scanner.next();
            continued = (inputForContinuation.equals("1")) ? true : false;
        }
        System.out.println("Bye!");
    }

    private static void availableOptions() {
        System.out.println("1. Find the distance of the route");
        System.out.println("2. Find a two way route with maximum Desired number of hops");
        System.out.println("3. Find a two way route with exact number of hops");
        System.out.println("4. Find a route between two houses with maximum Desired number of hops");
        System.out.println("5. Find a route between two houses with exact number of hops");
        System.out.println("6. Find Minimum Distance between two Houses");
        System.out.println("7. Find Minimum Distance of Two way route");
        System.out.println("8. Find All the Routes between two Houses");
        System.out.println("9. Find All the two way Routes with maximum desired distance");
        String inputForOptions = scanner.next();
        completeIfInputIsCorrect(inputForOptions);
    }

    private static void completeIfInputIsCorrect(String inputForOptions) {
        if (inputForOptions.matches("\\d+"))
            completeTheOperation(Integer.parseInt(inputForOptions));
        else
            System.out.println("You have Entered wrong option, please Enter a number!");
    }

    private static void completeTheOperation(int inputForOptions) {
        switch (inputForOptions){
            case 1: SocietyUtils.getDistanceOfPath();
                break;
            case 2: SocietyUtils.getTwoWayRouteWithNumberOfHops(false);
                break;
            case 3: SocietyUtils.getTwoWayRouteWithNumberOfHops(true);
                break;
            case 4: SocietyUtils.getRouteWithNumberOfHopsBetweenTwoHouses(false);
                break;
            case 5: SocietyUtils.getRouteWithNumberOfHopsBetweenTwoHouses(true);
                break;
            case 6: SocietyUtils.getMinimumDistancePathBetweenTwoHouses();
                break;
            case 7: SocietyUtils.getMinimumDistancePathOfTwoWayRoute();
                break;
            case 8: SocietyUtils.getAllTheRoutesBetweenTwoHouses();
                break;
            case 9: SocietyUtils.getAllTwoWayRoutesWithMaxDistance();
                break;
            default:System.out.println("Option does not exist!");
        }
    }

}
