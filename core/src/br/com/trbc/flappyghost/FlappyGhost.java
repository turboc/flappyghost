package br.com.trbc.flappyghost;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import br.com.trbc.flappyghost.estados.EstadoJogo;
import br.com.trbc.flappyghost.estados.EstadoMenu;
import br.com.trbc.flappyghost.estados.GerenciadorEstado;


// classe principal do nosso game, que sera chamada em cada launcher
// a origem de tudo se da aqui
public class FlappyGhost extends ApplicationAdapter {

	// largura do nosso game
	public static final int WIDTH = 480;

	// altura do nosso game
	public static final int HEIGHT = 800;

	// titulo do jogo
	public static final  String TITULO = "FlappyGhost";


	// cria uma classe gerenciadora de estados - cenarios
	private GerenciadorEstado gerenciadorEstado;

	// container em que os dados serao renderizados e exibidos
	private SpriteBatch batch;

	// musica do nosso jogo
	private Music music;



    /*
    *   O jogo (basicamente em jogos 2d, possui a seguinte estrutura
    *   A inicializacao (Aqui representada no metodo create)
    *   A atualizacao dos dados, que sera realizada antes da renderizacao,
    *   o metodo update sera chamado dentro do metodo render
    * */
	@Override
	public void create () {
		batch = new SpriteBatch();
        music = Gdx.audio.newMusic(Gdx.files.internal("music.wav") );
        music.setLooping(true);
        music.setVolume(0.1F);
        music.play();

        batch = new SpriteBatch();
        gerenciadorEstado = new GerenciadorEstado();
        gerenciadorEstado.push(new EstadoMenu(gerenciadorEstado, batch));

	}

	@Override
	public void render () {


        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gerenciadorEstado.update(Gdx.graphics.getDeltaTime());
        gerenciadorEstado.render(batch);



	}

	@Override
	public void dispose () {
		batch.dispose();

	}
}
