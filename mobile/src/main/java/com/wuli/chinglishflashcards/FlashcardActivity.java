package com.wuli.chinglishflashcards;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class FlashcardActivity extends ActionBarActivity {

    private static final List<VocabItem> VOCAB_ITEMS = Arrays.asList(new VocabItem[] {
            new VocabItem("中国", "zhōng guó", "China"),
            new VocabItem("澳大利亚", "Ào dà lì yǎ", "Australia"),
            new VocabItem("英国", "Yīng guó", "England")
    });
    public static final int NUMBER_OF_ANSWERS = 3;

    private List<TextView> answerButtons = new ArrayList<>();

    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard);

        answerButtons.add((TextView) findViewById(R.id.answer1));
        answerButtons.add((TextView) findViewById(R.id.answer2));
        answerButtons.add((TextView) findViewById(R.id.answer3));

        Collections.shuffle(VOCAB_ITEMS);
        fillInCardAndAnswers();
    }

    private void fillInCardAndAnswers() {
        final VocabItem vocabItem = VOCAB_ITEMS.get(index % VOCAB_ITEMS.size());
        ((TextView) findViewById(R.id.flashcard_language)).setText(vocabItem.chineseChar);
        ((TextView) findViewById(R.id.flashcard_pinyin)).setText(vocabItem.chinesePinyin);
        final List<String> answers=generateAnswers(vocabItem);
        Collections.shuffle(answers);
        for (int i = 0; i < NUMBER_OF_ANSWERS; i++) {
            final String answer = answers.get(i);
            final TextView button = answerButtons.get(i);
            button.setText(answer);
            button.getBackground().clearColorFilter();
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (answer == vocabItem.english) {
                        button.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
                        v.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                fillInCardAndAnswers();
                            }
                        }, 2000);
                    } else {
                        button.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                    }
                }
            });
        }
        index++;
    }

    private List<String> generateAnswers(VocabItem vocabItem) {
        List<String> answers = new ArrayList<>();
        answers.add(vocabItem.english);

        Random random = new Random();
        while(answers.size() < NUMBER_OF_ANSWERS) {
            int randomindex = random.nextInt(VOCAB_ITEMS.size());
            String altAnswer = VOCAB_ITEMS.get(randomindex).english;
            if (!answers.contains(altAnswer)) {
                answers.add(altAnswer);
            }
        }

        return answers;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_flashcard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
