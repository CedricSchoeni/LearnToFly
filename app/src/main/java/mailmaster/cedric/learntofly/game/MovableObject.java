package mailmaster.cedric.learntofly.game;

/**
 * Created by Cedric on 03.03.2018.
 * This Interface was created to make it easier to move multiple different objects on the screen
 * Due to there only being 2 classes which implement this Interface in the current version pretty useless.
 */

public interface MovableObject {

    void addRotation(float r);
    void setRotation(float r);

}
