package br.com.trbc.flappyghost.estados;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import br.com.trbc.flappyghost.FlappyGhost;
import br.com.trbc.flappyghost.display.Hud;
import br.com.trbc.flappyghost.sprites.Ghost;
import br.com.trbc.flappyghost.sprites.Toten;

/**
 * Created by Jorge on 13/03/2017.
 */

/*
*  Classe EstadoJogo - responsavel pelo gerenciamento da interacao com o usuario
*  atualizacao dos objetos e renderizacao do nosso jogo
*  e a classe principal da gameplay do nosso game.
* 
* */
public class EstadoJogo extends Estado {


    // espacamento entre os totens
    private static final int TOTEN_SPACING = 125;

    // quantidade de totens a serem criados
    private static final int TOTEN_COUNT = 4;

    // offset em relacao ao chao
    private static final int GROUND_Y_OFFSET = -30;


    // nosso personagem principal - o ghost

    private Ghost ghost;

    // textua referente ao background do cenario
    private Texture bg;

    // testura referente ao chao do cenario
    private Texture ground;

    // posicao 1 e 2 do chao
    // sera melhor explicada na utilizacao
    private Vector2 groundPos1, groundPos2;

    // um array de totens
    private Array<Toten> totens;

    private Hud hud;

    /*
    *  O construtor do estado do jogo recebe o gerenciador de cenarios
    *  e inicializa os objetos para o game
    * */
    public EstadoJogo(GerenciadorEstado gerenciadorEstado, SpriteBatch sb) {
        // realiza a chamada ao construtor da superclasse
        super(gerenciadorEstado, sb);
        // inicia o personagem na posicao 50 x 300
        ghost = new Ghost(50,300);

        // posiciona a camera do game no centro da tela.

        cam.setToOrtho(false, FlappyGhost.WIDTH /2 , FlappyGhost.HEIGHT / 2);

        // carrega o background e o chão
        bg = new Texture("bg.png");
        ground = new Texture("ground.png");

        // cria as duas posicoes do chao
        // o chao e exibido de forma duplicada para gerar a sensacao de movimento
        // de acordo com a movimentacao da camera
        groundPos1 = new Vector2((cam.position.x - cam.viewportWidth / 2), GROUND_Y_OFFSET);
        groundPos2 = new Vector2((cam.position.x - cam.viewportWidth / 2) + ground.getWidth(), GROUND_Y_OFFSET);


        // inicializa o array de totens
        totens = new Array<Toten>();

        // cria novos totens de acordo com a quantidade constante
        for (int i = 1; i <= TOTEN_COUNT; i++) {

            totens.add(new Toten(i * (TOTEN_SPACING + Toten.TOTEN_WIDTH)));
        }

        this.sb = sb;
        hud = new Hud(sb);

    }


    // responsavel por obter informacoes de input (interacoes com o usuario)
    @Override
    protected void handleInput() {

        // se o evento touch ocorreu, realiza o jump no personagem
        if (Gdx.input.justTouched()) {
            ghost.jump();
        }

    }


    // responsavel por atualizar os dados do game
    // tratar as entradas e interacoes entre o usuario
    //  e as interacoes entre os objetos do jogo
    @Override
    public void update(float dt) {
        handleInput();
        updateGround();
        ghost.update(dt);

        cam.position.x = ghost.getPosicao().x + 80;

        // verifica os totens criados
        for (int i = 0; i< totens.size; i++) {
            Toten toten =  totens.get(i);


            // se o toten passou da area de exibicao da camera
            // reposiciona o mesmo
            if ((cam.position.x - (cam.viewportWidth /2)) > toten.getPosTopToten().x + toten.getTopToten().getWidth() ) {

                toten.reposiciona(toten.getPosTopToten().x + ((Toten.TOTEN_WIDTH + TOTEN_SPACING) * TOTEN_COUNT));
                hud.addScore(100);

            }

            // verifica a colisao.. se o player colidiu com o toten em questao,
            // reinicia o cenario
            if (toten.colide(ghost.getBounds())) {
                gerenciadorEstado.set(new EstadoJogo(gerenciadorEstado, sb));

            }


        }

        // caso o player tenha caido ate o chao do game,
        // reinicia o cenario
        if (ghost.getPosicao().y <= ground.getHeight() + GROUND_Y_OFFSET) {
            gerenciadorEstado.set(new EstadoJogo(gerenciadorEstado, sb));

        }


        cam.update();
        hud.update(dt);

    }

    @Override
    public void render(SpriteBatch sb) {



        sb.setProjectionMatrix(cam.combined);
        sb.begin();




        sb.draw(bg, cam.position.x - (cam.viewportWidth / 2), 0);
        sb.draw(ghost.getGhost(), ghost.getPosicao().x, ghost.getPosicao().y);

        for (Toten Toten: totens) {

            sb.draw(Toten.getTopToten(), Toten.getPosTopToten().x, Toten.getPosTopToten().y);
            sb.draw(Toten.getBottomToten(), Toten.getPosBotToten().x, Toten.getPosBotToten().y);


        }




        sb.draw(ground, groundPos1.x, groundPos1.y);
        sb.draw(ground, groundPos2.x, groundPos2.y);


        sb.end();

        hud.draw();

    }

    @Override
    public void dispose() {

        bg.dispose();
        ghost.dispose();
        ground.dispose();
        for (Toten Toten : totens) {
            Toten.dispose();

        }



    }
    // se a camera comeca a exibir alem dos limites de cada posicao do chao
    // reposiciona as imagens do chao para dentro da camera.

    private void updateGround() {

        if ((cam.position.x - (cam.viewportWidth / 2)) > groundPos1.x + ground.getWidth()) {
            groundPos1.add(ground.getWidth() *2, 0);
        }
        if ((cam.position.x - (cam.viewportWidth / 2)) > groundPos2.x + ground.getWidth()) {
            groundPos2.add(ground.getWidth() *2, 0);
        }

    }

}
