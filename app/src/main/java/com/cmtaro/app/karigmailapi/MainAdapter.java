package com.cmtaro.app.karigmailapi;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    // initialize valiable
    private List<MainData> dataList;
    private Activity context;
    private RoomDB database;

    // create Constracter
    public MainAdapter(Activity context, List<MainData> datalist) {
        this.context = context;
        this.dataList = datalist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // initialize view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_main, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // initialize main data
        MainData data = dataList.get(position);
        database = RoomDB.getInstance(context);

        // Set Text on text View
        holder.textView.setText(data.getText());


        holder.btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // initialize main data
                MainData d = dataList.get(holder.getAdapterPosition());

                // get id
                int sID = d.getID();
                // get text
                String sText = d.getText();
                // create dialog
                Dialog dialog = new Dialog(context);
                // set create view
                dialog.setContentView(R.layout.dialog_update);
                // initialize width
                int width = WindowManager.LayoutParams.MATCH_PARENT;

                // height
                int height = WindowManager.LayoutParams.WRAP_CONTENT;

                // Set Layout
                dialog.getWindow().setLayout(width, height);

                // Show Dialog
                dialog.show();

                // initialize and assign
                EditText editText = dialog.findViewById(R.id.edit_text);
                Button btUpdate = dialog.findViewById(R.id.bt_update);

                // Set Text on edit text
                editText.setText(sText);

                // updateボタンが押された時の処理
                btUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //　dissmiss dialog
                        dialog.dismiss();

                        // get update text from editText
                        String uText = editText.getText().toString().trim();

                        // Update text in database
                        database.mainDao().update(sID,uText);

                        // notify when data is update
                        dataList.clear();
                        dataList.addAll(database.mainDao().getAll());
                        notifyDataSetChanged();
                    }
                });
            }
        });

        holder.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // initialize main data
                MainData d = dataList.get(holder.getAdapterPosition());

                // Delete text from database
                database.mainDao().delete(d);

                // notify when data is deleted
                int positon = holder.getAdapterPosition();
                dataList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(positon,dataList.size());

            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // initialize valiable
        TextView textView;
        ImageView btEdit, btDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.text_view);
            btEdit = itemView.findViewById(R.id.bt_edit);
            btDelete = itemView.findViewById(R.id.bt_delete);
        }
    }
}
