package com.company;

import java.util.ArrayList;
import java.util.Objects;

public class Path {
    private ArrayList<House> includedHouses;
    private int distance = 0;

    public Path(ArrayList<House> includedHouses) {
        this.includedHouses = includedHouses;
        updateDistance();
    }
    public Path(String includedHousesNames){
        this.includedHouses = new ArrayList<>();
        for (int i = 0; i < includedHousesNames.length(); i++) {
            House house = new House(String.valueOf(includedHousesNames.charAt(i)));
            this.includedHouses.add(house);
        }
        updateDistance();
    }

    public Path() {
    }

    public ArrayList<House> getIncludedHouses() {
        return includedHouses;
    }

    public int getDistance() {
        return distance;
    }

    public boolean hasHouse(House house){
        return includedHouses.contains(house);
    }
    public boolean isLastHouse(House house){
        return (includedHouses.lastIndexOf(house) == includedHouses.size() - 1);
    }
    public boolean isFirstHouse(House house){
        return (includedHouses.indexOf(house) == 0);
    }
    public House getNextHouseOf(House currentHouse){
        int indexOfCurrentHouse = includedHouses.indexOf(currentHouse);
        return includedHouses.get(indexOfCurrentHouse + 1);
    }

    public House getPrevHouseOf(House currentHouse){
        int indexOfCurrentHouse = includedHouses.indexOf(currentHouse);
        return includedHouses.get(indexOfCurrentHouse - 1);
    }

    public boolean canHouseBeInsertedBefore(House currentHouse, House houseToBeInserted){
        if (includedHouses.contains(currentHouse)){
            if (isFirstHouse(currentHouse)) return true;
            else if (getPrevHouseOf(currentHouse).hasPathTo(houseToBeInserted)) return true;
            else return false;
        }
        else return false;
    }

    public boolean canHouseBeInsertedAfter(House currentHouse, House houseToBeInserted){
        if (includedHouses.contains(currentHouse)){
            if (isLastHouse(currentHouse)) return true;
            else if (houseToBeInserted.hasPathTo(getNextHouseOf(currentHouse))) return true;
            else return false;
        }
        else return false;
    }

    public void addHouseNextToCurrent(House currentHouse, House houseToBeAdded){
        includedHouses.add(includedHouses.indexOf(currentHouse) + 1, houseToBeAdded);
        updateDistance();
    }
    public void addHousePrevToCurrent(House currentHouse, House houseToBeAdded){
        includedHouses.add(includedHouses.indexOf(currentHouse), houseToBeAdded);
        updateDistance();
    }

    public void print() {
        printAndGetPathName();
        System.out.println(": " + distance);
    }

    public String printAndGetPathName(){
        String pathName = "";
        for (House house : includedHouses) {
            pathName += house.getName();
            System.out.print(house.getName());
        }
        return pathName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Path path = (Path) o;
        return includedHouses.equals(path.includedHouses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(includedHouses);
    }

    private void updateDistance() {
        int distance = 0;
        for (int i = 0; i < includedHouses.size() -1 ; i++) {
            House prevHouse = includedHouses.get(i);
            House nextHouse = includedHouses.get(i + 1);
            distance += prevHouse.hasPathTo(nextHouse) ?
                    prevHouse.getDistanceTo(nextHouse) : Society.MAX_DISTANCE_OF_PATH;
        }
        this.distance =  distance;
    }

}
