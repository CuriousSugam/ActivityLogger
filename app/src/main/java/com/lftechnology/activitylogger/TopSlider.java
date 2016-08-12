package com.lftechnology.activitylogger;

/**
 * Created by DevilDewzone on 7/13/2016.
 */

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;




public class TopSlider extends PagerAdapter {

    private int[] img_res= {R.drawable.testimgone, R.drawable.testimgtwo, R.drawable.testimgthree};
    private Context ctx;
    private LayoutInflater layoutInflater;

    public TopSlider (Context ctx){
        this.ctx=ctx;
    }

    @Override
    public int getCount() {
        return img_res.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==(RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itm_view= layoutInflater.inflate(R.layout.top_slider,container,false);
        ImageView imageView =(ImageView) itm_view.findViewById(R.id.image_slide);
        imageView.setImageResource(img_res[position]);
        container.addView(itm_view);
        return itm_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}
