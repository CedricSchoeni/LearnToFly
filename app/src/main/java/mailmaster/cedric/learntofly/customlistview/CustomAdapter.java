package mailmaster.cedric.learntofly.customlistview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import mailmaster.cedric.learntofly.R;

/**
 * Created by Cedric on 06.03.2018.
 */

public class CustomAdapter extends ArrayAdapter<DataModel> implements View.OnClickListener{

    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtType;
        TextView txtVersion;
        TextView txtMass;
        ImageView info;
    }

    public CustomAdapter(ArrayList<DataModel> data, Context context) {
        super(context, R.layout.row_item, data);
        ArrayList<DataModel> dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        DataModel dataModel=(DataModel)object;

        switch (v.getId())
        {
            case R.id.item_info:
                //Snackbar.make(v, "Release date " +dataModel.getFeature(), Snackbar.LENGTH_LONG)
                //      .setAction("No action", null).show();

                //Log.e("SQL-ID", ""+((DataModel) object).getFeature()+"::"+((DataModel) object).getProfileID());
                setProfileVal(dataModel);
                break;
        }
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        DataModel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.txtName = convertView.findViewById(R.id.name);
            viewHolder.txtType = convertView.findViewById(R.id.type);
            viewHolder.txtVersion = convertView.findViewById(R.id.version_number);
            viewHolder.txtMass = convertView.findViewById(R.id.name2);
            viewHolder.info = convertView.findViewById(R.id.item_info);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        //Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        //result.startAnimation(animation);
        int lastPosition = position;

        viewHolder.txtName.setText(dataModel != null ? dataModel.getName() : "");
        viewHolder.txtType.setText(dataModel.getType());
        viewHolder.txtVersion.setText(dataModel.getVersion_number());
        viewHolder.txtMass.setText(dataModel.getMass());
        viewHolder.info.setOnClickListener(this);
        viewHolder.info.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }

    private void setProfileVal(DataModel dataModel){
        switch(dataModel.getProfileID()){
            case 1:
                dataModel.getProfile().stage1=Integer.parseInt(dataModel.getFeature());
                break;
            case 2:
                dataModel.getProfile().stage2=Integer.parseInt(dataModel.getFeature());
                break;
            case 3:
                dataModel.getProfile().stage3=Integer.parseInt(dataModel.getFeature());
                break;
            case 4:
                dataModel.getProfile().stage4=Integer.parseInt(dataModel.getFeature());
                break;
            case 5:
                dataModel.getProfile().boost1=Integer.parseInt(dataModel.getFeature());
                break;
            case 6:
                dataModel.getProfile().boost2=Integer.parseInt(dataModel.getFeature());
                break;
            case 7:
                dataModel.getProfile().boost3=Integer.parseInt(dataModel.getFeature());
                break;
            case 8:
                dataModel.getProfile().boost4=Integer.parseInt(dataModel.getFeature());
                break;
        }
    }
}
