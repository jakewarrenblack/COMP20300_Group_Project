package com.example.examplefx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class HelloController {
    @FXML // This annotation tells the compiler that the variable below is an FXML element, it exists in the FXML file, FXML can access this, even though it is private
    private URL location;

    // The variable name/identifier must match the fx:id in the FXML file
    // You do not need to initialise them, when the FXMLLoader initialises your UI it will construct them according to the definition in the FXML file
    @FXML
    private TextField textField;

    @FXML
    private TextArea textArea;


    @FXML
    private ResourceBundle resources;

    // Default constructor will have no arguments
    public HelloController() {
        //
    }


    // The initialise method is a method we can use if we need to do any set up for our controller – we don’t here, but still need to specify it (it must have a @FXML annotation)
    @FXML
    public void initialise() {
        //
    }

    // This method is called when the button is clicked
    // We gave the mapping to this function from SceneBuilder, which added it to the FXML file
    public void onHelloButtonClick(ActionEvent actionEvent) {

        // We can access the text field and text area using the variables we defined above
        // We can get the text from the text field and set it as the text in the text area
        textArea.setText(textField.getText());
    }
}
