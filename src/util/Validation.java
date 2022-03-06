package util;

public class Validation {

    private Validation() {
    }

    public static int validateInteger(String num, int lowerLimit, int upperLimit, String validateFor) {
        String validIntegers = "";
        if(lowerLimit != Integer.MIN_VALUE) validIntegers = "(" + lowerLimit + " to " + upperLimit + ")";
        if(!validateFor.equals("")) validateFor = "for " + validateFor;
        if(num.equals("")) {
            System.out.println("\nYou have not given any option " + validateFor
                    + ". Please select valid option " + validateFor + validIntegers);
            return -1;
        }
        int number;
        try {
            number = Integer.parseInt(num);
        }
        catch (NumberFormatException e) {
            System.out.println("\nYou Entered Non Integer " + validateFor
                    + "! Please select valid option " + validateFor + validIntegers);
            return -1;
        }
        if(number < lowerLimit || number > upperLimit) {
            System.out.println("\nYou have selected wrong option "
                    + validateFor + ". Please select valid option " + validateFor + validIntegers);
            return -1;
        }
        return number;
    }

    public static int validateInteger(String num, int lowerLimit, int upperLimit) {
        return validateInteger(num,lowerLimit,upperLimit,"");
    }

    public static int validateInteger(String num) {
        return validateInteger(num,Integer.MIN_VALUE,Integer.MAX_VALUE,"");
    }

    public static int validateInteger(String num, String validateFor) {
        return validateInteger(num,Integer.MIN_VALUE,Integer.MAX_VALUE,validateFor);
    }

}
