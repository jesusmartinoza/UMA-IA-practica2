package ia.uma.practica2.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import ia.uma.practica2.widgets.FilePicker;

/**
 * Created by jesusmartinoza on 10/31/19.
 */
public class Exercise3 extends ExerciseScreen {

    TiledMap map = new TiledMap();
    OrthogonalTiledMapRenderer renderer = new OrthogonalTiledMapRenderer(map, 1);
    Texture textureGrass = new Texture("tileset/grass.png");
    Texture textureDirt = new Texture("tileset/dirt.png");

    public Exercise3(Game game) {
        super(game);
    }

    private void parseFile(FileHandle fileHandle) {
        String content = fileHandle.readString();
        String[] lines = content.split(System.getProperty("line.separator"));
        MapLayers layers = map.getLayers();

        TiledMapTileLayer layer1 = new TiledMapTileLayer(512, 512, 4, 4);

        int i = 0;
        for(String l : lines) {
            if(l.length() > 10) {
                int j = 0;
                for(char c : l.toCharArray()) {
                    TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                    StaticTiledMapTile tile = new StaticTiledMapTile(new TextureRegion(
                            c == '@'? textureDirt : textureGrass,
                            0, 0, 162, 164));

                    tile.setOffsetX(i);
                    tile.setOffsetY(j);
                    cell.setTile(tile);

                    System.out.print(c);

                    layer1.setCell(i, j, cell);
                    j++;
                }
                i++;
                layers.add(layer1);
            }
        }

        renderer = new OrthogonalTiledMapRenderer(map, 1);
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
        super.render(delta);
        renderer.render();
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
