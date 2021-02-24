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

        String postalCodeRegex = "^[0-9]{5}(?:-[0-9]{4})?$"
                + "|^(?!.*[DFIOQU])[A-VXY][0-9][A-Z] ?[0-9][A-Z][0-9]$"
                + "|^[A-Z]{1,2}[0-9R][0-9A-Z]? [0-9][ABD-HJLNP-UW-Z]{2}$";
        Pattern postalCodePattern = Pattern.compile(postalCodeRegex);
        Matcher postalCodeMatcher = postalCodePattern.matcher(postalCode);
        Boolean postalCodeMatch = postalCodeMatcher.matches();

        if (!postalCodeMatch) {
            textValidator += "\nPlease enter a valid postal code!";
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
