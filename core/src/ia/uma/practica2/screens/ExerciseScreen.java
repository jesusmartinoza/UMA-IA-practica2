package ia.uma.practica2.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by jesusmartinoza on 10/31/19.
 */
public class ExerciseScreen implements Screen {

    protected Game game;
    protected Skin skin;
    protected Stage stage;
    protected ShapeRenderer shapeRenderer;
    private Viewport viewport;
    protected static OrthographicCamera camera;

    public ExerciseScreen(Game game) {
        this.game = game;
        this.shapeRenderer = new ShapeRenderer();
        this.camera  = new OrthographicCamera();
        viewport = new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
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
        camera.update();
        shapeRenderer.setProjectionMatrix(camera.combined);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f);
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
