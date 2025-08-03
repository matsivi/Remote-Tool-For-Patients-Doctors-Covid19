package com.e.covid19new;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";
    private ArrayList<String> mDataSet;
    public Adapter(ArrayList<String> DataSet){
            mDataSet = DataSet;
    }


    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private  TextView text_on,text_epwn,text_date,text_time;
        private TableLayout table;
        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                }
            });
            text_on = (TextView) v.findViewById(R.id.onoma_txt);
            text_epwn = (TextView) v.findViewById(R.id.epwn_txt);
            text_date = (TextView) v.findViewById(R.id.date_txt);
            text_time = (TextView) v.findViewById(R.id.time_txt);
            table = (TableLayout) v.findViewById(R.id.table);

        }


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.table_rantebou, viewGroup, false);

        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");
        String[] metadata;
        try{
            String data=mDataSet.get(position);
             if(position % 2 == 0 ){
                 viewHolder.table.setBackgroundColor(Color.parseColor("#FFFFFF"));

             }

             metadata= data.split(",");
            viewHolder.text_on.setText(metadata[0]);
            viewHolder.text_epwn.setText(metadata[1]);
            viewHolder.text_date.setText(metadata[2]);
            viewHolder.text_time.setText(metadata[3]);

        }
        catch(Exception e){
            viewHolder.text_on.setText("");
            viewHolder.text_epwn.setText("");
            viewHolder.text_date.setText("");
            viewHolder.text_time.setText("");
            Log.e("error", String.valueOf(e));
        }


    }
    // END_INCLUDE(recyclerViewOnBindViewHolder)

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        Log.d("data size", String.valueOf(mDataSet.size()));
        return mDataSet.size();
    }

    @Override
    public int getItemViewType(int position){
        return position;


    }
    @Override
    public long getItemId(int position){

        return position;

    }
}
