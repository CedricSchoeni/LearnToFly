package mailmaster.cedric.learntofly;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import mailmaster.cedric.learntofly.CustomListView.CustomAdapter;
import mailmaster.cedric.learntofly.CustomListView.DataModel;
import mailmaster.cedric.learntofly.Game.FlightDevices._Stages.Stage;
import mailmaster.cedric.learntofly.SQL.DatabaseHelper;

/**
 * Created by cedric.schoeni on 06.03.2018.
 */

public class Shop extends AppCompatActivity {

    ArrayList<DataModel> dataModels;
    ListView listView;
    private static CustomAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        listView=(ListView)findViewById(R.id.list);
        DatabaseHelper dbhelper = new DatabaseHelper(this);
        dbhelper.onUpgrade(dbhelper.getWritableDatabase(),0,1);
        List<Stage> stages = dbhelper.getAllStages();
        dataModels= new ArrayList<>();
        dataModels.add(new DataModel("Empty","none","none","none"));
        dataModels.add(new DataModel("Empty","none","none","none"));
        dataModels.add(new DataModel("Empty","none","none","none"));
        for (int i = 0; i < stages.size(); i++) {
            Stage stage = stages.get(i);
            dataModels.add(new DataModel(stage.getName(),
                    "Fuel: "+stage.getFuel(),
                    "Power:"+stage.getPower(),
                    "Mass:"+stage.getMass()));
        }
        listView.setVisibility(View.INVISIBLE);
        adapter= new CustomAdapter(dataModels,getApplicationContext());

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DataModel dataModel= dataModels.get(position);

                //Snackbar.make(view, dataModel.getName()+"\n"+dataModel.getType()+" API: "+dataModel.getVersion_number(), Snackbar.LENGTH_LONG)
                //        .setAction("No action", null).show();
                //Log.e("Item", "Hello");
            }
        });


        // https://www.journaldev.com/10416/android-listview-with-custom-adapter-example-tutorial

        ImageButton play = (ImageButton) findViewById(R.id.imageButton);

        play.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Shop.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }


}
