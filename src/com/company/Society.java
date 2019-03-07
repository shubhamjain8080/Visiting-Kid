package com.company;

import java.util.ArrayList;
import java.util.HashMap;

public class Society {
    public static final int MAX_DISTANCE_OF_PATH = 5000;
    public static final String NO_ROUTE_EXIST = "NO ROUTE EXIST!";
    public static final int MAX_NUM_OF_HOPS = 10;
    public HashMap<String, House> houses = new HashMap<>();
    public ArrayList<Path> paths = new ArrayList<>();

    public void addHouses(String houseFrom, String houseTo, int distance) {
        houses.putIfAbsent(houseFrom, new House(houseFrom));
        houses.putIfAbsent(houseTo, new House(houseTo));
        updateDistanceFromOneHouseToAnother(houseFrom, houseTo, distance);
    }

    public void printPaths() {
        for (Path path : paths)
            path.print();
    }


    public void updatePathList() {
        for (int i = 0; i < paths.size(); i++) {
            Path path = paths.get(i);
            for (House houseFrom : houses.values()) {
                for (House houseTo : houseFrom.distanceTo.keySet()) {
                    if (canHouseToBeInsertedToCreateNewPath(path, houseFrom, houseTo)) {
                        ArrayList<House> includedHouses = new ArrayList<>(path.getIncludedHouses());
                        Path newPath = new Path(includedHouses);
                        newPath.addHouseNextToCurrent(houseFrom, houseTo);
                        if (!paths.contains(newPath)) paths.add(newPath);
                    } else if (canHouseFromBeInsertedToCreateNewPath(path, houseFrom, houseTo)) {
                        ArrayList<House> includedHouses = new ArrayList<>(path.getIncludedHouses());
                        Path newPath = new Path(includedHouses);
                        newPath.addHousePrevToCurrent(houseTo, houseFrom);
                        if (!paths.contains(newPath)) paths.add(newPath);
                    }
                }
            }
        }
    }

    public void getPathDistance(Path path) {
        if (paths.contains(path)) {
            int index = paths.indexOf(path);
            path = paths.get(index);
        }
        printPath(path);
    }

    public void getMinimumDistancePathBetween(House startingHouse, House lastHouse) {
        int min = MAX_DISTANCE_OF_PATH;
        Path resultedPath = new Path();
        for (Path path : paths) {
            ArrayList<House> includedHouses = new ArrayList<>(path.getIncludedHouses());
            if (startingHouse.equals(lastHouse)) includedHouses.add(lastHouse);
            Path newlyCreatedPath = new Path(includedHouses);
            boolean pathStartIsStartingHouse = includedHouses.get(0).equals(startingHouse);
            boolean pathEndIsLastHouse = includedHouses.get(includedHouses.size() - 1).equals(lastHouse);
            if (pathStartIsStartingHouse && pathEndIsLastHouse && newlyCreatedPath.getDistance() < min) {
                resultedPath = newlyCreatedPath;
                min = path.getDistance();
            }
        }
        printPath(resultedPath);
    }

    public void getMinimumDistanceTwoWayPath(House house) {
        getMinimumDistancePathBetween(house, house);
    }

    public void getAllTheRoutesBetween(House startingHouse, House lastHouse) {
        ArrayList<Path> resultedPaths = new ArrayList<>();
        for (Path path : paths) {
            ArrayList<House> houses = path.getIncludedHouses();
            boolean pathStartsFromDesiredHouse = houses.get(0).equals(startingHouse);
            boolean pathEndsWithLastHouse = houses.get(houses.size() - 1).equals(lastHouse);
            if (pathStartsFromDesiredHouse && pathEndsWithLastHouse) {
                resultedPaths.add(path);
            }
        }
        printPath(resultedPaths);
    }

    public void printTwoWayRouteWithHops(House house, int numOfHops, boolean exact) {
        ArrayList<Path> resultedPaths = getRouteWithHops(house, house, numOfHops, exact);
        printPath(resultedPaths);
    }

    public void printAllTheTwoWayRoutesWithDistanceLessThan(House house, int distance) {
        ArrayList<Path> originalPaths = getRouteWithHops(house, house, MAX_NUM_OF_HOPS, false);
        ArrayList<Path> resultedPaths = new ArrayList<>();
        for (Path path :
                originalPaths) {
            if (path.getDistance() < distance) {
                resultedPaths.add(path);
            }
        }
        printPath(resultedPaths);
    }

    public void getRouteBetweenHousesWithHops(House firstHouse, House lastHouse, int numOfHops, boolean exact) {
        ArrayList<Path> resultedPaths = getRouteWithHops(firstHouse, lastHouse, numOfHops, exact);
        printPath(resultedPaths);
    }


    private void updateDistanceFromOneHouseToAnother(String houseFromName, String houseToName, int distance) {
        House houseFrom = houses.get(houseFromName);
        House houseTo = houses.get(houseToName);
        houseFrom.updateDistance(houseTo, distance);
        createNewPath(houseFrom, houseTo);
    }

    private void createNewPath(House houseFrom, House houseTo) {
        ArrayList<House> housesOfPath = new ArrayList<>();
        housesOfPath.add(houseFrom);
        housesOfPath.add(houseTo);
        Path pathToBeAdded = new Path(housesOfPath);
        if (!paths.contains(pathToBeAdded)) paths.add(pathToBeAdded);
    }

    private boolean canHouseFromBeInsertedToCreateNewPath(Path oldPath, House houseFrom, House houseTo) {
        boolean doesOldPathHaveHouseToAndNotHouseFrom = oldPath.hasHouse(houseTo) && !oldPath.hasHouse(houseFrom);
        return doesOldPathHaveHouseToAndNotHouseFrom && oldPath.canHouseBeInsertedBefore(houseTo, houseFrom);
    }

    private boolean canHouseToBeInsertedToCreateNewPath(Path oldPath, House houseFrom, House houseTo) {
        boolean doesOldPathHaveHouseFromAndNotHouseTo = oldPath.hasHouse(houseFrom) && !oldPath.hasHouse(houseTo);
        return doesOldPathHaveHouseFromAndNotHouseTo && oldPath.canHouseBeInsertedAfter(houseFrom, houseTo);
    }

    private void printPath(Path resultedPath) {
        if (resultedPath.getIncludedHouses() != null && resultedPath.getDistance() < MAX_DISTANCE_OF_PATH) {
            resultedPath.print();
        } else {
            System.out.println(NO_ROUTE_EXIST);
        }
    }

    private boolean isOfDesiredLength(int numOfHops, boolean exact, ArrayList<House> includedHouses) {
        return (exact) ? includedHouses.size() - 1 == numOfHops
                : (includedHouses.size() - 1 <= numOfHops);
    }

    private ArrayList<Path> getRouteWithHops(House firstHouse, House lastHouse, int numOfHops, boolean exact) {
        ArrayList<Path> resultedPaths = new ArrayList<>();
        ArrayList<Path> originalPaths = new ArrayList<>(paths);
        for (int i = 0; i <originalPaths.size() ; i++) {
            Path path = originalPaths.get(i);
            if (path.isFirstHouse(firstHouse)) {
                maxThreeNodesPath(lastHouse, numOfHops, exact, path, resultedPaths, originalPaths);
                minFourNodesPaths(lastHouse, numOfHops, exact, path, resultedPaths, originalPaths);
            }
        }
        return resultedPaths;
    }

    private void maxThreeNodesPath(House lastHouse, int numOfHops, boolean exact, Path path,
                                   ArrayList<Path> resultedPaths, ArrayList<Path> originalPaths) {
        ArrayList<House> includedHouses = path.getIncludedHouses();
        if (includedHouses.size() == 2){
            ArrayList<House> newHouseList = new ArrayList<>(includedHouses);
            if (!path.isLastHouse(lastHouse)) newHouseList.add(lastHouse);
            Path pathToBeChecked = new Path(newHouseList);
            boolean isOfDesiredLength = isOfDesiredLength(numOfHops, exact, newHouseList);
            if (isOfDesiredLength && pathToBeChecked.getDistance() < MAX_DISTANCE_OF_PATH){
                if (!resultedPaths.contains(pathToBeChecked)) resultedPaths.add(pathToBeChecked);
                if (!originalPaths.contains(pathToBeChecked)) originalPaths.add(pathToBeChecked);
            }
        }
    }

    private void minFourNodesPaths(House lastHouse, int numOfHops, boolean exact,Path path,
                                   ArrayList<Path> resultedPaths, ArrayList<Path> originalPaths) {
        ArrayList<House> includedHouses = path.getIncludedHouses();
        for (int j = 0; j< originalPaths.size();j++) {
            Path pathToBeAdded = originalPaths.get(j);
            if (pathToBeAdded.isLastHouse(lastHouse)){
                ArrayList<House> newHouseList = new ArrayList<>(includedHouses);
                newHouseList.addAll(pathToBeAdded.getIncludedHouses());
                Path pathToBeChecked = new Path(newHouseList);
                boolean isOfDesiredLength = isOfDesiredLength(numOfHops, exact, newHouseList);
                while (isOfDesiredLength && pathToBeChecked.getDistance() < MAX_DISTANCE_OF_PATH) {
                    ArrayList<House> newlyCreatedList = new ArrayList<>(pathToBeChecked.getIncludedHouses());
                    Path newlyCreatedPath = new Path(newlyCreatedList);
                    if (!resultedPaths.contains(newlyCreatedPath)) resultedPaths.add(newlyCreatedPath);
                    if (!originalPaths.contains(newlyCreatedPath)) originalPaths.add(newlyCreatedPath);
                    newHouseList.addAll(pathToBeAdded.getIncludedHouses());
                    pathToBeChecked = new Path(newHouseList);
                    isOfDesiredLength = isOfDesiredLength(numOfHops, exact, newHouseList);
                }
            }
        }
    }

    private void printPath(ArrayList<Path> resultedPaths) {
        if (resultedPaths.isEmpty()) {
            System.out.println(NO_ROUTE_EXIST);
        } else {
            for (Path path : resultedPaths) {
                printPath(path);
            }
        }
    }
}
