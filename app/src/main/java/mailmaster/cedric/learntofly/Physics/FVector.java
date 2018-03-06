package mailmaster.cedric.learntofly.Physics;


/**
 * Created by cedric.schoeni on 02.03.2018.
 * This is a 2 Dimensional Vector class with the methods required to be used by the game.
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

    /**
     * Adds the x and y of another FVector to this one.
     * @param fv FVector to use the values of.
     */
    public void add(FVector fv){
        this.x += fv.x;
        this.y += fv.y;
    }

    /**
     * Subtracts the x and y of another FVector to this one.
     * @param fv FVector to use the values of.
     */
    public void sub(FVector fv){
        this.x -= fv.x;
        this.y -= fv.y;
    }

    /**
     * Scales the x and y of this FVector by the scale given.
     * @param scale float which is used to scale this FVector's values.
     */
    public void mult(float scale){
        this.x *= scale;
        this.y *= scale;
    }

    /**
     * Scales this FVector's values by another FVector's values (respectively, so x*=fv.x & y*=fv.y)
     * @param fv FVector to use the values of.
     */
    public void scale(FVector fv){
        this.x *= fv.x;
        this.y *= fv.y;
    }

    /**
     * Sets the FVector's x & y to 0.
     */
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
