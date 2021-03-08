package assignment_1;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class evaluation extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("."));
        File mainDirectory = directoryChooser.showDialog(primaryStage);

        if(mainDirectory == null){
            System.out.println("No directory chosen");
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

            VBox report = new VBox();
            report.getChildren().addAll(result);

            // Label
//            Label showAccuracy = new Label("Accuracy: ");
//            report.getChildren().add(showAccuracy);

            Scene resultLog = new Scene(report, 715, 500);
            primaryStage.setTitle("Spam Master 3000");
            primaryStage.setScene(resultLog);
            primaryStage.show();
        }
    }


    public static void main(String[] args) { launch(args); }
}
