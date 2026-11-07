package misiones;

public class Coordenada {
    private final int x;
    private final int y;

    public Coordenada(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Coordenada otra = (Coordenada) obj;
        return this.x == otra.x && this.y == otra.y;
    }
}
