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
