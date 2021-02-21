package Utilites;

public class HelperMethods {

    public static String emptyCustomerTextFieldValidator(
            String name,
            String address,
            String postalCode,
            String phone,
            String firstLevelSelection,
            String emptyValidator) {

        if (name.equals("")) {
            emptyValidator = "\nThe Customer Name field cannot be empty!";
        }

        if (address.equals("")) {
            emptyValidator = "\nThe Address field cannot be empty!";
        }

        if (postalCode.equals("")) {
            emptyValidator = "\nThe Postal Code field cannot be empty!";
        }

        if (phone.equals("")) {
            emptyValidator = "\nThe Phone field cannot be empty!";
        }

        if (firstLevelSelection.equals(null)) {
            emptyValidator = "\nA First Level Division must be selected!";
        }

        return emptyValidator;
    }
}
