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
    private double f;
    private double g;
    private double h;
    private int row;
    private int col;
    private Color color;
    private boolean diagonal;
    private MapNode parent;

    public MapNode(char value, int row, int col) {
        this.value = value;
        this.row = row;
        this.col = col;
        this.f = MAX_VALUE;
        this.g = MAX_VALUE;
        this.h = 0;
        this.color = isObstacle() ? ThemeColors.primary : ThemeColors.secondary;
        this.diagonal = false;
    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(color);
        shapeRenderer.rect(row, col, 1, 1);
    }

    public void reset() {
        this.f = MAX_VALUE;
        this.g = MAX_VALUE;
        this.h = 0;
        this.color = isObstacle() ? ThemeColors.primary  : ThemeColors.secondary;
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

    public void setF(double f) {
        this.f = f;
    }

    public void setG(double g) {
        this.g = g;
    }

    public void setH(double h) {
        this.h = h;
    }

    public double getF() {
        return f;
    }

    public double getG() {
        return g;
    }

    public double getH() {
        return h;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isDiagonal() {
        return diagonal;
    }

    public void setDiagonal(boolean diagonal) {
        this.diagonal = diagonal;
    }

    public MapNode getParent() {
        return parent;
    }

    public void setParent(MapNode parent) {
        this.parent = parent;
    }
}
