package sample;

/***************************************************************
 /* The following is the user class. All the data in this class
 /* is gathered from previous FXML GUI screens and methods within
 ****************************************************************/

public class User {

    /***************************************************************
     /* USER VARIABLE DATA AND CORRESPONDING GETTERS / SETTERS
     ****************************************************************/

    // collected from Account Creation
    String userEmail;
    String password;

    // collected from Profile creation
    String name;
    String username;
    char sex;
    int age;


    // Default constructor
    public User() {

        CreateAccount acctRefer = new CreateAccount();
        userEmail = acctRefer.getUserEmail();
        password = acctRefer.getPassword();

        Profile profileRefer = new Profile();
        name = profileRefer.getName();
        username = profileRefer.getUsername();
        sex = profileRefer.getSex();
        age = profileRefer.getSex();

    }

}
