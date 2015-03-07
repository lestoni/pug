package com.app.pug.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.TextView;

import com.app.pug.R;
import com.app.pug.models.GameSkillAdapterItem;
import com.app.pug.utils.Utils;

import java.util.ArrayList;
import java.util.Locale;

public class GameSkillAdapter extends ArrayAdapter<GameSkillAdapterItem> {

    private Context c;
    private ArrayList<GameSkillAdapterItem> items;

    public GameSkillAdapter(Context c, int layoutId, ArrayList<GameSkillAdapterItem> items) {
        super(c, layoutId, items);
        this.c = c;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View root = convertView;
        Holder holder;

        if (root == null) {
            root = LayoutInflater.from(c).inflate(R.layout.game_skill_item_layout, parent, false);

            holder = new Holder();
            holder.titleLabel = (TextView) root.findViewById(R.id.game_skill_title_label);
            holder.valueLabel = (TextView) root.findViewById(R.id.game_skill_value_label);
            holder.seekBar = (SeekBar) root.findViewById(R.id.game_skill_seekbar);

            root.setTag(holder);
        } else {
            holder = (Holder) root.getTag();
        }

        final GameSkillAdapterItem item = items.get(position);
        final TextView valueLabel = holder.valueLabel;

        holder.titleLabel.setText(Utils.capitalizeFirst(item.title));
        holder.valueLabel.setText(String.format(Locale.getDefault(), "%d%%", item.value));
        holder.seekBar.setProgress(item.value);
        holder.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                valueLabel.setText(String.format(Locale.getDefault(), "%d%%", progress));
                item.value = progress;
            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        return root;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    private class Holder {
        TextView titleLabel;
        TextView valueLabel;
        SeekBar seekBar;
    }
}
