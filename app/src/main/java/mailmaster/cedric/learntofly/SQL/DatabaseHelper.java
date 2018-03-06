package mailmaster.cedric.learntofly.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;
import java.util.List;

import mailmaster.cedric.learntofly.Game.FlightDevices._Boosts.Boost;
import mailmaster.cedric.learntofly.Game.FlightDevices._Stages.Stage;
import mailmaster.cedric.learntofly.R;

/**
 * Created by adriano.campiotti on 06.03.2018.
 * The DatabaseHelper allows access to the SQLite Database without much hassle and contains per-made functions
 * as well as a creation, deletion and base insert script.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private Player player = new Player();
    private Item item = new Item();
    private Inventory inventory = new Inventory();
    private StringBuilder sb = new StringBuilder();
    public DatabaseHelper(Context context) {
        super(context,"Item", null, 1);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sb = new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXISTS ");
        sb.append(player.TABLE+"(");
        sb.append(player.COL1+" INT PRIMARY KEY, ");
        sb.append(player.COL2+" VARCHAR(16), ");
        sb.append(player.COL3+" INT, ");
        sb.append(player.COL4+" INT)");
        sqLiteDatabase.execSQL(sb.toString());
        sb = new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXISTS ");
        sb.append(item.TABLE+"(");
        sb.append(item.COL1+" INT PRIMARY KEY, ");
        sb.append(item.COL2+" VARCHAR(50), ");
        sb.append(item.COL3+" FLOAT, ");
        sb.append(item.COL4+" FLOAT, ");
        sb.append(item.COL5+" FLOAT, ");
        sb.append(item.COL6+" INT, ");
        sb.append(item.COL7+" INT, ");
        sb.append(item.COL8+" INT)");
        sqLiteDatabase.execSQL(sb.toString());
        sb= new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXISTS ");
        sb.append(inventory.TABLE+" (");
        sb.append(inventory.COL1+" INT PRIMARY KEY, ");
        sb.append(inventory.COL2+" INT, ");
        sb.append(inventory.COL3+" INT, ");
        sb.append("FOREIGN KEY ("+inventory.COL2+") ");
        sb.append("REFERENCES "+player.TABLE+"("+player.COL1+"), ");
        sb.append("FOREIGN KEY ("+inventory.COL3+") ");
        sb.append("REFERENCES "+item.TABLE+"("+item.COL1+"))");
        sqLiteDatabase.execSQL(sb.toString());

        insertBaseData(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
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

        onCreate(sqLiteDatabase);
    }

    public List<Stage> getAllStages(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Stage> stages= new ArrayList<>();

        sb = new StringBuilder();
        sb.append("SELECT * FROM "+item.TABLE);
        sb.append(" WHERE "+item.COL8+" = 1");

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

    public List<Boost> getAllBoosts(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Boost> boosts= new ArrayList<>();

        sb = new StringBuilder();
        sb.append("SELECT * FROM "+item.TABLE);
        sb.append(" WHERE "+item.COL8+" = 0");

        Cursor cursor = db.rawQuery(sb.toString(), null);
        if(cursor.moveToFirst()){
            do{
                try{Boost boost = new Boost();
                boost.setId(cursor.getInt(0));
                boost.setName(cursor.getString(1));
                boost.setPower(cursor.getFloat(2));
                boost.setFuel(cursor.getFloat(3));
                boost.setMass(cursor.getFloat(4));
                boost.setModel(cursor.getInt(5));
                boost.setPrice(cursor.getInt(6));
                boosts.add(boost);}catch (Exception e){exceptionHandle();}
            }while(cursor.moveToNext());
        }
        return boosts;
    }

    private void insertBaseData(SQLiteDatabase sqLiteDatabase){
        insertBaseStages(sqLiteDatabase);
        insertBaseBoosts(sqLiteDatabase);
    }

    private void insertBaseStages(SQLiteDatabase sqLiteDatabase){
        ContentValues values = new ContentValues();
        values.put(item.COL2,"Super Rocket 5000");
        values.put(item.COL3,(float)100);
        values.put(item.COL4,(float)1250);
        values.put(item.COL5,(float)10);
        values.put(item.COL6, R.drawable.rocket_v1);
        values.put(item.COL7,50);
        values.put(item.COL8,1);
        sqLiteDatabase.insert(item.TABLE,null,values);
        values.clear();
        values.put(item.COL2,"Super Rocket 5001");
        values.put(item.COL3,(float)125);
        values.put(item.COL4,(float)1050);
        values.put(item.COL5,(float)15);
        values.put(item.COL6, R.drawable.rocket_v2);
        values.put(item.COL7,66);
        values.put(item.COL8,1);
        sqLiteDatabase.insert(item.TABLE,null,values);
        values.clear();
        values.put(item.COL2,"North Korea Rocket");
        values.put(item.COL3,(float)200);
        values.put(item.COL4,(float)300);
        values.put(item.COL5,(float)18);
        values.put(item.COL6, R.drawable.rocket_v2);
        values.put(item.COL7,30);
        values.put(item.COL8,1);
        sqLiteDatabase.insert(item.TABLE,null,values);
    }

    private void insertBaseBoosts(SQLiteDatabase sqLiteDatabase){
        ContentValues values = new ContentValues();
        values.put(item.COL2,"Super Booster 2018");
        values.put(item.COL3,(float)50);
        values.put(item.COL4,(float)12500);
        values.put(item.COL5,(float)7.5);
        values.put(item.COL6, R.drawable.rocket_v2);
        values.put(item.COL7,70);
        values.put(item.COL8,0);
        sqLiteDatabase.insert(item.TABLE,null,values);
        values.clear();
        values.put(item.COL2,"Super Booster 1942");
        values.put(item.COL3,(float)90);
        values.put(item.COL4,(float)8500);
        values.put(item.COL5,(float)8.8);
        values.put(item.COL6, R.drawable.rocket_v2);
        values.put(item.COL7,420);
        values.put(item.COL8,0);
        sqLiteDatabase.insert(item.TABLE,null,values);
        values.clear();
        values.put(item.COL2,"Illuminated Booster v0.3B");
        values.put(item.COL3,(float)30);
        values.put(item.COL4,(float)15000);
        values.put(item.COL5,(float)5);
        values.put(item.COL6, R.drawable.rocket_v2);
        values.put(item.COL7,40);
        values.put(item.COL8,0);
        sqLiteDatabase.insert(item.TABLE,null,values);
    }
    private void exceptionHandle(){
        this.onUpgrade(this.getWritableDatabase(),0, 1);
    }

}
