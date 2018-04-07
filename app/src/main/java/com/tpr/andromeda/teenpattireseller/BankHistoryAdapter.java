package com.tpr.andromeda.teenpattireseller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class BankHistoryAdapter extends ArrayAdapter<BankHistory> {
    private static class ViewHolder{
        TextView tvReceiverId;
        TextView tvNumChips;
        TextView tvHType;
        TextView tvHTime;
    }

    public BankHistoryAdapter(@NonNull Context context, ArrayList<BankHistory> histories) {
        super(context, R.layout.layout_bank_history_item, histories);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        BankHistory history = getItem(position);

        ViewHolder viewHolder;
        if(convertView == null)
        {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.layout_bank_history_item, parent, false);
            viewHolder.tvReceiverId = convertView.findViewById(R.id.tvReceiverId);
            viewHolder.tvNumChips = convertView.findViewById(R.id.tvTotalChips);
            viewHolder.tvHType = convertView.findViewById(R.id.tvType);
            viewHolder.tvHTime = convertView.findViewById(R.id.tvTime);

            convertView.setTag(viewHolder);
        } else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(history != null) {
            viewHolder.tvReceiverId.setText(history.getReciverId());
            viewHolder.tvNumChips.setText(String.valueOf(history.getChips()));
            viewHolder.tvHType.setText(history.getType());
            viewHolder.tvHTime.setText(history.getTime());
        }

        return convertView;
    }
}
