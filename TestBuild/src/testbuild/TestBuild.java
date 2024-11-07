/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testbuild;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JOptionPane;

public class TestBuild extends Application {

    TextField fName, lName, matNo;
    TextField questionNo;
    TextArea codeArea;
    ComboBox<String> selExten;
    Button submitBtn;
    Button resetBtn;
    Button passBtn;
    Button retBtn;
    Button backBtn;
    PasswordField pass;

    private static final int START_SEC = 0; // Starting countdown time in seconds
    private static final int START_MIN = 0;
    private static final int START_HOUR = 2;

    private int timeSeconds = START_SEC;
    private int timeMin = START_MIN;
    private int timeHour = START_HOUR;

    private Label secLabel;

    private Label minLabel;
    private Label hourLabel;
    private Label colon;
    private Label colon2;
    private HBox timeLabel;

    private Label passError;
    private Timeline timeline; // Define timeline as a class-level variable

    private StackPane root;
    VBox welPage;
    BorderPane bp;
    VBox subPage;
    boolean timesup;

    @Override
    public void start(Stage stage) throws Exception {

        // First Screen Layout
        Label welMessage = new Label("EXAM SUBMISSION PORTAL");
        pass = new PasswordField();
        passError = new Label();
        HBox hb = new HBox(5, new Label("Password: "), pass);
        pass.setPromptText("Enter password");
        passBtn = new Button("ENTER");
        passBtn.setPadding(new Insets(10));
        passBtn.setPrefWidth(200);
        passBtn.setMaxHeight(40);

        welPage = new VBox(10, welMessage, hb, passError, passBtn, new Label("NOTE: Once logged in fill your details"));

        hb.setAlignment(Pos.CENTER);
        VBox.setMargin(hb, new Insets(20));
        welPage.setAlignment(Pos.CENTER);
        welMessage.setStyle("-fx-font-size: 48px; -fx-text-fill: blue;");
        welPage.setStyle("-fx-font-size: 26px;");
        hb.setSpacing(20);
        welPage.setSpacing(20);
        passError.setStyle("-fx-text-fill: red;");

        // Submit Page
        retBtn = new Button("HOME");
        backBtn = new Button("BACK");

        retBtn.setStyle("-fx-background-color: red;");
        backBtn.setStyle("-fx-background-color: yellow");
        HBox gb = new HBox(10, backBtn, retBtn);
        gb.setAlignment(Pos.CENTER);

        Label submitMessage = new Label("Submission Successful");

        subPage = new VBox(10, submitMessage, new Label("SELECT AN OPTION"), gb);

        subPage.setAlignment(Pos.CENTER);
        submitMessage.setStyle("-fx-font-size: 48px; -fx-text-fill: green;");
        subPage.setSpacing(15);
        subPage.setStyle("-fx-font-size: 25px");
        retBtn.setPadding(new Insets(10));
        retBtn.setPrefWidth(200);
        retBtn.setMaxHeight(40);
        backBtn.setPadding(new Insets(10));
        backBtn.setPrefWidth(200);
        backBtn.setMaxHeight(40);

        // ---------------------------------------------------------------------------------------------
        fName = new TextField();
        lName = new TextField();
        matNo = new TextField();
        questionNo = new TextField();
        selExten = new ComboBox(FXCollections.observableArrayList(
                "JAVA", "C", "PY"
        ));
        codeArea = new TextArea();
        codeArea.setPrefRowCount(40);
        submitBtn = new Button("SUBMIT");
        resetBtn = new Button("RESET");
        HBox buttonPane = new HBox(10, resetBtn, submitBtn);

        secLabel = new Label();
        minLabel = new Label();
        hourLabel = new Label();
        colon = new Label(":");
        colon2 = new Label(":");

        secLabel.setStyle("-fx-font-size: 48px;");
        minLabel.setStyle("-fx-font-size: 48px;");
        hourLabel.setStyle("-fx-font-size: 48px;");
        colon.setStyle("-fx-font-size: 48px;");
        colon2.setStyle("-fx-font-size: 48px;");

        timeLabel = new HBox(10, hourLabel, colon, minLabel, colon2, secLabel);

        VBox vb1 = new VBox(new Label("First Name"), fName);
        VBox vb2 = new VBox(new Label("Last Name"), lName);
        VBox vb3 = new VBox(new Label("Matric Number"), matNo);
        VBox vb4 = new VBox(new Label("Language"), selExten);
        VBox vb5 = new VBox(new Label("Question Number"), questionNo);

        VBox vb6 = new VBox(new Label("Code"), codeArea);

        GridPane gp = new GridPane();

        fName.setPrefWidth(230);
        gp.setPrefWidth(230);

        gp.addRow(0, vb1);
        gp.addRow(1, vb2);
        gp.addRow(2, vb3);
        gp.addRow(3, vb4);
        gp.addRow(4, vb5);

        fName.setPromptText("Enter your First Name");
        lName.setPromptText("Enter your Last Name");
        matNo.setPromptText("Enter your Matric Number");
        selExten.setPromptText("Select Extension");
        questionNo.setPromptText("Enter your Question Number");
        codeArea.setPromptText("Enter your Code");

        buttonPane.setAlignment(Pos.CENTER);

        bp = new BorderPane();
        bp.setTop(timeLabel);
        bp.setLeft(gp);
        bp.setCenter(vb6);
        bp.setBottom(buttonPane);
        bp.setStyle("-fx-font-size:11pt");

        timeLabel.setAlignment(Pos.CENTER);

        ColumnConstraints cc1 = new ColumnConstraints();
        RowConstraints rc = new RowConstraints();

        cc1.setPercentWidth(100);
        cc1.setHalignment(HPos.RIGHT);
        rc.setValignment(VPos.CENTER);

        gp.getColumnConstraints().addAll(cc1);
        gp.getRowConstraints().addAll(rc, rc, rc, rc, rc);
        gp.setPadding(new Insets(10));
        bp.setPadding(new Insets(20));
        gp.setHgap(15);
        gp.setVgap(10);
        Insets margins = new Insets(20, 0, 0, 0);
        HBox.setMargin(resetBtn, margins);
        HBox.setMargin(submitBtn, margins);

        submitBtn.setPadding(new Insets(20));
        resetBtn.setPadding(new Insets(20));
        submitBtn.setPrefWidth(250);
        resetBtn.setPrefWidth(250);
        submitBtn.setMaxHeight(40);
        resetBtn.setMaxHeight(40);

        gp.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        vb6.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        timeLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        buttonPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        selExten.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        fName.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        submitBtn.setMaxWidth(Double.MAX_VALUE);
        resetBtn.setMaxWidth(Double.MAX_VALUE);

        passBtn.setOnAction(e -> verifyPassword());
        resetBtn.setOnAction(e -> resetForm());
        submitBtn.setOnAction(e -> submitForm());
        retBtn.setOnAction(e -> returnAction());
        backBtn.setOnAction(e -> continueExam());

        root = new StackPane(welPage);
        Scene scene = new Scene(root, 1000, 750);
        timer();

        stage.setScene(scene);
        fName.requestFocus();
        stage.setTitle("SUBMIT CODE");
        stage.setResizable(false);
        stage.show();
    }

    private void successPage() {
        if (timesup == true) {
            backBtn.setDisable(true);
        } else {
            backBtn.setDisable(false);
        }
        root.getChildren().remove(bp);
        root.getChildren().add(subPage);
        timeline.stop();
    }

    private void verifyPassword() {
        String password = pass.getText();
        if ("start exam".equals(password)) {
            resetForm();
            resetTimer();
            codeArea.setDisable(false);
            submitBtn.setDisable(false);
            passError.setText("");
            pass.setText("");
  
            root.getChildren().remove(welPage);
            root.getChildren().add(bp);
            timeline.play();
        } else {
            passError.setText("Incorrect Password Please Try again!!");
            pass.setText("");
        }
    }

    private void resetTimer() {
        timeSeconds = START_SEC;
        timeMin = START_MIN;
        timeHour = START_HOUR;
        timesup = false;
    }

    private void returnAction() {
        fName.setDisable(false);
        lName.setDisable(false);
        matNo.setDisable(false);
        resetBtn.setDisable(false);
        root.getChildren().remove(subPage);
        root.getChildren().add(welPage);
    }

    private void continueExam() {
        fName.setDisable(true);
        lName.setDisable(true);
        matNo.setDisable(true);
        resetBtn.setDisable(true);
        root.getChildren().remove(subPage);
        root.getChildren().add(bp);
        timeline.play();
    }

    private void resetForm() {
        matNo.setText("");
        fName.setText("");
        lName.setText("");
        questionNo.setText("");
        codeArea.setText("");
        selExten.getSelectionModel().clearSelection();
        fName.requestFocus();
    }

    private void timesUp() {
        timesup = true;
        if (timesup == true) {
            codeArea.setDisable(true);
            submitBtn.setDisable(true);
            submitForm();
        }
        else{
            codeArea.setDisable(false);
            submitBtn.setDisable(false);
        }
    }

    private void timer() {

        // Update the label text with initial countdown value
        secLabel.setText(String.valueOf(timeSeconds));
        minLabel.setText(String.valueOf(timeMin));
        hourLabel.setText(String.valueOf(timeHour));

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (timeSeconds > 0) {
                timeSeconds--;
            } else {
                if (timeMin > 0) {
                    timeMin--;
                    timeSeconds = 59;
                } else if (timeHour > 0) {
                    timeHour--;
                    timeMin = 59;
                    timeSeconds = 59;
                } else {
                    // Time's up
                    timeline.stop();
                    secLabel.setText("0");
                    minLabel.setText("0");
                    hourLabel.setText("0");
                    timesUp();
                    return;
                }
            }

            // Update label text with current time values
            secLabel.setText(String.valueOf(timeSeconds));
            minLabel.setText(String.valueOf(timeMin));
            hourLabel.setText(String.valueOf(timeHour));
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
//        timeline.play();

    }

    private boolean isValid() {
        String pattern = "UG/\\d{2}/\\d{4}";
        String input = matNo.getText();

        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(input);

        boolean fNameStatus, lNameStatus, matNoStatus, questionNoStatus, selExtenStatus, codeAreaStatus;
        fNameStatus = !fName.getText().isEmpty();

        lNameStatus = !lName.getText().isEmpty();

        matNoStatus = !matNo.getText().isEmpty() && matcher.matches();

        try {
            Integer.parseInt(questionNo.getText());
            questionNoStatus = true;
        } catch (NumberFormatException e) {
            questionNoStatus = false;

        }
        selExtenStatus = selExten.getSelectionModel().getSelectedItem() != null;
        codeAreaStatus = !codeArea.getText().isEmpty();

        return fNameStatus && lNameStatus && matNoStatus && questionNoStatus && selExtenStatus && codeAreaStatus;

    }

    private void submitForm() {
        if (isValid()) {
            try {
                String matno = matNo.getText().replace("/", "");
                String filePath = fName.getText() + "_" + lName.getText() + "_" + matno + "_" + questionNo.getText() + "." + selExten.getSelectionModel().getSelectedItem().toLowerCase();
                File file = new File("c:/Submit/" + selExten.getSelectionModel().getSelectedItem() + "/" + filePath);
                if (file.exists()) {
                    int response = JOptionPane.showConfirmDialog(null, "File Already Exists\n Do you want to continue?", "", JOptionPane.YES_NO_CANCEL_OPTION);

                    if (response != JOptionPane.YES_OPTION) {
                        return;
                    }
                } else {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                }

                FileWriter fileName = new FileWriter(file, false);

                try (BufferedWriter writer = new BufferedWriter(fileName)) {
                    writer.write(codeArea.getText());
                }

//                resetForm();
                timeline.pause();
                successPage();

            } catch (IOException err) {
                JOptionPane.showMessageDialog(null, "An error occured " + err.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "FILL THE FORM PROPERLY BEFORE SUBMITTING");
        }
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
