package mailmaster.cedric.learntofly.SQL;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import mailmaster.cedric.learntofly.Game.FlightDevices.FlightDevice;
import mailmaster.cedric.learntofly.Game.FlightDevices._Stages.Stage_Rocket_v1;

/**
 * Created by adriano.campiotti on 06.03.2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    Player player = new Player();
    Item item = new Item();
    Inventory inventory = new Inventory();
    StringBuilder sb = new StringBuilder();
    public DatabaseHelper(Context context) {
        super(context,"Player", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sb = new StringBuilder();
        sb.append("CREATE TABLE ");
        sb.append(player.TABLE+"(");
        sb.append(player.COL1+" INT PRIMARY KEY, ");
        sb.append(player.COL2+" VARCHAR(16), ");
        sb.append(player.COL3+" INT, ");
        sb.append(player.COL4+" INT);");
        sqLiteDatabase.execSQL(sb.toString());
        sb = new StringBuilder();
        sb.append("CREATE TABLE ");
        sb.append(item.TABLE+"(");
        sb.append(item.COL1+" INT PRIMARY KEY, ");
        sb.append(item.COL2+" VARCHAR(50), ");
        sb.append(item.COL3+" FLOAT, ");
        sb.append(item.COL4+" FLOAT, ");
        sb.append(item.COL5+" FLOAT, ");
        sb.append(item.COL6+" INT) ");
        sqLiteDatabase.execSQL(sb.toString());
        sb= new StringBuilder();
        sb.append("CREATE TABLE ");
        sb.append(inventory.TABLE+" (");
        sb.append(inventory.COL1+" INT PRIMARY KEY, ");
        sb.append(inventory.COL2+" INT, ");
        sb.append(inventory.COL3+" INT, ");
        sb.append("FOREIGN KEY ("+inventory.COL2+") ");
        sb.append("REFERENCES "+player.TABLE+"("+player.COL1+"), ");
        sb.append("FOREIGN KEY ("+inventory.COL3+") ");
        sb.append("REFERENCES "+item.TABLE+"("+item.COL1+"));");
        sqLiteDatabase.execSQL(sb.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sb = new StringBuilder();
        sb.append("DROP TABLE IF EXISTS ");
        sb.append(inventory.TABLE+";");
        sqLiteDatabase.execSQL(sb.toString());
        sb = new StringBuilder();
        sb.append("DROP TABLE IF EXISTS ");
        sb.append(player.TABLE+";");
        sqLiteDatabase.execSQL(sb.toString());
        sb = new StringBuilder();
        sb.append("DROP TABLE IF EXISTS ");
        sb.append(item.TABLE+";");
        sqLiteDatabase.execSQL(sb.toString());
    }


}
