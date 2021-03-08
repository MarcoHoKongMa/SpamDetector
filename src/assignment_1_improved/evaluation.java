package assignment_1_improved;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.text.DecimalFormat;

public class evaluation extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("."));
        File mainDirectory = directoryChooser.showDialog(primaryStage);

        if(mainDirectory == null){
            Alert alert = new Alert(Alert.AlertType.WARNING, "No directory chosen. Program will be quit now.");
            alert.showAndWait();
            System.exit(0);
        }
        else{
            testing evaList = new testing(mainDirectory.getAbsolutePath());
            TableView<TestFile> result = new TableView<>();

            // Columns
            TableColumn<TestFile, String> fileColumn = new TableColumn<>("File");
            fileColumn.setMinWidth(300);
            fileColumn.setCellValueFactory(new PropertyValueFactory<>("filename"));
            TableColumn<TestFile, String> classColumn = new TableColumn<>("Actual Class");
            classColumn.setMinWidth(100);
            classColumn.setCellValueFactory(new PropertyValueFactory<>("actualClass"));
            TableColumn<TestFile, String> spamColumn = new TableColumn<>("Spam Probability");
            spamColumn.setMinWidth(300);
            spamColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getSpamProbRounded()));

            result.setItems(evaList.getTestFiles());
            result.getColumns().addAll(fileColumn, classColumn, spamColumn);

            // >> Pane for Table <<
            VBox report = new VBox();
            report.getChildren().addAll(result);

            // >> Pane for Summanry <<
            GridPane stat = new GridPane();
            stat.setAlignment(Pos.TOP_LEFT);
            stat.setHgap(10);
            stat.setVgap(10);
            stat.setPadding(new Insets(25, 25, 25, 25));

            // >> Accuracy & Precision <<
            System.out.println("Calculating accuracy & precision...");
            double correctGuess = 0;
            double truePositives = 0;
            double falsePositives = 0;
            double numFiles = 0;

            for(TestFile email: evaList.getTestFiles()) {
                if(email.getActualClass() == "Spam" && email.getSpamProbability() > 0.5) {
                    correctGuess++;
                    truePositives++;
                }
                else if(email.getActualClass() == "Ham" && email.getSpamProbability() < 0.5) {
                    correctGuess++;
                }
                else if(email.getActualClass() == "Ham" && email.getSpamProbability() > 0.5) {
                    falsePositives++;
                }
                numFiles++;
            }

            double accuracy = correctGuess / numFiles;
            double precision = truePositives / (falsePositives + truePositives);

            // >> Stats Summary <<
            DecimalFormat df = new DecimalFormat("0.00000");

            Label showAccuracy = new Label("Accuracy:");
            TextField calAccuracy = new TextField(df.format(accuracy));
            stat.add(showAccuracy, 0, 0);
            stat.add(calAccuracy, 1, 0);

            Label showPrecision = new Label("Precision:");
            TextField calPrecision = new TextField(df.format(precision));
            stat.add(showPrecision, 0, 1);
            stat.add(calPrecision, 1, 1);

            // >> Root Pane <<
            VBox root = new VBox();
            root.getChildren().addAll(report, stat);

            // >> Setup Stage <<
            Scene resultLog = new Scene(root, 715, 500);
            primaryStage.setTitle("Spam Master 3000");
            primaryStage.setScene(resultLog);
            primaryStage.show();
        }
    }


    public static void main(String[] args) { launch(args); }
}
