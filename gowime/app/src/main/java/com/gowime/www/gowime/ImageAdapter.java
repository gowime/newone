package com.gowime.www.gowime;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View categoryCard;

        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            categoryCard = new View(mContext);
            categoryCard = inflater.inflate(R.layout.category_item, null);
            ImageView categoryCardImage = categoryCard.findViewById(R.id.category_card);
            categoryCardImage.setImageResource(mThumbIds[position]);


        } else {
            categoryCard = (View)convertView;
        }

        return categoryCard;
    }

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.outdoor_bw, R.drawable.outdoor_color,
            R.drawable.food_color, R.drawable.travel_bw,
            R.drawable.outdoor_bw, R.drawable.outdoor_color,
            R.drawable.food_color, R.drawable.travel_bw,
            R.drawable.outdoor_bw, R.drawable.outdoor_color,
            R.drawable.food_color, R.drawable.travel_bw,
            R.drawable.outdoor_bw, R.drawable.outdoor_color,
            R.drawable.food_color, R.drawable.travel_bw,
            R.drawable.outdoor_bw, R.drawable.outdoor_color,
            R.drawable.food_color, R.drawable.travel_bw
    };
}
