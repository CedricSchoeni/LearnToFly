package mailmaster.cedric.learntofly.SQL;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import mailmaster.cedric.learntofly.Game.FlightDevices.FlightDevice;
import mailmaster.cedric.learntofly.Game.FlightDevices._Stages.Stage;
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
        sb.append(player.COL4+" INT)");
        sqLiteDatabase.execSQL(sb.toString());
        sb = new StringBuilder();
        sb.append("CREATE TABLE ");
        sb.append(item.TABLE+"(");
        sb.append(item.COL1+" INT PRIMARY KEY, ");
        sb.append(item.COL2+" VARCHAR(50), ");
        sb.append(item.COL3+" FLOAT, ");
        sb.append(item.COL4+" FLOAT, ");
        sb.append(item.COL5+" FLOAT, ");
        sb.append(item.COL6+" INT, ");
        sb.append(item.COL7+" INT)");
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
        sb.append("REFERENCES "+item.TABLE+"("+item.COL1+"))");
        sqLiteDatabase.execSQL(sb.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sb = new StringBuilder();
        sb.append("DROP TABLE IF EXISTS ");
        sb.append(inventory.TABLE);
        sqLiteDatabase.execSQL(sb.toString());
        sb = new StringBuilder();
        sb.append("DROP TABLE IF EXISTS ");
        sb.append(player.TABLE);
        sqLiteDatabase.execSQL(sb.toString());
        sb = new StringBuilder();
        sb.append("DROP TABLE IF EXISTS ");
        sb.append(item.TABLE);
        sqLiteDatabase.execSQL(sb.toString());
    }

    public List<Stage> getAllStages(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Stage> stages= new ArrayList<Stage>();

        sb = new StringBuilder();
        sb.append("SELECT * FROM "+item.TABLE);

        Cursor cursor = db.rawQuery(sb.toString(), null);
        if(cursor.moveToFirst()){
            do{
                Stage stage = new Stage();
                stage.setId(cursor.getInt(0));
                stage.setName(cursor.getString(1));
                stage.setPower(cursor.getFloat(2));
                stage.setFuel(cursor.getFloat(3));
                stage.setMass(cursor.getFloat(4));
                stage.setModel(cursor.getInt(5));
                stage.setPrice(cursor.getInt(6));
                stages.add(stage);
            }while(cursor.moveToNext());
        }
        return stages;
    }


}
