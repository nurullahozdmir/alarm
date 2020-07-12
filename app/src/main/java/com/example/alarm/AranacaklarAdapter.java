package com.example.alarm;


import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alarm.Interface.ItemClickListener;
import com.example.alarm.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class AranacaklarAdapter extends RecyclerView.Adapter<AranacaklarAdapter.AranacaklarViewHolder> {

    private List<VeriModeli> dataList;
    private ArrayList<VeriModeli> arrayList;
    private Context context;
    DatabaseHelper databaseHelper;

    public AranacaklarAdapter(List<VeriModeli> dataList, Context context) {

        this.dataList = dataList;
        this.context = context;
        this.arrayList = new ArrayList<VeriModeli>();
        this.arrayList.addAll(dataList);

    }


    @Override
    public AranacaklarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.adapter_layout, parent, false);
        return new AranacaklarViewHolder(view);


    }


    @Override
    public void onBindViewHolder(AranacaklarViewHolder holder, final int position) {


        databaseHelper =new DatabaseHelper (context);

        holder.saat.setText(dataList.get(position).getSaat());


        holder.telefon.setText(String.valueOf(dataList.get(position).getTelefon()));

        holder.tarih.setText(dataList.get(position).getTarih());
    //    holder.saat.setText(dataList.get(position).getSaat());
    //    holder.durum.setText(dataList.get(position).getDurum());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {


                    Intent intent = new Intent(context, Formekrani.class);
                 //   intent.putExtra("adsoyad", dataList.get(position).getAdSoyad());
                    intent.putExtra("telefon", dataList.get(position).getTelefon());
                    intent.putExtra("tarih", dataList.get(position).getTarih());
                    intent.putExtra("saat", dataList.get(position).getSaat());
                    context.startActivity(intent);

            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // databaseHelper.deleteTitle(dataList.get(position).getTelefon());
          //      databaseHelper.delete(position);
                databaseHelper.delete(dataList.get(position).getTelefon());

         //     databaseHelper.deleteTitle(dataList.get(position));


                dataList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, dataList.size());
                notifyDataSetChanged();
            }
        });




    }

    @Override
    public int getItemCount() {

        return dataList.size();
    }

    class AranacaklarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private ItemClickListener itemClickListener;

        private Button delete;

        TextView adsoyad,
                telefon,
                tarih,
                saat,
                durum;


        AranacaklarViewHolder(View itemView) {
            super(itemView);



            saat = (TextView) itemView.findViewById(R.id.saat);
            telefon = (TextView) itemView.findViewById(R.id.telefon);
            tarih = (TextView) itemView.findViewById(R.id.tarih);
        //    saat = (TextView) itemView.findViewById(R.id.saat);
        //    durum = (TextView) itemView.findViewById(R.id.durum);

           delete = itemView.findViewById(R.id.buttonSil);


            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;

        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), false);

        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), true);
            return true;
        }
    }

}