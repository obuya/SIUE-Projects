package application;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import org.w3c.dom.Document;

public class LearnOBAMain extends Application {
    private Pane splashLayout;
    private ProgressBar loadProgress;
    private Label progressText;
    private WebView webView;
    private LoginGUI loginView;
    private Stage mainStage;
    private static final int SPLASH_WIDTH = 770;
    private static final int SPLASH_HEIGHT = 327;

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void init() {
        ImageView splash =
                new ImageView(new Image("file:resources/splashImage.png"));
        loadProgress = new ProgressBar();
        loadProgress.setPrefWidth(SPLASH_WIDTH - 20);
        progressText = new Label("Loading LearnOBA Project...");
        splashLayout = new VBox();
        splashLayout.getChildren().addAll(splash, loadProgress, progressText);
        progressText.setAlignment(Pos.CENTER);
        splashLayout
                .setStyle("-fx-padding: 5; -fx-background-color: cornsilk; -fx-border-width:5; -fx-border-color: linear-gradient(to bottom, #04356C, derive(#04356C, 50%));");
        splashLayout.setEffect(new DropShadow());
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        final Stage splash = new Stage();
        loginView = new LoginGUI("LoginGUI.fxml", "Login Screen");
        //showSplash(splash);
        loginView.show();
        showMainStage();


        webView.getEngine().documentProperty().addListener(new ChangeListener<Document>() {
            @Override
            public void changed(ObservableValue<? extends Document> observableValue,
                                Document document, Document document1) {
                if (splash.isShowing()) {
                    loadProgress.progressProperty().unbind();
                    loadProgress.setProgress(1);
                    progressText.setText("LearnOBA is loaded...");
                    mainStage.setIconified(false);
                    splash.toFront();
                    FadeTransition fadeSplash =
                            new FadeTransition(Duration.seconds(1.2), splashLayout);
                    fadeSplash.setFromValue(1.0);
                    fadeSplash.setToValue(0.0);
                    fadeSplash.setOnFinished(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            splash.hide();
                            loginView.show();
                        }
                    });
                    fadeSplash.play();

                }
            }
        });

    }

    private void showMainStage() {
        mainStage = new Stage(StageStyle.DECORATED);
        mainStage.setTitle("LearnOBA");
        mainStage.centerOnScreen();
        mainStage.setIconified(true);

        // create a WebView.
        webView = new WebView();
        webView.getEngine().load("http://fxexperience.com/");
        loadProgress.progressProperty().bind(
                webView.getEngine().getLoadWorker().workDoneProperty().divide(100));
    }

    private void showSplash(Stage initStage) {
        Scene splashScene = new Scene(splashLayout);
        initStage.initStyle(StageStyle.UNDECORATED);
        final Rectangle2D bounds = Screen.getPrimary().getBounds();
        initStage.setScene(splashScene);
        initStage.setX(bounds.getMinX() + bounds.getWidth() / 2 - SPLASH_WIDTH / 2);
        initStage.setY(bounds.getMinY() + bounds.getHeight() / 2 - SPLASH_HEIGHT / 2);
        initStage.show();
    }
}
