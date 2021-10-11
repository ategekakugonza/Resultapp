package com.example.resultapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;


public class Student_Adapter extends RecyclerView.Adapter<Student_Adapter.DataViewHolder> implements Filterable {

    private Context mCtx;
    String status;
    private int doll;
    List<Student_List> mData;
    List<Student_List> mDataFiltered;

    public Student_Adapter(Context mCtx, List<Student_List> mData) {
        this.mCtx = mCtx;
        this.mData = mData;
        this.mDataFiltered = mData;
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.row_results, parent,false);
//        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//        view.setLayoutParams(layoutParams);
        return new DataViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final DataViewHolder holder, final int position) {
        final Student_List info = mDataFiltered.get(position);
        holder.date.setText(""+info.getDate());
        holder.name.setText(""+info.getName());
        holder.index.setText("Index No: "+info.getIndex());
        holder.mtc.setText(""+info.getMtc());
        holder.sst.setText(""+info.getSst());
        holder.scie.setText(""+info.getScie());
        holder.eng.setText(""+info.getEng());
        holder.total.setText(""+(
                Integer.parseInt(info.getMtc())+
                        Integer.parseInt(info.getScie()) +
                Integer.parseInt(info.getEng())+
                        Integer.parseInt(info.getSst())
        )+"/400");
        holder.comment.setText("Tap on the card to edit or delete marks");
        holder.car_here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent(mCtx,ManageResultsActivity.class);
                resultIntent.putExtra("result_id",""+info.getId());
                resultIntent.putExtra("index",""+info.getId());
                resultIntent.putExtra("name",""+info.getName());
                resultIntent.putExtra("mtc",""+info.getMtc());
                resultIntent.putExtra("sci",""+info.getScie());
                resultIntent.putExtra("eng",""+info.getEng());
                resultIntent.putExtra("sst",""+info.getSst());
                mCtx.startActivity(resultIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return myFilterData;
    }


    private Filter myFilterData = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String key=constraint.toString();
            if (key.isEmpty()){
                mDataFiltered=mData;
            }
            else{
                List<Student_List> FilteredList=new ArrayList<>();
                mDataFiltered=FilteredList;
            }
            FilterResults  filterResults=new FilterResults();
            filterResults.values=mDataFiltered;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            mDataFiltered=(List<Student_List>)results.values;
            notifyDataSetChanged();
        }
    };
    class DataViewHolder extends RecyclerView.ViewHolder {
        private CardView car_here;
        private ImageView img_post;
        private TextView name,index,mtc,scie,eng,sst,comment,date,total;
        public DataViewHolder(View itemView) {
            super(itemView);
            car_here=itemView.findViewById(R.id.car_here);
            name=itemView.findViewById(R.id.name);
            index=itemView.findViewById(R.id.index);
            date=itemView.findViewById(R.id.date);
            mtc=itemView.findViewById(R.id.mtc);
            scie=itemView.findViewById(R.id.scie);
            eng=itemView.findViewById(R.id.eng);
            sst=itemView.findViewById(R.id.sst);
            total=itemView.findViewById(R.id.total);
            comment=itemView.findViewById(R.id.comment);
        }
    }

}