package mailmaster.cedric.learntofly.sql;

/**
 * Created by adriano.campiotti on 06.03.2018.
 * Holds the table name as well as all column names of the inventory table
 * for easy permanent access and not static to prevent unnecessary usage of RAM.
 */

class Inventory {
    final String TABLE="Inventory";
    final String COL1="ID";
    final String COL2="playerFK";
    final String COL3="itemFK";
}
