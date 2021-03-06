package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class CreateAccount implements Initializable {

    /* used for reference to switch screens */
    Stage stage;
    Parent root;

    /***************************************************************
     /* VALID USER DATA TO STORE ELSEWHERE
     ****************************************************************/

    /* username, password, and respective getters/setters */
    private static String password;
    private static String userEmail;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }


    /***************************************************************
     /* PANE FXML ELEMENTS
     ****************************************************************/

    @FXML
    private AnchorPane mainAccountCreatePane;

    @FXML
    private Label mainEmailLabel;

    @FXML
    private TextField mainUsernameInfo;

    @FXML
    private PasswordField mainPasswordInfo;

    @FXML
    private Button createAcctButton;

    @FXML
    private Label mainPasswordLabel;

    @FXML
    private Label acctText;

    @FXML
    private PasswordField confirmPasswordInfo;

    @FXML
    private Label confirmPasswordLabel;

    @FXML
    private Label shortPasswordErrorMSG;

    @FXML
    private Label passwordMismatchErrorMSG;

    @FXML
    private Label invalidEmailErrorMSG;

    @FXML
    private Button cancelToLoginMain;

    /***************************************************************
     /* METHODS RELATED TO USERNAME AND PASSWORD VALIDATION
     ****************************************************************/

    // the following algorithm is provided by GeeksforGeeks
    // https://www.geeksforgeeks.org/check-email-address-valid-not-java/

    public static boolean isValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public boolean checkEmailProperty(TextField mainUsernameInfo) {

        boolean validEmail;

        if (isValid(mainUsernameInfo.getText())) {
            invalidEmailErrorMSG.setVisible(false);
            setUserEmail(mainUsernameInfo.getText());
            validEmail = true;

        } else {
            invalidEmailErrorMSG.setVisible(true);
            validEmail = false;

        }

        return validEmail;

    }

    public boolean checkPasswordLength(PasswordField mainPasswordInfo) {

        boolean validPasswordLength;

        if (mainPasswordInfo.getLength() < 8 || mainPasswordInfo.getLength() > 15) {
            shortPasswordErrorMSG.setVisible(true);
            validPasswordLength = false;

        } else {
            shortPasswordErrorMSG.setVisible(false);
            validPasswordLength = true;
        }

        return validPasswordLength;
    }

    public boolean checkMatchingPasswords(PasswordField mainPasswordInfo, PasswordField confirmPasswordInfo) {

        boolean samePasswords;

        if (mainPasswordInfo.getText().compareTo(confirmPasswordInfo.getText()) != 0) {
            passwordMismatchErrorMSG.setVisible(true);
            mainPasswordInfo.clear();
            confirmPasswordInfo.clear();
            samePasswords = false;

        } else {
            passwordMismatchErrorMSG.setVisible(false);
            setPassword(confirmPasswordInfo.toString());
            samePasswords = true;
        }

        return samePasswords;
    }

    /***************************************************************
     /* BINDING METHODS TO BUTTON ACTIONS
     ****************************************************************/
    public void handleAccountCreationActions() {
        createAcctButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // on the user's click of the "Create account" button, the program will check
                // if the password input is > 8 characters, and it matches to the confirm password

                checkEmailProperty(mainUsernameInfo);
                checkPasswordLength(mainPasswordInfo);
                checkMatchingPasswords(mainPasswordInfo, confirmPasswordInfo);

                if (checkEmailProperty(mainUsernameInfo) && checkPasswordLength(mainPasswordInfo)
                        && checkMatchingPasswords(mainPasswordInfo, confirmPasswordInfo)) {

                    // sends user to the program's main menu. All user data collected in the program screen
                    // can be called and save to their respective classes/databases here
                    setUserEmail(mainEmailLabel.toString());
                    setPassword(mainPasswordInfo.toString());

                    stage=(Stage) createAcctButton.getScene().getWindow();
                    //load up main menu FXML document
                    try {
                        root = FXMLLoader.load(getClass().getResource("Profile.fxml"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();

                }

            }
        });

    }

    @SuppressWarnings("Duplicates")
    public void handleCancelActions() {
        cancelToLoginMain.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                // if the user decides to cancel the account creation, their input data will
                // be cleared, and they will be returned to the Login screen

                mainUsernameInfo.clear();
                mainPasswordInfo.clear();
                confirmPasswordInfo.clear();


                stage=(Stage) cancelToLoginMain.getScene().getWindow();
                //load up Login FXML document
                try {
                    root = FXMLLoader.load(getClass().getResource("Login.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            }
        });

    }


    /***************************************************************
     /* INITIALIZABLE
     ****************************************************************/
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // hides the error messages on screen start-up. They will appear if the passwords mismatch
        // or if the password is too short

        invalidEmailErrorMSG.setVisible(false);
        shortPasswordErrorMSG.setVisible(false);
        passwordMismatchErrorMSG.setVisible(false);

        handleAccountCreationActions();
        handleCancelActions();

    }
}
