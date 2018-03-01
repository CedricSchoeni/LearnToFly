package mailmaster.cedric.learntofly.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import mailmaster.cedric.learntofly.R;
import mailmaster.cedric.learntofly.Resources.ResourceManager;

/**
 * Created by adriano.campiotti on 01.03.2018.
 */

public class Renderer extends View {

    ResourceManager rm;
    Context context;

    public List<RObject> renderList;


    public Renderer(Context context) {
        super(context);
        rm = new ResourceManager();
        this.context = context;

        renderList = new ArrayList<RObject>();
        RObject roiDBoi = new RImage(50, 50, 45, rm.drawableToBitmap(context, R.drawable.elkenholz));
        renderList.add(roiDBoi);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);

        for (RObject ro : renderList){
            ro.drawObject(canvas, context);
        }

    }
}
