package ia.uma.practica2.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ia.uma.practica2.MapNode;
import ia.uma.practica2.ThemeColors;
import ia.uma.practica2.widgets.FilePicker;

/**
 * Created by jesusmartinoza on 10/31/19.
 */
public class Exercise3 extends ExerciseScreen implements Runnable {

    private MapNode[][] mapData;
    public static int MAP_ROWS;
    public static int MAP_COLS;
    private MapNode src;
    private MapNode dest;
    private Thread thread;
    private float explorationValue;

    public Exercise3(Game game) {
        super(game);

        mapData = new MapNode[0][0];
        explorationValue = 5;
    }

    /**
     * Get Libgdx file handle and get content of .map file.
     *
     * .map file looks like
     *
     * type octile
     * height 512
     * width 512
     * map
     * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
     * @@@@@@@@@@@@@@@@@...........@@@@@@@@@@.......................................@@@@@@@
     *
     * ...
     * @param fileHandle
     */
    private void parseFile(FileHandle fileHandle) {
        String content = fileHandle.readString();
        String[] lines = content.split(System.getProperty("line.separator"));
        MAP_COLS = Integer.parseInt(lines[1].split(" ")[1]);
        MAP_ROWS = Integer.parseInt(lines[2].split(" ")[1]);
        mapData = new MapNode[MAP_ROWS][MAP_COLS];

        String[] subarray = new String[lines.length - 4];
        System.arraycopy(lines, 4, subarray, 0, subarray.length);

        int i = 0;
        for(String l : subarray) {
            int j = 0;
            for(char c : l.toCharArray()) {
                mapData[i][j] = new MapNode(c, i, j);
                j++;
            }
            i++;
        }
    }

    private boolean isDestination(int row, int column) {
        return row == dest.getRow() && column == dest.getCol();
    }

    /**
     * Utility method to calculate the 'h' heuristics.
     */
    private double calculateHValue(int row, int col)
    {
        double rowDistance = Math.pow(row - dest.getRow(), 2);
        double colDistance = Math.pow(col - dest.getCol(), 2);

        return Math.sqrt(rowDistance + colDistance);
    }


    /**
     Generating all the 8 successor of this cell

         N.W   N   N.E
           \   |   /
            \  |  /
         W----Cell----E
              / | \
            /   |  \
         S.W    S   S.E

     Cell-->Popped Cell (i, j)
     N -->  North       (i-1, j)
     S -->  South       (i+1, j)
     E -->  East        (i, j+1)
     W -->  West           (i, j-1)
     N.E--> North-East  (i-1, j+1)
     N.W--> North-West  (i-1, j-1)
     S.E--> South-East  (i+1, j+1)
     S.W--> South-West  (i+1, j-1)
     */
    private List<MapNode> calculateNeighbors(MapNode node) {
        ArrayList<MapNode> neighbors = new ArrayList<>();

        if(validCoordinate(node.getRow() - 1, node.getCol())) {
            MapNode neighbor = mapData[node.getRow() - 1][node.getCol()];
            neighbor.setDiagonal(false);
            neighbors.add(neighbor);
        }

        if (validCoordinate(node.getRow() + 1, node.getCol())) {
            MapNode neighbor = mapData[node.getRow() + 1][node.getCol()];
            neighbor.setDiagonal(false);
            neighbors.add(neighbor);
        }

        if (validCoordinate(node.getRow(), node.getCol() + 1)) {
            MapNode neighbor = mapData[node.getRow()][node.getCol() + 1];
            neighbor.setDiagonal(false);
            neighbors.add(neighbor);
        }

        if (validCoordinate(node.getRow(), node.getCol() - 1)) {
            MapNode neighbor = mapData[node.getRow()][node.getCol() - 1];
            neighbor.setDiagonal(false);
            neighbors.add(neighbor);
        }

        if (validCoordinate(node.getRow() - 1, node.getCol() + 1)) {
            MapNode neighbor = mapData[node.getRow() - 1][node.getCol() + 1];
            neighbor.setDiagonal(true);
            neighbors.add(neighbor);
        }

        if (validCoordinate(node.getRow() - 1, node.getCol() - 1)) {
            MapNode neighbor = mapData[node.getRow() - 1][node.getCol() - 1];
            neighbor.setDiagonal(true);
            neighbors.add(neighbor);
        }

        if (validCoordinate(node.getRow() + 1, node.getCol() + 1)) {
            MapNode neighbor = mapData[node.getRow() + 1][node.getCol() + 1];
            neighbor.setDiagonal(true);
            neighbors.add(neighbor);
        }

        if (validCoordinate(node.getRow() + 1, node.getCol() - 1)) {
            MapNode neighbor = mapData[node.getRow() + 1][node.getCol() - 1];
            neighbor.setDiagonal(true);
            neighbors.add(neighbor);
        }

        return neighbors;
    }

    /**
     * Pick random source and destination nodes
     */
    private void pickRandomPath() {
        while(src == null || dest == null) {
            MapNode tmpSrc = mapData
                    [MathUtils.random(0, MAP_ROWS - 1)]
                    [MathUtils.random(0, MAP_COLS - 1)];

            MapNode tmpDest = mapData
                    [MathUtils.random(0, MAP_ROWS - 1)]
                    [MathUtils.random(0, MAP_COLS - 1)];

            if(!tmpSrc.isObstacle() && !tmpDest.isObstacle()) {
                tmpSrc.setColor(Color.YELLOW);
                tmpDest.setColor(Color.RED);
                src = tmpSrc;
                dest = tmpDest;
            }

            System.out.println("Picking random path...");
        }
    }

    /**
     * Validate if the coordinate is in the map.
     * @param i x
     * @param j y
     * @return result
     */
    private boolean validCoordinate(int i, int j) {
        return (i >= 0) && (i < MAP_ROWS) && (j >= 0) && (j < MAP_COLS);
    }

    /**
     * Reset nodes values
     */
    private void reset() {
        for (MapNode[] aMapData : mapData)
            for (MapNode tile : aMapData)
                tile.reset();
    }

    /**
     * Algorithm very chidori to find the shortest path.
     */
    private void aStarSearch()
    {
        // If the source is out of range
        if (!src.isValid())
        {
            System.out.println("Source is invalid\n");
            return;
        }

        // If the destination is out of range
        if (!dest.isValid())
        {
            System.out.println("Destination is invalid\n");
            return;
        }

        // Either the source or the destination is blocked
        if (src.isObstacle() || dest.isObstacle())
        {
            System.out.println("Source or the destination is blocked\n");
            return;
        }

        // If the destination cell is the same as source cell
        if(src.getRow() == dest.getRow() || src.getCol() == dest.getCol())
        {
            System.out.println("We are already at the destination\n");
            return;
        }

        // Create a closed list and initialise it to false which means
        // that no cell has been included yet
        // This closed list is implemented as a boolean 2D array
        List<MapNode> closedList = new ArrayList<>();
        List<MapNode> openList = new ArrayList<>();

        src.setG(0);
        src.setH(calculateHValue(src.getRow(), src.getCol()));
        src.setF(src.getG() + src.getH());

        openList.add(src);
        boolean foundDest = false;

        while (!openList.isEmpty())
        {
            List<MapNode> nodesWithSameF = openList.stream()
                    .filter((o) -> o.getF() == openList.get(0).getF())
                    .collect(Collectors.toList());
            nodesWithSameF.sort((n1, n2) -> Double.compare(n1.getH(), n2.getH()));
            MapNode current = nodesWithSameF.get(0);

            if(isDestination(current.getRow(), current.getCol()))
            {
                // Set the Parent of the destination cell
                System.out.println("The destination cell is found\n");
                foundDest = true;

                // Draw Path
                MapNode pathNode = current;
                while(src != pathNode) {
                    pathNode.setColor(Color.valueOf("#FFA200"));
                    pathNode = pathNode.getParent();
                }
                openList.clear();
                continue;
            }

            openList.remove(0);
            closedList.add(current);
            List<MapNode> neighbors = calculateNeighbors(current);

            for(MapNode n : neighbors) {
                if(closedList.contains(n) || n.isObstacle())
                    continue;

                int r = n.getRow();
                int c = n.getCol();
                double gScore = current.getG() + (n.isDiagonal() ? 1.41 : 1); // length of this path.
                if (!openList.contains(n))
                    openList.add(n); // Discover a new node
                //else if (gScore >= n.getG())
                //    continue;

                // This path is the best until now. Record it!
                n.setParent(current);
                n.setG(gScore);
                n.setH(calculateHValue(r, c) * explorationValue);
                n.setF(n.getG() + n.getH());

                n.setColor(Color.valueOf("#1B676C"));
            }

            openList.sort((t1, t2) -> Double.compare(t1.getF(), t2.getF()));
        }

        if (!foundDest)
            System.out.println("Failed to find the Destination Cell\n");

        thread.interrupt();
    }

    @Override
    public void show() {
        super.show();
        TextButton fileButton = new TextButton("Abrir", skin);
        TextButton resetButton = new TextButton("Reiniciar", skin);
        TextButton menuButton = new TextButton("Menu", skin);
        Slider slider = new Slider(1,10,1,false, skin);
        slider.setValue(5);
        Label sliderLabel = new Label("Rapacidad:", skin);
        sliderLabel.setStyle(new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        Exercise3 context = this;

        fileButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                FilePicker filePicker = new FilePicker("Save graph", skin) {
                    @Override
                    protected void result(Object object) {
                        if (object.equals("OK")) {
                            src = dest = null;
                            parseFile(getFile());
                            //pickRandomPath();
                        }
                    }
                };
                filePicker.setDirectory(Gdx.files.local("maps/"));
                filePicker.show(stage);
            }
        });

        resetButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                reset();
                src = dest = null;
            }
        });

        menuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainScreen(game));
            }
        });

        slider.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                float sliderValue = slider.getValue();

                if(sliderValue > 5)
                    explorationValue = sliderValue - 5;
                else
                    explorationValue = 0.2f * sliderValue;

                reset();
                System.out.println("Slider: " + sliderValue + " rapacidad: " + explorationValue);
                thread = new Thread(context);
                thread.start();
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.padRight(30);
        table.right().add(fileButton).row();
        table.add(resetButton).padTop(20).row();
        table.add(menuButton).padTop(20).row();
        table.add(sliderLabel).padTop(40).row();
        table.add(slider).width(80).padTop(10).row();

        stage.addActor(table);

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                Vector3 input = new Vector3(screenX, screenY, 0);
                camera.unproject(input);

                int x = (int)input.x;
                int y = (int)input.y;

                if(src == null)
                    src = new MapNode('.', x, y);
                else if(dest == null)
                    dest = new MapNode('.', x, y);

                if(src != null && dest != null) {
                    System.out.print(src.getRow());
                    src.setColor(Color.YELLOW);
                    dest.setColor(Color.RED);
                    thread = new Thread(context);
                    thread.start();
                }

                return true;
            }
        });
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(ThemeColors.primary.r, ThemeColors.primary.g, ThemeColors.primary.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(mapData != null) {
            this.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

            for (MapNode[] aMapData : mapData)
                for (MapNode tile : aMapData)
                    tile.draw(shapeRenderer);

            if(src != null) src.draw(shapeRenderer);
            if(dest != null) dest.draw(shapeRenderer);
            this.shapeRenderer.end();
        }

        super.render(delta);
   }

    @Override
    public void run() {
        if(mapData.length > 0)
            aStarSearch();
    }
}
