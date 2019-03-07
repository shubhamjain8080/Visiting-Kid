package com.company;

import com.company.Utils.GuideUtils;
import com.company.Utils.SocietyUtils;

import java.util.Scanner;

public class Main {

    public static final String WRONG_INPUT_MESSAGE = "You have Entered wrong input, please rerun the program again and Enter right input";
    static Society society = new Society();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the house name with distance, Example: AB5, CB3 ");
        String input = scanner.nextLine();

        String inputPattern = "(([A-Z][A-Z]\\d,\\s)*)([A-Z][A-Z]\\d)\\s*";
        if (input.matches(inputPattern)) {
            society = SocietyUtils.fillSocietyWithInput(input);
            GuideUtils.displayOptions();
        }
        else {
            System.out.println(WRONG_INPUT_MESSAGE);
        }
    }
}
