package Engine.Structures;

public class Vector2D {
    public double x;
    public double y;


    public Vector2D() {
        x = y = 0;
    }
    
    public Vector2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setCoord(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D add(Vector2D other) {
        return new Vector2D(this.x + other.getX(), this.y + other.getY());
    }

    public Vector2D subtract(Vector2D other) {
        return new Vector2D(this.x - other.getX(), this.y - other.getY());
    }

    public Vector2D multiply(int scalar) {
        return new Vector2D(this.x * scalar, this.y * scalar);
    }

    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }

    @Override
    public Vector2D clone() {
        return new Vector2D(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}