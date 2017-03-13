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
