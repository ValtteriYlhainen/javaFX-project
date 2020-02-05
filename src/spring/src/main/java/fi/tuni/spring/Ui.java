package fi.tuni.spring;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Ui-class that contains all of the UI-elements and initializes them.
 */
public class Ui {
    int rowCount;
    double prefWidth = 150;
    Stage stage;
    static Html html;
    Label htmlView;
    String htmlTitle;
    FileChooser fileChooser = new FileChooser();

    /**
     * "Main-method" of the Ui-class. Call this method to generate the UI.
     *
     * @param window
     */

    void generateUi(Stage window) {
        rowCount = 0;
        html = new Html();
        stage = window;
        GridPane gridPane = new GridPane();
        generateTitleColumn(gridPane);
        generateObjectColumn(gridPane);
        HBox hBoxTop = generateHtmlViewer(gridPane);
        HBox hBoxBottom = generateBottomRow();
        generateWindow(hBoxTop, hBoxBottom);
    }

    /**
     * First part of the UI-generation.
     *
     * To add another type of heading copy paste the three rows and change all
     * heading related strings to the correct one.
     *
     * @param gridPane
     */

    void generateTitleColumn(GridPane gridPane) {
        generateRow(gridPane, "Title", 0, rowCount);
        Button title = new Button("Set Title");
        title.setPrefWidth(prefWidth);
        gridPane.add(title, 2, rowCount);
        title.setOnMousePressed(mouseEvent -> {
            TextField textField = (TextField) getNodeByRowColumnIndex(gridPane, 1, 0);
            html.getHtml().set(4, "<title>" + textField.getText() + "</title>");
            htmlTitle = textField.getText();
            setHtmlView();
        });
        rowCount++;
        
        generateRow(gridPane, "H1", 0, rowCount);
        generateBasicButton(gridPane, "Add H1", "h1", 2, rowCount);
        rowCount++;
        
        generateRow(gridPane, "H2", 0, rowCount);
        generateBasicButton(gridPane, "Add H2", "h2", 2, rowCount);
        rowCount++;
        
        generateRow(gridPane, "H3", 0, rowCount);
        generateBasicButton(gridPane, "Add H3", "h3", 2, rowCount);
        rowCount++;
    }

    /**
     * Second part of the UI-generation.
     *
     * To add another type of custom object copy paste the rows
     * between generateRow and rowCount++ correcting the strings and
     * creating the right effect for button.
     *
     * @param gridPane
     */

    void generateObjectColumn(GridPane gridPane) {
        generateRow(gridPane, "Paragraph", 0, rowCount);
        generateBasicButton(gridPane, "Add Paragraph", "p", 2, rowCount);
        rowCount++;

        generateRow(gridPane, "Url, Text", 0, rowCount);
        Button link = new Button("Add Link");
        link.setPrefWidth(prefWidth);
        gridPane.add(link, 2, rowCount);
        link.setOnMousePressed(mouseEvent -> {
            TextField textField = (TextField) getNodeByRowColumnIndex(gridPane, 1, rowCount);
            String[] array = textField.getText().split(",");
            html.getHtml().add( "<a href=\"" + array[0] + "\">" + array[1] + "</a>");
            setHtmlView();
        });
        rowCount++;

        generateRow(gridPane, "Src, Alt, Width, Height", 0, rowCount);
        Button image = new Button("Add Image");
        image.setPrefWidth(prefWidth);
        gridPane.add(image, 2, rowCount);
        image.setOnMousePressed(mouseEvent -> {
            TextField textField = (TextField) getNodeByRowColumnIndex(gridPane, 1, rowCount);
            String[] array = textField.getText().split(",");
            html.getHtml().add( "<img src=\"" + array[0] + "\" alt=\"" + array[1] + "\" width=\"" + array[2] + "\" height=\"" + array[3] + "\">");
            setHtmlView();
        });
        rowCount++;

        generateRow(gridPane, "Src, Type, Width, Height", 0, rowCount);
        Button video = new Button("Add Video");
        video.setPrefWidth(prefWidth);
        gridPane.add(video, 2, rowCount);
        video.setOnMousePressed(mouseEvent -> {
            TextField textField = (TextField) getNodeByRowColumnIndex(gridPane, 1, rowCount);
            String[] array = textField.getText().split(",");
            html.getHtml().add( "<video width=\"" + array[2] + "\" height=\"" + array[3] + "\" controls> <source src=\"" + array[0] + "\" type=\"video/" + array[1] +"\"> Your browser does not support the video tag</video>");
            setHtmlView();
        });
        rowCount++;

        generateRow(gridPane, "Integer", 0, rowCount);
        Button remove = new Button("Remove line");
        remove.setPrefWidth(prefWidth);
        gridPane.add(remove, 2, rowCount);
        remove.setOnMousePressed(mouseEvent -> {
            TextField textField = (TextField) getNodeByRowColumnIndex(gridPane, 1, rowCount);
            html.getHtml().remove(Integer.parseInt(textField.getText()) - 1);
            setHtmlView();
        });
        rowCount++;
    }

    /**
     * Third part of the UI-generation.
     *
     * Generates HTML-view on the right, which shows the current code.
     *
     * @param gridPane
     */

    HBox generateHtmlViewer(GridPane gridPane) {
        htmlView = new Label();
        htmlView.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        htmlView.setWrapText(true);

        HBox hBoxTop = new HBox(gridPane, htmlView);
        setHtmlView();
        return hBoxTop;
    }

    /**
     * Fourth part of the UI-generation.
     *
     * Generates the buttons on the bottom row.
     *
     * To add another button copy paste a button, correct the strings
     * and change the actions to the wanted one and add the button
     * to hBoxBottom at the end.
     */

    HBox generateBottomRow() {

        Button save = new Button("Save");
        save.setOnMousePressed(mouseEvent -> {
            FileWriter fWriter;
            BufferedWriter writer;
            try {
                fWriter = new FileWriter(htmlTitle + ".html");
                writer = new BufferedWriter(fWriter);
                writer.write(html.getHtmlString());
                writer.newLine();
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Button open = new Button("Open");

        open.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                File file = fileChooser.showOpenDialog(stage);
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    html.getHtml().clear();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        html.getHtml().add(line);
                    }
                } catch (IOException eIOE) {
                        eIOE.printStackTrace();
                }
                html.getHtml().remove(html.getHtml().size() - 1);
                html.getHtml().remove(html.getHtml().size() - 1);
                setHtmlView();
            }
        });

        Button openBrowser = new Button("Open html in default browser");
        openBrowser.setOnMousePressed(mouseEvent -> {

            String url = "http://localhost:8080/index";
            String os = System.getProperty("os.name").toLowerCase();

            if(os.indexOf( "win" ) >= 0) {
                Runtime rt = Runtime.getRuntime();
                try {
                    rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (os.indexOf( "mac" ) >= 0) {
                Runtime rt = Runtime.getRuntime();
                try {
                    rt.exec("open " + url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Runtime rt = Runtime.getRuntime();
                String[] browsers = { "firefox", "mozilla",
                        "opera",  "safari", "chrome" };

                StringBuffer cmd = new StringBuffer();
                for (int i = 0; i < browsers.length; i++)
                    if(i == 0)
                        cmd.append(String.format(    "%s \"%s\"", browsers[i], url));
                    else
                        cmd.append(String.format(" || %s \"%s\"", browsers[i], url));

                try {
                    rt.exec(new String[] { "sh", "-c", cmd.toString() });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        HBox hBoxBottom = new HBox(save, open, openBrowser);
        hBoxBottom.setPrefHeight(50.0);
        hBoxBottom.setAlignment(Pos.CENTER);
        hBoxBottom.setSpacing(10);

        return hBoxBottom;
    }

    /**
     * Last part of the UI-generation.
     *
     * Constructs the UI in a desired way and shows it
     *
     * @param hBoxTop
     * @param hBoxBottom
     */
    void generateWindow(HBox hBoxTop, HBox hBoxBottom) {

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(hBoxTop);
        borderPane.setBottom(hBoxBottom);

        stage.setTitle("HTML Generator");
        Scene content = new Scene(borderPane, 720, 640);
        stage.initStyle(StageStyle.DECORATED);
        stage.setScene(content);
        stage.show();
    }

    /**
     * Generates a simple row which add a title part and a editable
     * TextField next to it in the given GridPane in the given location.
     *
     * @param gridPane
     * @param title
     * @param x
     * @param y
     */

    void generateRow(GridPane gridPane, String title, int x, int y) {
        generateUiTitle(gridPane, title, x, y);
        TextField titleInput = new TextField();
        gridPane.add(titleInput, x + 1, y);
    }

    /**
     * Generates a uneditable TextField to the given GridPane in the given location
     * and sets the the text to title.
     *
     * @param gridPane
     * @param title
     * @param x
     * @param y
     */
    void generateUiTitle(GridPane gridPane, String title, int x, int y) {
        TextField textField = new TextField(title + ":");
        textField.setFont(Font.font("System Bold"));
        textField.setEditable(false);
        gridPane.add(textField, x, y);
    }

    /**
     * Generates a basic button to the given location in the given GridPane
     * which creates a simple <type>add text from left TextField</type> button
     *
     * @param gridPane
     * @param title
     * @param type
     * @param x
     * @param y
     */
    void generateBasicButton(GridPane gridPane, String title, String type, int x, int y) {
        Button button = new Button(title);
        button.setPrefWidth(prefWidth);
        gridPane.add(button, x, y);
        button.setOnMousePressed(mouseEvent -> {
            TextField textField = (TextField) getNodeByRowColumnIndex(gridPane, x - 1, y);
            html.getHtml().add("<" + type +">" + textField.getText() + "</"+ type +">");
            setHtmlView();
        });
    }

    /**
     * Sets and updates the HtmlView.
     *
     */
    void setHtmlView() {
        String htmlOutput = "";
        int rowCount = 1;
        for (String s : html.getHtml()) {
            htmlOutput += rowCount + ": " + s + "\n";
            rowCount++;
        }
        htmlView.setText(htmlOutput + rowCount + ": </body>\n" + (rowCount + 1) + ": </html>");
    }

    /**
     * returns the node from the given GridPane with the given coordinates.
     *
     * @param gridPane
     * @param column
     * @param row
     * @return
     */
    public Node getNodeByRowColumnIndex (GridPane gridPane, final int column, final int row) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();

        for (Node node : childrens) {
            if(gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }

        return result;
    }

    /**
     * private method that opens a file.
     * Used by the open button
     *
     * DON'T USE!!!
     *
     * @param file
     */

    private void openFile(File file) {
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.open(file);
        } catch (IOException ex) {
            Logger.getLogger(
                    Application.class.getName()).log(
                    Level.SEVERE, null, ex
            );
        }
    }
}
