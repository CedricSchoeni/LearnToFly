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
 * The CustomAdapter is the class holding together the custom list with it's DataModel Entries.
 * It's used to display a DataModel List as a list in the shop.
 */

public class CustomAdapter extends ArrayAdapter<DataModel> implements View.OnClickListener{

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtFuel;
        TextView txtPower;
        TextView txtMass;
        ImageView info;
    }

    public CustomAdapter(ArrayList<DataModel> data, Context context) {
        super(context, R.layout.row_item, data);

    }

    /**
     * Adds the functionality to the select buttons on each list entry.
     * @param v view as in selected Data Model list entry.
     */
    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        DataModel dataModel=(DataModel)object;

        switch (v.getId())
        {
            case R.id.item_info:
                //Snackbar.make(v, "Release date " +dataModel.getId(), Snackbar.LENGTH_LONG)
                //      .setAction("No action", null).show();

                //Log.e("SQL-ID", ""+((DataModel) object).getId()+"::"+((DataModel) object).getProfileID());
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

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.txtName = convertView.findViewById(R.id.name);
            viewHolder.txtFuel = convertView.findViewById(R.id.fuel);
            viewHolder.txtPower = convertView.findViewById(R.id.power);
            viewHolder.txtMass = convertView.findViewById(R.id.name2);
            viewHolder.info = convertView.findViewById(R.id.item_info);



            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        //result.startAnimation(animation);

        viewHolder.txtName.setText(dataModel != null ? dataModel.getName() : "");
        viewHolder.txtFuel.setText(dataModel != null ? dataModel.getFuel() : "");
        viewHolder.txtPower.setText(dataModel != null ? dataModel.getPower() : "");
        viewHolder.txtMass.setText(dataModel != null ? dataModel.getMass() : "");
        viewHolder.info.setOnClickListener(this);
        viewHolder.info.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }

    /**
     * setProfileVal sets the correct Profile variable with the selected entry's SQL ID
     * @param dataModel dataModel which was selected and which it gets the info from.
     */
    private void setProfileVal(DataModel dataModel){
        switch(dataModel.getProfileID()){
            case 1:
                dataModel.getProfile().stage1=Integer.parseInt(dataModel.getId());
                break;
            case 2:
                dataModel.getProfile().stage2=Integer.parseInt(dataModel.getId());
                break;
            case 3:
                dataModel.getProfile().stage3=Integer.parseInt(dataModel.getId());
                break;
            case 4:
                dataModel.getProfile().stage4=Integer.parseInt(dataModel.getId());
                break;
            case 5:
                dataModel.getProfile().boost1=Integer.parseInt(dataModel.getId());
                break;
            case 6:
                dataModel.getProfile().boost2=Integer.parseInt(dataModel.getId());
                break;
            case 7:
                dataModel.getProfile().boost3=Integer.parseInt(dataModel.getId());
                break;
            case 8:
                dataModel.getProfile().boost4=Integer.parseInt(dataModel.getId());
                break;
        }
    }
}
