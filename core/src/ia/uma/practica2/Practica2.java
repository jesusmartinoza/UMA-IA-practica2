package ia.uma.practica2;

import com.badlogic.gdx.Game;

import ia.uma.practica2.screens.MainScreen;

public class Practica2 extends Game {

	@Override
	public void create () {
        this.setScreen(new MainScreen(this));
	}

	@Override
	public void render () {
        super.render();
	}
	
	@Override
	public void dispose () {
	}
}
