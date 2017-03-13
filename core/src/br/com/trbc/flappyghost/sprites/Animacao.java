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
