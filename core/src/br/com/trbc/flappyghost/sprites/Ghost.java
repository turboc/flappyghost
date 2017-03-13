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
