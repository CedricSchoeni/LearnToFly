package mailmaster.cedric.learntofly.Physics;


/**
 * Created by cedric.schoeni on 02.03.2018.
 */

public class FVector {

    public float x;
    public float y;

    public FVector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void set(float x, float y){
        this.x = x;
        this.y = y;
    }

    public void add(FVector fv){
        this.x += fv.x;
        this.y += fv.y;
    }

    public void sub(FVector fv){
        this.x -= fv.x;
        this.y -= fv.y;
    }

    public void mult(float scale){
        this.x *= scale;
        this.y *= scale;
    }

    public void scale(FVector fv){
        this.x *= fv.x;
        this.y *= fv.y;
    }

    public void reset(){
        this.x = 0;
        this.y = 0;
    }

    /**
     * Length of the vector - Magnitude
     * a² + b² = c²
     */
    public float mag(){
        return (float)(Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)));
    }
    private float direction;



}
