package com.wuli.chinglishflashcards;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

public class AddNewVocabDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        final View view = inflater.inflate(R.layout.dialog_add_new_vocab, null);
        builder.setView(view);

        builder.setTitle(R.string.add_new_vocab_item)
               .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // Add new vocab item
                       // Instantiate the RequestQueue.
                       RequestQueue queue = Volley.newRequestQueue(getActivity());
                       final String englishword = ((EditText) view.findViewById(R.id.english)).getText().toString();
                       String url ="http://hablaa.com/hs/translation/" + englishword + "/eng-zho/";

                       JsonRequest jsonRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                           @Override
                           public void onResponse(JSONArray jsonArray) {
                               try {
                                   String chinesechar = jsonArray.getJSONObject(0).getString("text");
                                   String pinyin = jsonArray.getJSONObject(0).getString("transcription");
                                   VocabItem vocabItem = new VocabItem(chinesechar,pinyin,englishword);
                                   MainActivity.VOCAB_ITEMS.add(vocabItem);
                               } catch (Exception e) {
                                   e.printStackTrace();
                               }

                               Log.i("liyawu", "jsonArray: " + jsonArray.toString());

                           }
                       }, new Response.ErrorListener() {
                           @Override
                           public void onErrorResponse(VolleyError volleyError) {

                           }
                       });
                       // Add the request to the RequestQueue
                       queue.add(jsonRequest);
                   }
               })
               .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // User cancelled the dialog
                   }
               });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}