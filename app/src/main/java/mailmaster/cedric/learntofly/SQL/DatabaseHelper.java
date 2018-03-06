package mailmaster.cedric.learntofly.SQL;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by adriano.campiotti on 06.03.2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    Player player = new Player();
    Item item = new Item();
    Inventory inventory = new Inventory();
    public DatabaseHelper(Context context) {
        super(context,"Player", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String tmp = "CREATE TABLE "+player.TABLE+"(";
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
