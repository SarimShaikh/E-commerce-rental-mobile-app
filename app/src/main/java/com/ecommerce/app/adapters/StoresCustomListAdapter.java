package com.ecommerce.app.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.ecommerce.app.R;
import com.ecommerce.app.model.Stores;
import com.ecommerce.app.singletonvolley.VolleySingleton;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB_MR1)
public class StoresCustomListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<Stores> storesItems;
    ImageLoader imageLoader = VolleySingleton.getInstance2().getImageLoader();

    public StoresCustomListAdapter(Activity activity, List<Stores> storesItems) {
        this.activity = activity;
        this.storesItems = storesItems;
    }

    @Override
    public int getCount() {
        return storesItems.size();
    }

    @Override
    public Object getItem(int location) {
        return storesItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row_stores, null);

        if (imageLoader == null)
            imageLoader = VolleySingleton.getInstance2().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView rating = (TextView) convertView.findViewById(R.id.rating);
        TextView genre = (TextView) convertView.findViewById(R.id.genre);
        TextView year = (TextView) convertView.findViewById(R.id.releaseYear);

        // getting store data for the row
        Stores m = storesItems.get(position);

        // thumbnail image
        thumbNail.setImageUrl(m.getImageUri(), imageLoader);

        // title
        title.setText(m.getStoreName());
        rating.setText("Contact: " + String.valueOf(m.getStoreContact()));
        year.setText(String.valueOf(m.getStoreAddress()));

        return convertView;
    }
    /*@Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length() == 0){
                    filterResults.count = itemsModelsl.size();
                    filterResults.values = itemsModelsl;

                }else{
                    List<itemsmodel> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for(ItemsModel itemsModel:itemsModelsl){
                        if(itemsModel.getName().contains(searchStr) || itemsModel.getEmail().contains(searchStr)){
                            resultsModel.add(itemsModel);
                            filterResults.count = resultsModel.size();
                            filterResults.values = resultsModel;
                        }
                    }
                }

                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                itemsModelListFiltered = (List<itemsmodel>) results.values;
                notifyDataSetChanged();

            }
        };
        return filter;
    }*/

}
