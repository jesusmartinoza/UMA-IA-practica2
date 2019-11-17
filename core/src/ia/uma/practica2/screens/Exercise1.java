package ia.uma.practica2.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

import ia.uma.practica2.widgets.FilePicker;

/**
 * Created by jesusmartinoza on 10/31/19.
 *

 * Consideremos el siguiente concurso en el que un concursante se enfrenta a un robot. Se dispone de
 * una fila de sacos de dinero conectados longitudinalmente por un cable, de forma que sólo pueden
 * retirarse sacos de los extremos de la fila. Cada saco tiene impresa su cantidad de dinero (en millones
 * de euros).
 *
 * El concursante debe elegir entre los dos sacos de los extremos y retirar uno de ellos, pero cada vez
 * que cogemos un saco el robot se apodera del que ha quedado más a la derecha y lo pone fuera de su
 * alcance. El proceso se repite hasta que ya no quedan más sacos en la fila. El concursante desea
 * apoderarse de la mayor cantidad posible de dinero.
 *
 * Dada la definición anterior del problema anterior, se pide:
 *    a) Definir las clases adecuadas para representar una configuración cualquiera del estado del
 *       problema, suponiendo que el número inicial de sacos puede ser cualquiera, pero siempre PAR.
 *
 *    b) Implementar los métodos necesarios para resolver el problema mediante la búsqueda con el
 *       algoritmo A*. Para la definición de la función de coste, nótese que maximizar el dinero que se
 *       lleva el concursante equivale a minimizar el dinero que sustrae el robot.
 */
public class Exercise1 extends ExerciseScreen {

    private Game game;

    public Exercise1(Game game) {
        super(game);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
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
