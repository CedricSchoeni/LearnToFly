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

import java.util.ArrayList;
import java.util.List;

import mailmaster.cedric.learntofly.customlistview.CustomAdapter;
import mailmaster.cedric.learntofly.customlistview.DataModel;
import mailmaster.cedric.learntofly.game.flightdevices.boosts.Boost;
import mailmaster.cedric.learntofly.game.flightdevices.stages.Stage;
import mailmaster.cedric.learntofly.game.Profile;
import mailmaster.cedric.learntofly.sql.DatabaseHelper;


/**
 * Created by cedric.schoeni on 06.03.2018.
 * The Shop activity contains the selection Menu prior to actually starting the game where
 * a play can choose whatever they desire out of the available items to fill their stage and boost slots with.
 */

public class Shop extends AppCompatActivity {

    private ArrayList<DataModel> dataModels;
    private ListView listView;
    private final Profile profile = new Profile();
    private int current=-1;
    private final DatabaseHelper dbhelper=new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        listView=findViewById(R.id.list);
        //final DatabaseHelper dbhelper = new DatabaseHelper(this);
        dbhelper.onUpgrade(dbhelper.getWritableDatabase(),0,1);
        dataModels= new ArrayList<>();

        listView.setVisibility(View.INVISIBLE);

        // https://www.journaldev.com/10416/android-listview-with-custom-adapter-example-tutorial

        ImageButton play = findViewById(R.id.imageButton);

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
        addFunctionality(tmp,1);
        addFunctionality((Button)findViewById(R.id.btnStage2),2);
        addFunctionality((Button)findViewById(R.id.btnStage3),3);
        addFunctionality((Button)findViewById(R.id.btnStage4),4);
        addFunctionality((Button)findViewById(R.id.btnBoost1),5);
        addFunctionality((Button)findViewById(R.id.btnBoost2),6);
        addFunctionality((Button)findViewById(R.id.btnBoost3),7);
        addFunctionality((Button)findViewById(R.id.btnBoost4),8);


    }

    private void setStagesList(int profile){
        List<Stage> stages = dbhelper.getAllStages();
        for (int i = 0; i < stages.size(); i++) {
            Stage stage = stages.get(i);
            dataModels.add(new DataModel(stage.getName(),
                    "Fuel: "+stage.getFuel(),
                    "Power:"+stage.getPower(),
                    ""+stage.getId(),
                    "Mass:"+stage.getMass(),
                    profile,
                    this.profile));
            //Log.e("ID", Integer.toString(stage.getDrawable()));
        }
    }

    private void setBoostsList(int profile){
        List<Boost> boosts = dbhelper.getAllBoosts();
        for (int i = 0; i < boosts.size(); i++) {
            Boost boost = boosts.get(i);
            dataModels.add(new DataModel(boost.getName(),
                    "Fuel: "+boost.getFuel(),
                    "Power:"+boost.getPower(),
                    ""+boost.getId(),
                    "Mass:"+boost.getMass(),
                    profile,
                    this.profile));
            //Log.e("ID",""+boost.getId());
        }
    }

    private void setNewAdapter(){

        CustomAdapter adapter = new CustomAdapter(dataModels, getApplicationContext());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DataModel dataModel= dataModels.get(position);
                Log.e("Item", dataModel.getId());
                setProfileVal(Integer.parseInt(dataModel.getId()));
                listView.setVisibility(View.GONE);
            }
        });
    }

    private void addFunctionality(Button tmp, final int current){
        this.current=current;
        if(current>0&&current<=4){
            tmp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dataModels.clear();
                    listView.setVisibility(View.VISIBLE);

                    setStagesList(current);
                    setNewAdapter();
                    listView.setOnItemClickListener(null);
                }
            });
        }else{
            tmp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dataModels.clear();
                    listView.setVisibility(View.VISIBLE);

                    setBoostsList(current);
                    setNewAdapter();
                    listView.setOnItemClickListener(null);
                }
            });

        }



    }
    private void setProfileVal(int ID){
        switch(this.current){
            case 1:
                profile.stage1=ID;
                break;
            case 2:
                profile.stage2=ID;
                break;
            case 3:
                profile.stage3=ID;
                break;
            case 4:
                profile.stage4=ID;
                break;
            case 5:
                profile.boost1=ID;
                break;
            case 6:
                profile.boost2=ID;
                break;
            case 7:
                profile.boost3=ID;
                break;
            case 8:
                profile.boost4=ID;
                break;
        }
    }



}
