package modelo;

import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class Mundo {
    public static float cronometro;
    private ArrayList<Suelo> suelos;
    private ArrayList<Enemigo> enemigos;
    private Nave nave;
    private MovilMax disparo;

    public Mundo() {
        this.nave = new Nave(new Vector3(0, 40, 25f), 20f, new Vector3(0, 0, 0), new Vector3(200f, 0, 0), 30);
        this.disparo= new MovilMax(new Vector3(0, 30, 25), 1f, new Vector3(0, 0, 0), new Vector3(0f, 0, 200f),20);
        this.iniciarSuelo();
        this.iniciarEnemigo();
        this.cronometro = 120;
    }

    public static float getCronometro() {
		return cronometro;
	}

    public static int getCronometroInt() {
		return (int)cronometro;
	}

    private void iniciarSuelo() {
        suelos = new ArrayList<Suelo>();
        float px = 0;
        Suelo e;
        for (int i = 0; i <= 7; i++) {
            px = ((float) Math.random() * 250f - 125);
            e = new Suelo(new Vector3(px - 800, -200f, (1000f - i * 200)), 20f, new Vector3(0, 0, -80f), new Vector3(0, 0, -450f), 1);
            suelos.add(e);
            e = new Suelo(new Vector3(px - 400, -200f, (1000f - i * 200)), 20f, new Vector3(0, 0, -80f), new Vector3(0, 0, -450f), 1);
            suelos.add(e);
            e = new Suelo(new Vector3(px, -200f, (1000f - i * 200)), 20f, new Vector3(0, 0, -80f), new Vector3(0, 0, -450f), 30);
            suelos.add(e);
            e = new Suelo(new Vector3(px + 400, -200f, (1000f - i * 200)), 20f, new Vector3(0, 0, -80f), new Vector3(0, 0, -450f), 1);
            suelos.add(e);
            e = new Suelo(new Vector3(px + 800, -200f, (1000f - i * 200)), 20f, new Vector3(0, 0, -80f), new Vector3(0, 0, -450f), 1);
            suelos.add(e);
        }
    }

    private void iniciarEnemigo() {
        enemigos = new ArrayList<Enemigo>();
        for (int i = 0; i <= 8; i++) {
            Enemigo e = new Enemigo(new Vector3(((float) Math.random() * 300f - 150), 30f, (850f - i * 100)), 0.4f, new Vector3(0, 0, (-80f)), new Vector3(0, 0, -450f), 1.5f);
            enemigos.add(e);
        }
    }

    public ArrayList<Suelo> getSuelos() {
        return suelos;
    }

    public ArrayList<Enemigo> getEnemigos() {
        return enemigos;
    }

    public Nave getNave() {
        return nave;
    }

    public MovilMax getDisparo() {
        return this.disparo;
    }
}
