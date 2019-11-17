package ia.uma.practica2.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by jesusmartinoza on 10/31/19.
 */
public class ExerciseScreen implements Screen {

    private Game game;
    protected Skin skin;
    protected Stage stage;
    protected ShapeRenderer shapeRenderer;

    public ExerciseScreen(Game game) {
        this.game = game;
        this.shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void show() {
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        stage = new Stage();
    }

    @Override
    public void render(float delta) {
        stage.draw();
        stage.act();
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

    }
}
