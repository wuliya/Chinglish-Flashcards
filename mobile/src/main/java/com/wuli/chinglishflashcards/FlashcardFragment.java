package com.wuli.chinglishflashcards;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class FlashcardFragment extends Fragment {

    private static final List<VocabItem> VOCAB_ITEMS = Arrays.asList(new VocabItem[] {
            new VocabItem("中国", "zhōng guó", "China"),
            new VocabItem("澳大利亚", "Ào dà lì yǎ", "Australia"),
            new VocabItem("英国", "Yīng guó", "England")
    });
    public static final int NUMBER_OF_ANSWERS = 3;

    private List<TextView> answerButtons = new ArrayList<>();

    private int index = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.activity_flashcard, container, false);
        answerButtons.add((TextView) rootView.findViewById(R.id.answer1));
        answerButtons.add((TextView) rootView.findViewById(R.id.answer2));
        answerButtons.add((TextView) rootView.findViewById(R.id.answer3));

        Collections.shuffle(VOCAB_ITEMS);
        fillInCardAndAnswers(rootView);
        return rootView;
    }

    private void fillInCardAndAnswers(final View rootView) {
        final VocabItem vocabItem = VOCAB_ITEMS.get(index % VOCAB_ITEMS.size());
        ((TextView) rootView.findViewById(R.id.flashcard_language)).setText(vocabItem.chineseChar);
        ((TextView) rootView.findViewById(R.id.flashcard_pinyin)).setText(vocabItem.chinesePinyin);
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
                                fillInCardAndAnswers(rootView);
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
}
