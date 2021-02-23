package Utilites;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelperMethods {

    public static String emptyCustomerTextFieldValidator(
            String name,
            String address,
            String postalCode,
            String phone,
            String firstLevelSelection,
            String emptyValidator) {

        if (name.equals("")) {
            emptyValidator += "\nThe Customer Name field cannot be empty!";
        }

        if (address.equals("")) {
            emptyValidator += "\nThe Address field cannot be empty!";
        }

        if (postalCode.equals("")) {
            emptyValidator += "\nThe Postal Code field cannot be empty!";
        }

        if (phone.equals("")) {
            emptyValidator += "\nThe Phone field cannot be empty!";
        }

        if (firstLevelSelection.equals(null)) {
            emptyValidator += "\nA First Level Division must be selected!";
        }

        return emptyValidator;
    }

    public static String customerTextFieldValidator(
            String postalCode,
            String phone,
            String countryName,
            String textValidator) {

        if (countryName == "U.S") {
            String usPostalCodeRegex = "^[0-9]{5}(?:-[0-9]{4})?$";
            Pattern usPostalCodePattern = Pattern.compile(usPostalCodeRegex);
            Matcher usPostalCodeMatches = usPostalCodePattern.matcher(postalCode);
            Boolean usPostalCodeMatch = usPostalCodeMatches.matches();

            if (!usPostalCodeMatch) {
                textValidator += "\nPlease enter a valid postal code!";
            }

        } else if (countryName == "Canada") {
            String canadaPostalCodeRegex = "^(?!.*[DFIOQU])[A-VXY][0-9][A-Z] ?[0-9][A-Z][0-9]$";
            Pattern canadaPostalCodePattern = Pattern.compile(canadaPostalCodeRegex);
            Matcher canadaPostalCodeMatches = canadaPostalCodePattern.matcher(postalCode);
            Boolean canadaPostalCodeMatch = canadaPostalCodeMatches.matches();

            if (!canadaPostalCodeMatch) {
                textValidator += "\nPlease enter a valid postal code!";
            }

        } else if (countryName == "UK") {
            String ukPostalCodeRegex = "^[A-Z]{1,2}[0-9R][0-9A-Z]? [0-9][ABD-HJLNP-UW-Z]{2}$";
            Pattern ukPostalCodePattern = Pattern.compile(ukPostalCodeRegex);
            Matcher ukPostalCodeMatches = ukPostalCodePattern.matcher(postalCode);
            Boolean ukPostalCodeMatch = ukPostalCodeMatches.matches();

            if (!ukPostalCodeMatch) {
                textValidator += "\nPlease enter a valid postal code!";
            }

        }

        String phoneNumberRegex = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
                + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"
                + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$";

        Pattern phonePattern = Pattern.compile(phoneNumberRegex);
        Matcher phoneMatcher = phonePattern.matcher(phone);
        Boolean phoneMatches = phoneMatcher.matches();

        if (!phoneMatches) {
            textValidator += "\nPlease enter a valid phone number!";
        }

        return textValidator;
    }
}
