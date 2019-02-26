package com.example.kkingsbe.autopilotapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class FunctionsAdapter extends RecyclerView.Adapter<FunctionsAdapter.FunctionsViewHolder> {
    private ArrayList<FunctionItem> functionsList;
    private OnItemClickListner mListener;

    public interface OnItemClickListner {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListner listener) {
        mListener = listener;
    }

    public static class FunctionsViewHolder extends RecyclerView.ViewHolder {
        public TextView textView1;
        public TextView textView2;

        public FunctionsViewHolder(@NonNull View itemView, final OnItemClickListner listener) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.textView1);
            textView2 = itemView.findViewById(R.id.textView2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public FunctionsAdapter(ArrayList<FunctionItem> mFunctionsList) {
        functionsList = mFunctionsList;
    }

    @NonNull
    @Override
    public FunctionsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.function_item, viewGroup, false);
        FunctionsViewHolder fvh = new FunctionsViewHolder(v, mListener);
        return fvh;
    }

    @Override
    public void onBindViewHolder(@NonNull FunctionsViewHolder functionsViewHolder, int i) {
        FunctionItem currentItem = functionsList.get(i);

        functionsViewHolder.textView1.setText(currentItem.getText1());
        functionsViewHolder.textView2.setText(currentItem.getText2());
    }

    @Override
    public int getItemCount() {
        return functionsList.size();
    }
}
