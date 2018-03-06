package mailmaster.cedric.learntofly;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import mailmaster.cedric.learntofly.CustomListView.CustomAdapter;
import mailmaster.cedric.learntofly.CustomListView.DataModel;
import mailmaster.cedric.learntofly.Game.FlightDevices._Boosts.Boost;
import mailmaster.cedric.learntofly.Game.FlightDevices._Stages.Stage;
import mailmaster.cedric.learntofly.Game.Profile;
import mailmaster.cedric.learntofly.SQL.DatabaseHelper;

/**
 * Created by cedric.schoeni on 06.03.2018.
 */

public class Shop extends AppCompatActivity {

    ArrayList<DataModel> dataModels;
    ListView listView;
    private static CustomAdapter adapter;
    private Profile profile = new Profile();
    private String current="";
    final DatabaseHelper dbhelper=new DatabaseHelper(this);

    //TODO get the extra info of the intent from the MainActivity into the Player class
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        listView=(ListView)findViewById(R.id.list);
        //final DatabaseHelper dbhelper = new DatabaseHelper(this);
        //dbhelper.onUpgrade(dbhelper.getWritableDatabase(),0,1);
        dataModels= new ArrayList<>();
        dataModels.add(new DataModel("Empty","none","none","none"));
        dataModels.add(new DataModel("Empty","none","none","none"));
        dataModels.add(new DataModel("Empty","none","none","none"));

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
                intent.putExtra("boost1",profile.boost1);
                intent.putExtra("boost2",profile.boost2);
                intent.putExtra("boost3",profile.boost3);
                intent.putExtra("boost4",profile.boost4);
                intent.putExtra("stage1",profile.stage1);
                intent.putExtra("stage2",profile.stage2);
                intent.putExtra("stage3",profile.stage3);
                intent.putExtra("stage4",profile.stage4);
                startActivity(intent);
            }
        });
        Button tmp = findViewById(R.id.btnStage1);
        addFunctionality(tmp,"stage1");
        tmp=findViewById(R.id.btnStage2);
        addFunctionality(tmp,"stage2");
        tmp=findViewById(R.id.btnStage3);
        addFunctionality(tmp,"stage3");
        tmp=findViewById(R.id.btnStage4);
        addFunctionality(tmp,"stage4");



    }
    private void setStagesList(){
        List<Stage> stages = dbhelper.getAllStages();
        for (int i = 0; i < stages.size(); i++) {
            Stage stage = stages.get(i);
            dataModels.add(new DataModel(stage.getName(),
                    "Fuel: "+stage.getFuel(),
                    "Power:"+stage.getPower(),
                    "Mass:"+stage.getMass()));
        }
    }
    private void setBoostsList(){
        List<Boost> boosts = dbhelper.getAllBoosts();
        for (int i = 0; i < boosts.size(); i++) {
            Boost boost = boosts.get(i);
            dataModels.add(new DataModel(boost.getName(),
                    "Fuel: "+boost.getFuel(),
                    "Power:"+boost.getPower(),
                    "Mass:"+boost.getMass()));
        }
    }
    private void addFunctionality(Button tmp, String current){
        this.current=current;
        listView.clearChoices();
        if(current.contains("stage"))
            setBoostsList();
        else
            setStagesList();
        tmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listView.setVisibility(View.VISIBLE);
                listView.bringToFront();
            }
        });
    listView.setOnItemClickListener(null);
    }



}
