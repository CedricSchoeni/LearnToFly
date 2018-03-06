package mailmaster.cedric.learntofly.SQL;

/**
 * Created by adriano.campiotti on 06.03.2018.
 * Holds the table name as well as all column names of the player table
 * for easy permanent access and not static to prevent unnecessary usage of RAM.
 */

class Player {

    final String TABLE="Player";
    final String COL1="ID";
    final String COL2="name";
    final String COL3="money";
    final String COL4="highscore";
}
