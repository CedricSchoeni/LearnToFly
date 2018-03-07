package mailmaster.cedric.learntofly.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;

import mailmaster.cedric.learntofly.game.flightdevices.boosts.Boost;
import mailmaster.cedric.learntofly.game.flightdevices.stages.Stage;
import mailmaster.cedric.learntofly.R;

/**
 * Created by adriano.campiotti on 06.03.2018.
 * The DatabaseHelper allows access to the SQLite Database without much hassle and contains per-made functions
 * as well as a creation, deletion and base insert script.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private Player player = new Player();
    private Item item = new Item();
    private Inventory inventory = new Inventory();
    private Image image = new Image();
    private StringBuilder sb = new StringBuilder();
    public DatabaseHelper(Context context) {
        super(context,"Item", null, 1);
        Context context1 = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sb = new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXISTS ");
        sb.append(player.TABLE+"(");
        sb.append(player.COL1+" INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sb.append(player.COL2+" VARCHAR(16), ");
        sb.append(player.COL3+" INT, ");
        sb.append(player.COL4+" INT)");
        sqLiteDatabase.execSQL(sb.toString());
        sb = new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXISTS ");
        sb.append(item.TABLE+"(");
        sb.append(item.COL1+" INTEGER PRIMARY KEY AUTOINCREMENT, ");
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
        sb.append(inventory.TABLE+"(");
        sb.append(inventory.COL1+" INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sb.append(inventory.COL2+" INT, ");
        sb.append(inventory.COL3+" INT, ");
        sb.append("FOREIGN KEY ("+inventory.COL2+") ");
        sb.append("REFERENCES "+player.TABLE+"("+player.COL1+"), ");
        sb.append("FOREIGN KEY ("+inventory.COL3+") ");
        sb.append("REFERENCES "+item.TABLE+"("+item.COL1+"))");
        sqLiteDatabase.execSQL(sb.toString());
        sb = new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXISTS ");
        sb.append(image.TABLE+"(");
        sb.append(image.COL1+" INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sb.append(image.COL2+" INT, ");
        sb.append(image.COL3+" INT, ");
        sb.append(image.COL4+" INT, ");
        sb.append(image.COL5+" INT, ");
        sb.append(image.COL6+" INT, ");
        sb.append("FOREIGN KEY("+image.COL2+")");
        sb.append(" REFERENCES "+item.TABLE+"("+item.COL1+"))");
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
        sb.append(image.TABLE);
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
        sb.append(" JOIN "+image.TABLE+" ON ");
        sb.append(item.TABLE+"."+item.COL1+"=");
        sb.append(image.TABLE+"."+image.COL2);
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
                stage.setModel(cursor.getInt(5),null);
                stage.setPrice(cursor.getInt(6));
                stage.setImage1(cursor.getInt(10));
                stage.setImage2(cursor.getInt(11));
                stage.setImage3(cursor.getInt(12));
                stage.setImage4(cursor.getInt(13));
                stages.add(stage);
                //Log.e("stageImage1",""+stage.getImage1());
                //Log.e("curosrImage1",""+cursor.getInt(10));
            }while(cursor.moveToNext());
        }
        cursor.close();
        return stages;
    }

    public Stage getStage(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Stage stage = new Stage();
        sb = new StringBuilder();
        sb.append("SELECT * FROM "+item.TABLE);
        sb.append(" JOIN "+image.TABLE+" ON ");
        sb.append(item.TABLE+"."+item.COL1+"=");
        sb.append(image.TABLE+"."+image.COL2);
        sb.append(" WHERE "+item.COL8+" = 1 and ");
        sb.append(item.TABLE+"."+item.COL1 + " = ").append(id);
        //Log.e("getStage(id:",id+")");
        Cursor cursor = db.rawQuery(sb.toString(), null);
        if(cursor.moveToFirst()){
            stage.setId(cursor.getInt(0));
            stage.setName(cursor.getString(1));
            stage.setPower(cursor.getFloat(2));
            stage.setFuel(cursor.getFloat(3));
            stage.setMass(cursor.getFloat(4));
            stage.setModel(cursor.getInt(5),null);
            stage.setPrice(cursor.getInt(6));
            stage.setImage1(cursor.getInt(10));
            stage.setImage2(cursor.getInt(11));
            stage.setImage3(cursor.getInt(12));
            stage.setImage4(cursor.getInt(13));
            //Log.e("Drawable:",""+cursor.getInt(5));
        }
        cursor.close();
        return stage;
    }

    public List<Boost> getAllBoosts(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Boost> boosts= new ArrayList<>();

        sb = new StringBuilder();
        sb.append("SELECT * FROM "+item.TABLE);
        sb.append(" JOIN "+image.TABLE+" ON ");
        sb.append(item.TABLE+"."+item.COL1+"=");
        sb.append(image.TABLE+"."+image.COL2);
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
                boost.setModel(cursor.getInt(5),null);
                boost.setPrice(cursor.getInt(6));
                boost.setImage1(cursor.getInt(10));
                boost.setImage2(cursor.getInt(11));
                boost.setImage3(cursor.getInt(12));
                boost.setImage4(cursor.getInt(13));
                boosts.add(boost);}catch (Exception e){exceptionHandle();}
            }while(cursor.moveToNext());
        }
        cursor.close();
        return boosts;
    }

    public Boost getBoost(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Boost boost = new Boost();
        sb = new StringBuilder();
        sb.append("SELECT * FROM "+item.TABLE);
        sb.append(" JOIN "+image.TABLE+" ON ");
        sb.append(item.TABLE+"."+item.COL1+"=");
        sb.append(image.TABLE+"."+image.COL2);
        sb.append(" WHERE "+item.COL8+" = 0 and ");
        sb.append(item.TABLE+"."+item.COL1 + " = ").append(id);
        Cursor cursor = db.rawQuery(sb.toString(), null);
        if(cursor.moveToFirst()){
            boost.setId(cursor.getInt(0));
            boost.setName(cursor.getString(1));
            boost.setPower(cursor.getFloat(2));
            boost.setFuel(cursor.getFloat(3));
            boost.setMass(cursor.getFloat(4));
            boost.setModel(cursor.getInt(5),null);
            boost.setPrice(cursor.getInt(6));
            boost.setImage1(cursor.getInt(10));
            boost.setImage2(cursor.getInt(11));
            boost.setImage3(cursor.getInt(12));
            boost.setImage4(cursor.getInt(13));
        }
        cursor.close();
        return boost;
    }


    private void insertBaseData(SQLiteDatabase sqLiteDatabase){
        insertBaseStages(sqLiteDatabase);
        insertBaseBoosts(sqLiteDatabase);
        insertBaseImages(sqLiteDatabase);
    }

    private void insertBaseStages(SQLiteDatabase sqLiteDatabase){
        ContentValues values = new ContentValues();
        values.put(item.COL2,"Super Red Rockets");
        values.put(item.COL3,(float)85);
        values.put(item.COL4,(float)1250);
        values.put(item.COL5,(float)12);
        values.put(item.COL6, R.drawable.rocket_icon);
        values.put(item.COL7,50);
        values.put(item.COL8,1);
        sqLiteDatabase.insert(item.TABLE,null,values);
        values.clear();
        values.put(item.COL2,"Magical Blue Rockets");
        values.put(item.COL3,(float)60);
        values.put(item.COL4,(float)2000);
        values.put(item.COL5,(float)8);
        values.put(item.COL6, R.drawable.rocket_blue_icon);
        values.put(item.COL7,30);
        values.put(item.COL8,1);
        sqLiteDatabase.insert(item.TABLE,null,values);
    }

    private void insertBaseBoosts(SQLiteDatabase sqLiteDatabase){
        ContentValues values = new ContentValues();
        values.put(item.COL2,"Apache Propellers");
        values.put(item.COL3,(float)50);
        values.put(item.COL4,(float)15000);
        values.put(item.COL5,(float)5);
        values.put(item.COL6, R.drawable.propeller_icon);
        values.put(item.COL7,70);
        values.put(item.COL8,0);
        sqLiteDatabase.insert(item.TABLE,null,values);
        values.clear();
        values.put(item.COL2,"Dual Torpedo");
        values.put(item.COL3,(float)90);
        values.put(item.COL4,(float)8500);
        values.put(item.COL5,(float)8.8);
        values.put(item.COL6, R.drawable.dual_torpedo_icon);
        values.put(item.COL7,420);
        values.put(item.COL8,0);
        sqLiteDatabase.insert(item.TABLE,null,values);
        values.clear();

    }
    private void insertBaseImages(SQLiteDatabase sqLiteDatabase){
        ContentValues values = new ContentValues();
        values.put(image.COL2,1);
        values.put(image.COL3,R.drawable.rocket_1);
        values.put(image.COL4,R.drawable.rocket_2);
        values.put(image.COL5,R.drawable.rocket_3);
        values.put(image.COL6,R.drawable.rocket_4);
        sqLiteDatabase.insert(image.TABLE,null,values);
        values.clear();
        values.put(image.COL2,2);
        values.put(image.COL3,R.drawable.rocket_blue_1);
        values.put(image.COL4,R.drawable.rocket_blue_2);
        values.put(image.COL5,R.drawable.rocket_blue_3);
        values.put(image.COL6,R.drawable.rocket_blue_4);
        sqLiteDatabase.insert(image.TABLE,null,values);
        values.clear();
        values.put(image.COL2,3);
        values.put(image.COL3,R.drawable.propeller_1);
        values.put(image.COL4,R.drawable.propeller_2);
        values.put(image.COL5,R.drawable.propeller_3);
        values.put(image.COL6,R.drawable.propeller_4);
        sqLiteDatabase.insert(image.TABLE,null,values);
        values.clear();
        values.put(image.COL2,4);
        values.put(image.COL3,R.drawable.dual_torpedo_1);
        values.put(image.COL3,R.drawable.dual_torpedo_2);
        values.put(image.COL3,R.drawable.dual_torpedo_3);
        values.put(image.COL3,R.drawable.dual_torpedo_4);
        sqLiteDatabase.insert(image.TABLE,null,values);
        values.clear();
    }
    private void exceptionHandle(){
        this.onUpgrade(this.getWritableDatabase(),0, 1);
    }

}
