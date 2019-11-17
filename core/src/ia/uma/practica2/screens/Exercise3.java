package ia.uma.practica2.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import ia.uma.practica2.widgets.FilePicker;

/**
 * Created by jesusmartinoza on 10/31/19.
 */
public class Exercise3 extends ExerciseScreen {

    private char[][] mapData;

    public Exercise3(Game game) {
        super(game);
        mapData = new char[10][10];
    }

    private void parseFile(FileHandle fileHandle) {
        String content = fileHandle.readString();
        String[] lines = content.split(System.getProperty("line.separator"));
        int height = Integer.parseInt(lines[1].split(" ")[1]);
        int width = Integer.parseInt(lines[2].split(" ")[1]);
        TiledMapTileLayer layer1 = new TiledMapTileLayer(height, width, 2, 2);
        mapData = new char[width][height];

        String[] subarray = new String[lines.length - 4];
        System.arraycopy(lines, 4, subarray, 0, subarray.length);

        int i = 0;
        for(String l : subarray) {
            int j = 0;
            for(char c : l.toCharArray()) {
                mapData[i][j] = c;
                System.out.print(c);
                j++;
            }
            System.out.println();
            i++;
        }
    }

    @Override
    public void show() {
        super.show();
        TextButton button = new TextButton("Seleccionar archivo...", skin);

        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                FilePicker filePicker = new FilePicker("Save graph", skin) {
                    @Override
                    protected void result(Object object) {
                        if (object.equals("OK")) {
                            parseFile(getFile());
                            System.out.println(getFile().name());
                        }
                    }
                };
                filePicker.setDirectory(Gdx.files.local("maps/"));
                filePicker.show(stage);
            }
        });

        stage.addActor(button);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for(int i = 0; i < mapData.length; i++)
        {
            for(int j = 0; j < mapData[i].length; j++)
            {
                int tile = mapData[i][j]; //get tile type+
                shapeRenderer.setColor(tile == '@' ? Color.valueOf("#fc5c65") : Color.valueOf("#45aaf2"));
                shapeRenderer.rect(i, j, 1, 1);
            }
        }
        this.shapeRenderer.end();

        super.render(delta);
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
