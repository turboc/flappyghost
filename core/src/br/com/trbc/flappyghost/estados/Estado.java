package br.com.trbc.flappyghost.estados;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;


/**
 * Created by turboc on 21/02/17.
 */
public abstract class Estado {

    /* Orthographic camera
    *  e utilizada para exibir as imagens no mundo bidimensional
    *  serao os olhos do jogo
    *
    * */
    protected OrthographicCamera cam;

    // o game tera um vetor de tres dimensoes para representar as coordenadas do mouse
    protected Vector3 mouse;

    // classe que gerencia os estados do jogo (uma pilha de estados - cenarios)
    protected GerenciadorEstado gerenciadorEstado;

    protected SpriteBatch sb;
    // construtor que recebe um gerenciador de estado
    protected Estado(GerenciadorEstado gerenciadorEstado, SpriteBatch sb) {

        this.gerenciadorEstado =  gerenciadorEstado;

        // inicializacao da camera e do mouse
        this.cam = new OrthographicCamera();
        this.mouse = new Vector3();
        this.sb = sb;


    }

    // metodos 'padrao' do nosso jogo 2d

    // metodo responsavel por obter as informacoes de entrada
    // interacoes com o mundo externo.. clicks, toques, etc
    protected abstract void handleInput();

    // metodo responsavel por atualizar os dados do jogo,
    // a partir do estado dos atores e das interacoes com o usuario
    // o update e responsavel por atualizar estados e posicoes dos atores no jogo
    public abstract void update(float dt /* delta time e a variacao de atualizacao de frames do dispositivo em que o jogo esta sendo executado*/);

    // metodo responsavel por exibir/reexibir os objetos na tela
    // recebe um SpriteBatch. que e o SpriteBatch do nosso jogo
    // uma area em que todos os objetos visiveis serao renderizados (que irao para a tela)
    public abstract void render(SpriteBatch sb);

    // metodo padrao para eliminar os objetos inutilizados em nosso jogo
    public abstract void dispose();

    // construtor padrao
    public Estado () {

    }




}

