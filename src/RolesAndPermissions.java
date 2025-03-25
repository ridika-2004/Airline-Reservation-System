public class RolesAndPermissions {

    public int isPrivilegedUserOrNot(String username, String password) {
        int isFound = -1;
        for (int i = 0; i < AdminUser.adminUserNameAndPassword.length; i++) {
            if (username.equals(AdminUser.adminUserNameAndPassword[i][0])) {
                if (password.equals(AdminUser.adminUserNameAndPassword[i][1])) {
                    isFound = i;
                    break;
                }
            }
        }
        return isFound;
    }


    public String isPassengerRegistered(String email, String password) {
        String isFound = "0";
        for (Customer c : Customer.customerCollection) {
            if (email.equals(c.getEmail())) {
                if (password.equals(c.getPassword())) {
                    isFound = "1-" + c.getUserID();
                    break;
                }
            }
        }
        return isFound;
    }
}
