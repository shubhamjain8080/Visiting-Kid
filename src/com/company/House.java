package com.company;

import java.util.HashMap;
import java.util.Objects;

public class House {
    private String name;
    public HashMap<House, Integer> distanceTo;

    public House(String name) {
        this.name = name;
        this.distanceTo = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public int getDistanceTo(House nextHouse) {
        return distanceTo.get(nextHouse);
    }

    public void updateDistance(House houseTo, int distance) {
        distanceTo.put(houseTo, distance);
    }

    public boolean hasPathTo(House house){
        return distanceTo.containsKey(house);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        House house = (House) o;
        return name.equals(house.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
