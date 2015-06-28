package com.wuli.chinglishflashcards;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class ItemFragment extends ListFragment {

    private static class CustomArrayAdapter extends ArrayAdapter<VocabItem> {

        public CustomArrayAdapter(Context context, List<VocabItem> vocabItems) {
            super(context, R.layout.summary_listview_item, vocabItems);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView;
            if (convertView != null) {
                itemView = convertView;
            } else {
                LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                itemView = layoutInflater.inflate(R.layout.summary_listview_item, null);
            }
            VocabItem vocabItem = getItem(position);

            ((TextView) itemView.findViewById(R.id.textViewChinese)).setText(vocabItem.chineseChar);
            ((TextView) itemView.findViewById(R.id.textViewEnglish)).setText(vocabItem.english);

            return itemView;
        }
    }

    public static ItemFragment newInstance() {
        ItemFragment fragment = new ItemFragment();
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO: Change Adapter to display your content
        setListAdapter(new CustomArrayAdapter(getActivity(), MainActivity.VOCAB_ITEMS));
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }
}
