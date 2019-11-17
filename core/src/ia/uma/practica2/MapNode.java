package ia.uma.practica2;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import ia.uma.practica2.screens.Exercise3;

/**
 * Created by jesusmartinoza on 11/17/19.
 *
 * Class to represent a node for the exercise 3 of the activity
 * It will store value and draw info such as coordinates
 */
public class MapNode {

    public static float MAX_VALUE = 99999;

    private char value; // @ for obstacle and . for path
    private float f;
    private float g;
    private float h;
    private int row;
    private int col;
    private Color color;

    public MapNode(char value, int row, int col) {
        this.value = value;
        this.row = row;
        this.col = col;
        this.f = MAX_VALUE;
        this.g = MAX_VALUE;
        this.h = MAX_VALUE;
        this.color = isObstacle() ? Color.GRAY : Color.DARK_GRAY;
    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(color);
        shapeRenderer.rect(row, col, 1, 1);
    }

    /**
     * Returns true if row number and column number.
     * is in range
     */
    public boolean isValid() {
        return (row >= 0) && (row < Exercise3.MAP_ROWS) &&
                (col >= 0) && (col < Exercise3.MAP_COLS);
    }

    /// Getters and Setters
    public boolean isObstacle() {
        return value == '@';
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setF(float f) {
        this.f = f;
    }

    public void setG(float g) {
        this.g = g;
    }

    public void setH(float h) {
        this.h = h;
    }

    public float getF() {
        return f;
    }

    public float getG() {
        return g;
    }

    public float getH() {
        return h;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
