package br.com.trbc.flappyghost.estados;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import br.com.trbc.flappyghost.FlappyGhost;

/**
 * Created by turboc on 21/02/17.
 */
public class EstadoMenu extends Estado {

    // textura 2d para o fundo do menu
    private Texture background;
    // textura 2d para o botão de inicio do game
    private Texture playButton;



    // codificacao do metodo handleInput
    // metodo que obtem as interacoes de usuario com o game
    @Override
    public void handleInput() {

        // caso o evento touch seja realizado
        // seta o estado do game(cenario) com o estado de jogo
        if (Gdx.input.justTouched()) {
            gerenciadorEstado.set(new EstadoJogo(gerenciadorEstado, sb));

        }


    }

    // o metodo update realiza a chamada do metodo handleInput
    @Override
    public void update(float dt) {

        handleInput();

    }

    // o metodo render e responsabel por renderizar os objetos do cenario "menu"
    @Override
    public void render(SpriteBatch sb) {

        // posiciona a projecao do container com a camera do game
        sb.setProjectionMatrix(cam.combined);
        // inicia os desenhos no container
        sb.begin();
        // desenha os objetos do cenario
        sb.draw(background, 0, 0);
        // desenha o botao de play, no centro da camera
        sb.draw(playButton, cam.position.x - playButton.getWidth() /2 , cam.position.y);
        // finaliza o desenho do container
        sb.end();



    }

    // construtor do menu
    // incializa os elementos do menu
    public EstadoMenu(GerenciadorEstado gerenciadorEstado, SpriteBatch sb) {
        super(gerenciadorEstado, sb);
        cam.setToOrtho(false, FlappyGhost.WIDTH / 2, FlappyGhost.HEIGHT / 2);
        background = new Texture("bg.png");
        playButton = new Texture("playbtn.png");

    }

    // limpa as imagens carregadas para o menu
    @Override
    public void dispose() {
        background.dispose();
        playButton.dispose();

    }


}
