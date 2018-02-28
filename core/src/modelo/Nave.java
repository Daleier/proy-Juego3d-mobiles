package modelo;

import com.badlogic.gdx.math.Vector3;

public class Nave extends MovilMax {

	public static int vidas_restantes;
	public static int aciertos;
	public static boolean invulnerable; //tras chocar contra un enemigo la nave sera invulnerable durante 1 segundo
	public static float tiempo_ultimo_impacto;

	public Nave(Vector3 pos, float escala, Vector3 velocidade, Vector3 velocidadeMax, float radioEsfera) {
        super(pos, escala, velocidade, velocidadeMax, radioEsfera);
        this.vidas_restantes = 3;
        this.aciertos = 0;
		this.invulnerable = false;
		this.tiempo_ultimo_impacto = 0;
	}

    public void setVelocidade(Vector3 velocidade) {
        this.velocidade = velocidade;
        if ((velocidade.x < (-Math.abs(this.velocidadeMax.x))) || (velocidade.x > Math.abs(this.velocidadeMax.x)))
            this.velocidade.x = velocidadeMax.x;
        if ((velocidade.y < (-Math.abs(this.velocidadeMax.y))) || (velocidade.y > Math.abs(this.velocidadeMax.y)))
            this.velocidade.y = velocidadeMax.y;
    }

	public static int getVidas_restantes() {
		return vidas_restantes;
	}

	public static int getAciertos() {
		return aciertos;
	}

	public static void addAciertos(){
		aciertos++;
	}

	public static void quitarVidas_restantes(){
		vidas_restantes--;
	}

	public static float getTiempo_ultimo_impacto() {
		return tiempo_ultimo_impacto;
	}

	public static void setTiempo_ultimo_impacto(float tiempo_ultimo_impacto) {
		Nave.tiempo_ultimo_impacto = tiempo_ultimo_impacto;
	}
}
