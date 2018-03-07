package mailmaster.cedric.learntofly.sql;

/**
 * Created by adriano.campiotti on 06.03.2018.
 * Holds the table name as well as all column names of the item table
 * for easy permanent access and not static to prevent unnecessary usage of RAM.
 */

class Item {

    final String TABLE="Item";
    final String COL1="ID";
    final String COL2="name";
    final String COL3="power";
    final String COL4="fuel";
    final String COL5="mass";
    final String COL6="image";
    final String COL7="price";
    final String COL8="type";
}