# Flappyghost
### Exemplo de modificação de um flappybird, utilizando LibGDX

Baixar a libgdx em: https://libgdx.badlogicgames.com/download.html
Neste passo a passo, a versão utilizada foi a libgdx-1.6.1

Quando executar o arquivo gdx-setup.jar, irá ser exibida uma janela como esta:
![image](https://cloud.githubusercontent.com/assets/8375336/23878992/41bb5f34-0829-11e7-97cd-469209e89186.png)


 

Fazer o preenchimento das informações da seguinte forma:
 

Após o preenchimento dos campos, acionar o botão “Generate”.
 
Clicar em “SIM”
 
“SIM” para esta mensagem também.

Após o projeto gerado, basta abri-lo no android-studio.
A estrutura inicial do projeto é a seguinte:
 
Um pacote android, um desktop (onde selecionamos que nosso jogo iria ser executado), e o pacote core, onde ficarão as codificações comuns a todas as plataformas. 

Para testarmos as codificações, enquanto estamos construindo o projeto, podemos direcionar o projeto a executar em desktop:
Podemos ir em Edit configurations, como na imagem que segue:
 

Adicionamos uma nova configuração:
 
Selecionamos a opção “Application”
 
Nomeamos a configuração como “Desktop” e selecionamos a classe DesktopLauncher em Mainclass:
 
Para o diretório de trabalho, selecionaremos a pasta “assets” do android, do nosso projeto:
 
Por fim, em module, selecionamos desktop
 

 

Ao executar nosso projeto, temos uma tela inicial, com a imagem da badlogic games

 

Agora vamos ao código

Nosso jogo terá três estados: 
	O estado de menu, em que o usuário deve realizar uma ação na tela para que possamos entrar no jogo;
	O estado de jogo, propriamente dito;
	E o estado de fim de jogo (Quando o “player” morre – gam eover).

Começaremos então a codificação dos estados, criando um novo pacote chamado “estados”:
 
Neste exemplo, deixarei o nome dos pacotes simples, para ganharmos tempo.
Criaremos então, a princípio, duas classes.
A primeira, a classe abstrata chamada “Estado” – que será a base dos nossos estados e a segunda: um gerenciador de Estados.
 

Mais explicações, nos comentários dos códigos:
Estado.java
package estados;

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

    // classe que gerencia os estados do jogo
    protected GerenciadorEstado gerenciadorEstado;

    // construtor que recebe um gerenciador de estado
    protected Estado(GerenciadorEstado gerenciadorEstado) {

        this.gerenciadorEstado =  gerenciadorEstado;
        
        // inicializacao da camera e do mouse
        this.cam = new OrthographicCamera();
        this.mouse = new Vector3();


    }

    // metodos 'padrao' do nosso jogo 2d
    
    // metodo responsavel por obter as informacoes de entrada
    // interacoes com o mundo externo.. clicks, toques, etc
    protected abstract void handleInput();
    
    // metodo responsavel por atualizar os dados do jogo,
    // a partir do estado dos atores e das interacoes com o usuario
    // o update e responsavel por atualizar estados e posicoes dos atores no jogo
    public abstract void update(float dt);
    
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



GerenciadorEstado.java
package br.com.trbc.flappyghost.estados;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.Stack;

/**
 * Created by turboc on 21/02/17.
 */


/*   O gerenciador de estados sera responsavel pela pilha de estados
 *   Nosso game tera uma pilha de estados
 *
 **/
public class GerenciadorEstado {

    // pilha, fornecida pela biblioteca java util
    private Stack<Estado> estados;

    public GerenciadorEstado () {
        estados = new Stack<Estado>();

    }

    // metodo responsavel por incluir um estado na pilha
    public void push(Estado estado) {
        estados.push(estado);
    }

    // metodo responsavel por excluir o primeiro estado da pilha
    public void pop() {

        estados.pop().dispose();
    }

    // metodo responsavel por substituir o primeiro estado da pilha
    public void set(Estado estado) {
        estados.pop().dispose();
        estados.push(estado);
    }

    // chama o metodo update do estado que o game se encontra
    public void update(float dt) {
        estados.peek().update(dt);
    }

    // chama o metodo de renderizacao do estado em que o game se encontra
    public void render(SpriteBatch sb) {

        estados.peek().render(sb);

    }





}


Após a criação da nossa classe abstrata de Estados e nosso gerenciador (Que nada mais é do que uma pilha de estados (Cenários) do nosso jogo, devemos criar os cenários propriamente ditos. Um para o menu – onde o usuário deve realizar  uma ação (Touch) para iniciar e o outro é o cenário do game.
Primeiro vamos criar a classe do menu, que vai ser chamada no início do nosso game.
MenuEstado.java
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
            gerenciadorEstado.set(new EstadoJogo(gerenciadorEstado));

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
    public EstadoMenu(GerenciadorEstado gerenciadorEstado) {
        super(gerenciadorEstado);
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


Note que em nosso handleInput, quando o usuário realiza uma ação na tela (touch), um novo cenário é inserido (O cenário de gameplay propriamente dito)
if (Gdx.input.justTouched()) {
    gerenciadorEstado.set(new EstadoJogo(gerenciadorEstado));

}



Vamos criar também a classe de gameplay apenas extendendo a classe Estado e um construtor padrão.
Antes da codificação da nossa classe de gameplay, vamos criar nossos personagens e objetos que irão interagir no game.
Dentro do pacore principal do nosso “core”, vamos criar um pacote chamado “sprites”
 

Então vamos criar uma classe Java chamada “Animacao”. Essa classe será responsável pela animação do nosso personagem principal.
Animacao.java
package br.com.trbc.flappyghost.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Created by turboc on 21/02/17.
 */

// classe de animacao do personagem

public class Animacao {

    // array de texturas (Textregion)
    // consiste em uma textura que contem todos os frames a serem exibidos em nossa animacao
    private Array<TextureRegion> frames;
    // maximo de tempo de vigencia de cada frame
    private float maxFrameTime;

    // tempo corrente do frame em vigencia

    private float currentFrameTime;

    // quantidade de frames
    private int frameCount;

    // indice do frame
    private int frame;

    public Animacao(TextureRegion region, int frameCount, float cycleTime /* tempo de um ciclo de animacao */) {

        // inicializa o array de frames da imagem recebida no region
        frames = new Array<TextureRegion>();

        // determina o tamanho de cada frame, a partir da quantidade de frames e do tamanho da imagem
        int frameWidth = region.getRegionWidth() / frameCount;

        // corta a imagem e adiciona os frame na animacao
        for (int i = 0; i < frameCount ; i++) {
            frames.add(new TextureRegion(region, i* frameWidth, 0, frameWidth, region.getRegionHeight()));

        }
        this.frameCount = frameCount;
        /* calcula o maximo de tempo de um frame, a partir do tempo do ciclo dividido pela quantidade de frames */
        maxFrameTime = cycleTime / frameCount;

        // inicia em zero
        frame = 0;


    }

    public void update(float dt) {

        // para cada update da animacao, e obtido o delta time do game em exeucao
        // e esse delta time e utilizado para incrementar o tempo em que o frame vigente esta

        currentFrameTime += dt;

        // caso o tempo do frame tenha superado o tempo limite de cada frame
        // incrementa-se o indize do frame e inicializa-se o tempo do proximo frame a ser exibido
        if (currentFrameTime > maxFrameTime) {
            frame++;
            currentFrameTime=0;
        }

        // se o indice do frame superou a quantidade de frames da imagem
        // retorna para o indice zero
        if (frame >= frameCount) {
            frame = 0;
        }
    }


    // retorna o frame corrente do indice em que a animacao se encontra
    public TextureRegion getFrame() {
        return frames.get(frame);

    }

}


Com a classe de animação codificada, passamos agora para a codificação do personagem principal do game (Ghost).
No pacote “sprites”, vamos criar uma classe chamada Ghost.
Ghost.java
package br.com.trbc.flappyghost.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Jorge on 13/03/2017.
 */
public class Ghost {

    // gravidade ---- determina a direcao constante em que nosso personagem ira ir, caso a tela nao seja tocada
    // e a contante que exerce forca sobre o personagem no mundo do nosso game

    // note que -15 significa um valor "contrario" aos valores gravitacionais que estamos acostumados
    // isso se da porque iremos utilizar a constante gravidade para indicar ao sprite do personagem
    // uma coordenada a ser atualizada em cada atualizacao.
    // e essa coordenada se da de forma negativa porque e como a libgdx trabalha com o sistema de coordenadas da camera
    // ver mais em: https://github.com/libgdx/libgdx/wiki/Coordinate-systems

    private static final int GRAVIDADE = -15;

    // essa e a movimentacao no eixo X que nosso ghost realiza a cada atualizacao
    private static final int MOVIMENTO = 100;

    // som do personagem quando realiza o pulo
    private Sound jump;

    // posicao do personagem
    private Vector3 poisicao;

    // velocidade de atualizacao da posicao
    private Vector3 velocity;

    // limites das linhas do personagem
    private Rectangle limites;

    // responsavel pela animacao do personagem
    private Animacao ghostAnimacao;

    // textura do personagem estatica
    private Texture textura;

 
    public Ghost(int x, int y) {

        // carrega o som de jump do personagem
        jump = Gdx.audio.newSound(Gdx.files.internal("jump.wav"));

        // inicia a posicao de acordo com os parametros passados no construtor
        poisicao = new Vector3(x, y, 0);

        // inicia com velocidade zero
        velocity = new Vector3(0, 0, 0);

        // obtem a imagem da animacao
        textura = new Texture("ghostanimation.png");
        // inicia a animacao passando a imagem obtida, a quantidade de sprites que existem
        // dentro dessa animagem a ser animada e o tempo do ciclo da animacao
        ghostAnimacao = new Animacao(new TextureRegion(textura), 6, 0.5F);


        // cria os limites do personagem a partir do x, y informados no construtor
        // da largura dividida pela quantidade de quadros e da altira.
        limites = new Rectangle(x, y, textura.getWidth() /6, textura.getHeight());

    }

    // metodo de atualizacao dos valores do personagem
    public void update(float dt) {

        // atualiza a animacao
        ghostAnimacao.update(dt);

        // caso o personagem esteja com seu eixo Y dentro das dependencias da camera
        // adiciona a constante gravidade ao vetor, fazendo com que o Y seja decrementado
        // e a movimentacao do eixo Y se altere
        if (poisicao.y > 0) {
            velocity.add(0, GRAVIDADE, 0);
        }

        // escala a velocidade de acordo com a taxa de atualizacao de frames
        velocity.scl(dt);

        // adiciona o movimento ao personagem, alterando sua posicao
        // a partir da constante MOVIMENTO, sempre considerando a taxa de atualizacao de frames
        // e nosso Y atualizado de acordo com a gravidade aplicada ao personagem
        poisicao.add(MOVIMENTO * dt, velocity.y, 0 /* eixo Z nao considerado aqui */);

        // caso o eixo y tenha saido das dependencias da camera, ele e novamente colocado em zero
        if (poisicao.y < 0) {
            poisicao.y = 0;
        }


        // escala a velocidade com valor inversamente proporcional ao deltatime
        // para reutilizacao na proxima atualizacao

        velocity.scl( 1/ dt);

        limites.setPosition(poisicao.x, poisicao.y);

    }


    public Vector3 getPosicao() {
        return poisicao;
    }

    public void setPosicao(Vector3 position) {
        this.poisicao = position;
    }

    public TextureRegion getGhost() {

        return ghostAnimacao.getFrame();
    }

    // incrementa o Y da velocidade para realizacao do pulo do personagem
    public void jump() {

        velocity.y = 300;
        jump.play(0.5f);
    }

    public Rectangle getBounds() {
        return limites;
    }

    public void dispose() {

        textura.dispose();
        jump.dispose();
    }



}


Agora precisamos criar os objetos que irão colidir com nosso personagem. Esses objetos que vão dar a dificuldade ao jogo.
No pacote “sprites” vamos adicionar uma nova classe chamada Toten
Toten.java
package br.com.trbc.flappyghost.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by Jorge on 13/03/2017.
 */
public class Toten {

    // o toten ira flutuar a partir dessa constante
    private static final int FLUTUACAO = 130;

    // espaco entre o toten de cima e o toten de baixo, para a passagem do personagem

    private static final int TOTEN_GAP = 100;


    // o minimo que o toten de cima deve ter para manter no topo e deixar espaco para que o
    // toten de baixo seja renderizado
    private static final int MANTER_TOPO = 100;

    // largura do toten
    public static final int TOTEN_WIDTH = 52;


    // uma textura para o toten de cima e uma para o toten de baixo

    private Texture topToten, bottomToten;

    // posicao do toten de cima e do toten de baixo

    private Vector2 posTopToten, posBotToten;

    // um randomizador, para variar as alturas dos totens spawnados
    private Random rand;

    // os limites dos totens
    private Rectangle boundsTop, boundsBot;



    public Toten (float x) {

        topToten = new Texture("toptoten.png");
        bottomToten = new Texture("bottomtoten.png");
        rand = new Random();
        // cria um novo toten com uma altura randomica, mas considerando a distancia entre os totens
        // e o minimo de altura que ele deve ter para ser desenhado
        posTopToten = new Vector2(x, rand.nextInt(FLUTUACAO)+ TOTEN_GAP+ MANTER_TOPO);

        // a partir da criacao do toten de cima, cria o de baixo, considerando a distancia que
        // deve existir entre os dois

        posBotToten = new Vector2(x, posTopToten.y - TOTEN_GAP - bottomToten.getHeight());

        // obtem os limites dos objetos e demarca seus limites de colisao
        boundsTop = new Rectangle(posTopToten.x, posTopToten.y, topToten.getWidth(), topToten.getHeight());
        boundsBot = new Rectangle(posBotToten.x, posBotToten.y, bottomToten.getWidth(), bottomToten.getHeight());




    }

    public Texture getTopToten() {
        return topToten;
    }

    public void setTopToten(Texture topToten) {
        this.topToten = topToten;
    }

    public Texture getBottomToten() {
        return bottomToten;
    }

    public void setBottomToten(Texture bottomToten) {
        this.bottomToten = bottomToten;
    }

    public Vector2 getPosTopToten() {
        return posTopToten;
    }

    public void setPosTopToten(Vector2 posTopToten) {
        this.posTopToten = posTopToten;
    }

    public Vector2 getPosBotToten() {
        return posBotToten;
    }

    public void setPosBotToten(Vector2 posBotToten) {
        this.posBotToten = posBotToten;
    }

    // reposiciona os totens para economizar memoria.
    public void reposiciona (float x) {
        posTopToten.set(x, rand.nextInt(FLUTUACAO)+ TOTEN_GAP+ MANTER_TOPO);
        posBotToten.set(x, posTopToten.y - TOTEN_GAP - bottomToten.getHeight());
        boundsBot.setPosition(posBotToten.x, posBotToten.y);
        boundsTop.setPosition(posTopToten.x, posTopToten.y);


    }
   // indica se house colisao entre o player e os totens
    public boolean colide(Rectangle player) {
        return player.overlaps(boundsTop) || player.overlaps(boundsBot);

    }

    public void dispose () {

        topToten.dispose();
        bottomToten.dispose();
    }




}


Agora vamos voltar ao desenvolvimento da classe EstadoJogo.java.
Nela, vamos codificar toda a interação entre as ações do usuário, o personagem principal e os objetos do cenário.
EstadoJogo.java
package br.com.trbc.flappyghost.estados;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import br.com.trbc.flappyghost.FlappyGhost;
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


    /*
    *  O construtor do estado do jogo recebe o gerenciador de cenarios
    *  e inicializa os objetos para o game
    * */
    public EstadoJogo(GerenciadorEstado gerenciadorEstado) {
        // realiza a chamada ao construtor da superclasse
        super(gerenciadorEstado);
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
            Toten Toten =  totens.get(i);


            // se o toten passou da area de exibicao da camera
            // reposiciona o mesmo
            if ((cam.position.x - (cam.viewportWidth /2)) > Toten.getPosTopToten().x + Toten.getTopToten().getWidth() ) {

                Toten.reposiciona(Toten.getPosTopToten().x + ((Toten.TOTEN_WIDTH + TOTEN_SPACING) * TOTEN_COUNT));

            }

            // verifica a colisao.. se o player colidiu com o toten em questao,
            // reinicia o cenario
            if (Toten.colide(ghost.getBounds())) {
                gerenciadorEstado.set(new EstadoJogo(gerenciadorEstado));

            }

        }

        // caso o player tenha caido ate o chao do game,
        // reinicia o cenario
        if (ghost.getPosicao().y <= ground.getHeight() + GROUND_Y_OFFSET) {
            gerenciadorEstado.set(new EstadoJogo(gerenciadorEstado));

        }
        cam.update();

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


Por fim, iremos adicionar uma pontuação ao game. Para isso iremos utilizar um HUD (heads up display) para exibição dos dados.
No projeto, vamos adicionar um novo pacote chamado “display” e uma nova classe java, chamada “Hud”
 



