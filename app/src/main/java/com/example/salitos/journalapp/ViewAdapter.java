package com.example.salitos.journalapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.ItemViewHolder>{
    private List<JournalDataClass> entries;
    private AdapterClickListener clickListener;
//The class will act as the interface between the recycler view and the classes holding the logical data to be
    //displayed
    public ViewAdapter(List<JournalDataClass> entries,
                         AdapterClickListener clickListener) {
        this.clickListener = clickListener;
        replaceData(entries);
    }
//The class inflates the fragment and gives focus to it
 @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.
                list_item, parent, false);

        return new ItemViewHolder(view);
    }
    // onbindholder will be responsible in mapping the position in response to action and other view components
    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.bind(entries.get(position), position);
    }
//determining the number of items to be placed in the view holder and bound
    @Override
    public int getItemCount() {
        if (entries == null) {
            return 0;
        }
        return entries.size();
    }

    public void replaceData(List<JournalDataClass> entries) {
        this.entries = entries;
        this.notifyDataSetChanged();
    }

    public interface AdapterClickListener {
        void onEntryClicked(JournalDataClass entry);
    }
//Declare and Instantiate the inflated fragments that will populate the viewholder
    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView detailTextView;
        TextView messageTextView;


        ItemViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.title);
            detailTextView = itemView.findViewById(R.id.details);
            messageTextView = itemView.findViewById(R.id.message);
        }

        void bind(final JournalDataClass entry, final int position) {
            titleTextView.setText(entry.getEvent());
            detailTextView.setText(entry.getDetails());
            messageTextView.setText(entry.getDate());
//Notify all elements of user action so they all respond as expected
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onEntryClicked(entries.get(position));
                }
            });
        }
    }
}