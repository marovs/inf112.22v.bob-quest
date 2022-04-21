package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import launcher.Boot;
import model.GameModel;
import model.GameState;
import org.lwjgl.system.CallbackI;

public class LevelCompletedScreen implements Screen {
    private final Viewport viewport;
    private final Stage stage;

    private final GameModel gameModel;
    private Skin skin;

    public LevelCompletedScreen(GameModel gameModel) {
        this.gameModel = gameModel;
        viewport = new FitViewport(Boot.INSTANCE.getScreenWidth(), Boot.INSTANCE.getScreenHeight(), new OrthographicCamera());
        stage = new Stage(viewport, new SpriteBatch());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
    }

    @Override
    public void show() {

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        table.center();
        table.setFillParent(true);
        stage.addActor(table);


        Label gameCompletedLabel = new Label(gameModel.getLevel().toString() + " COMPLETED!", font);
        gameCompletedLabel.setFontScale(4f);
        table.add(gameCompletedLabel);

        TextButton nextLevel = new TextButton("Next Level", skin);
        TextButton mainMenu = new TextButton("Main menu", skin);

        table.row();
        table.add(nextLevel).padTop(20).minWidth(250).minHeight(50);;
        table.row();
        table.add(mainMenu).padTop(20).minWidth(250).minHeight(50);;


        nextLevel.addListener(Boot.INSTANCE.getGameController().goToScreenListener(GameState.ACTIVE));
        mainMenu.addListener(Boot.INSTANCE.getGameController().goToScreenListener(GameState.MAIN_MENU));
    }

    @Override
    public void render(float delta) {
        gameModel.update();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
