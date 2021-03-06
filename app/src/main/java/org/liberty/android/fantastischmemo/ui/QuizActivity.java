/*
Copyright (C) 2012 Haowen Ning

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.

*/
package org.liberty.android.fantastischmemo.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Parcel;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.base.Strings;

import org.liberty.android.fantastischmemo.R;
import org.liberty.android.fantastischmemo.common.AnyMemoDBOpenHelper;
import org.liberty.android.fantastischmemo.common.AnyMemoDBOpenHelperManager;
import org.liberty.android.fantastischmemo.dao.HistoryDao;
import org.liberty.android.fantastischmemo.entity.Card;
import org.liberty.android.fantastischmemo.entity.Category;
import org.liberty.android.fantastischmemo.entity.History;
import org.liberty.android.fantastischmemo.entity.Option;
import org.liberty.android.fantastischmemo.entity.Setting;
import org.liberty.android.fantastischmemo.modules.AppComponents;
import org.liberty.android.fantastischmemo.queue.QueueManager;
import org.liberty.android.fantastischmemo.queue.QuizQueueManager;
import org.liberty.android.fantastischmemo.scheduler.Scheduler;
import org.liberty.android.fantastischmemo.ui.helper.HistoryHelper;
import org.liberty.android.fantastischmemo.ui.loader.DBLoader;
import org.liberty.android.fantastischmemo.ui.quiz.QuizReviewActivity;
import org.liberty.android.fantastischmemo.utils.DictionaryUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class QuizActivity extends QACardActivity {
    public static final String EXTRA_CATEGORY_ID = "category_id";
    public static final String EXTRA_START_CARD_ORD = "start_card_ord";
    public static final String EXTRA_QUIZ_SIZE = "quiz_size";
    public static final String EXTRA_SHUFFLE_CARDS = "shuffle_cards";
    public static final String EXTRA_START_CARD_ID = "start_card_id";
    public static final String EXTRA_TIMER_MODE = "timer_id";
    public static final String EXTRA_COUNTDOWN = "countdown_value";
    public MediaPlayer mediaPlayer;

    /* UI elements */
    private GradeButtonsFragment gradeButtonsFragment;

    /* Settings */
    private Setting setting;
    private Option option;

    /* Utils */
    @Inject DictionaryUtil dictionaryUtil;

    private QuizQueueManager queueManager;

    private int startCardId = -1;
    private int categoryId = -1;
    private int startCardOrd = -1;
    private int quizSize = -1;

    private boolean isNewCardsCompleted = false;
    private boolean shuffleCards = false;
    private int totalQuizSize = -1;
    private String quizScore;

    private TextView countdownText;
    private CountDownTimer countDownTimer;
    private int timeInSeconds;
    private long timeLeftInMilliseconds;
    private boolean timerMode = false;

    private String historyDbPath = "/sdcard/history.db";

    @Override
    public int getContentView() {
        return R.layout.qa_card_layout_study;
    }

    @Override
    public void onPostInit() {
        super.onPostInit();
        setting = getSetting();
        option = getOption();
        createQueue();

        // Keep track the initial total quiz size.
        totalQuizSize = queueManager.getNewQueueSize();

        /* Run the learnQueue init in a separate thread */
        if (startCardId != -1) {
            setCurrentCard(queueManager.dequeuePosition(startCardId));
        } else {
            setCurrentCard(queueManager.dequeue());
        }
        if (getCurrentCard() == null) {
            showNoItemDialog();
            return;
        }
        setupGradeButtons();
        displayCard(false);
        setSmallTitle(getActivityTitleString());
        setTitle(getDbName());

        SharedPreferences prefs = getApplicationContext().getSharedPreferences("QuizPref", 0); // 0 for private mode
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("quizMode", true);
        editor.commit();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        categoryId = extras.getInt(EXTRA_CATEGORY_ID, -1);
        startCardOrd = extras.getInt(EXTRA_START_CARD_ORD, -1);
        quizSize = extras.getInt(EXTRA_QUIZ_SIZE, -1);
        shuffleCards = extras.getBoolean(EXTRA_SHUFFLE_CARDS, false);
        timerMode = extras.getBoolean(EXTRA_TIMER_MODE, false);
        timeInSeconds = extras.getInt(EXTRA_COUNTDOWN, 120);
        timeLeftInMilliseconds = timeInSeconds * 1000;
        countdownText = (TextView) findViewById(R.id.countdown_text);

        if (savedInstanceState != null) {
            startCardId = savedInstanceState.getInt(EXTRA_START_CARD_ID, -1);
        }

        getMultipleLoaderManager().registerLoaderCallbacks(3, new QuizQueueManagerLoaderCallbacks(), false);

        startInit();
        if (timerMode) { startTimer(); }

    }

    public void startTimer() {
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.countdown);
        this.countDownTimer = new CountDownTimer(timeLeftInMilliseconds, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMilliseconds = millisUntilFinished;

                if (!mediaPlayer.isPlaying()) {
                    if (timeLeftInMilliseconds <= 8000) {
                        mediaPlayer.start();
                    }
                }

                if (timeLeftInMilliseconds <= 11000) {
                    TextView clock = (TextView) findViewById(R.id.countdown_text);
                    clock.setTextColor(Color.RED);
                }
                updateTimerText();
            }

            @Override
            public void onFinish() {
                mediaPlayer.stop();
                if (getCurrentCard().getOrdinal() != totalQuizSize + 1) {
                    new AlertDialog.Builder(QuizActivity.this)
                            .setTitle(R.string.quiz_not_completed)
                            .setMessage("Would you like to try again?")
                            .setPositiveButton(R.string.yes_text, flushAndQuitListener)
                            .setCancelable(false)
                            .show();
                }
            }
        }.start();

        timerMode = true;
    }

    public void stopTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            timerMode = false;
        }
    }

    public void updateTimerText() {
        int minutesLeft = (int) timeLeftInMilliseconds / 60000;
        int secondsLeft = (int) timeLeftInMilliseconds % 60000 / 1000;

        String totalTimeLeftText;

        totalTimeLeftText = "" + minutesLeft;
        totalTimeLeftText += ":";
        if (secondsLeft < 10) {
            totalTimeLeftText += "0";
        }
        totalTimeLeftText += secondsLeft;

        countdownText.setText(totalTimeLeftText);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Card currentCard = getCurrentCard();
        if (currentCard != null) {
            outState.putInt(EXTRA_START_CARD_ID, currentCard.getId());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.quiz_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_lookup: {
                dictionaryUtil.showLookupListDialog("" + getCurrentCard().getQuestion() + " " + getCurrentCard().getAnswer());
                break;
            }
            case R.id.menu_speak_question: {
                speakQuestion();
                break;
            }
            case R.id.menu_speak_answer: {
                speakAnswer();
                break;
            }
            case R.id.menu_paint: {
                Intent myIntent = new Intent(this, PaintActivity.class);
                startActivity(myIntent);
            }
        }
        return false;
    }

    @Override
    protected boolean onClickQuestionText() {
        if ((option.getSpeakingType() == Option.SpeakingType.AUTOTAP
                || option.getSpeakingType() == Option.SpeakingType.TAP)) {
            speakQuestion();
        } else {
            onClickQuestionView();
        }
        return true;
    }

    @Override
    protected boolean onClickAnswerText() {
        if (!isAnswerShown()) {
            onClickAnswerView();
        } else if ((option.getSpeakingType() == Option.SpeakingType.AUTOTAP
                || option.getSpeakingType() == Option.SpeakingType.TAP)) {
            speakAnswer();
        }
        return true;
    }

    @Override
    protected boolean onClickQuestionView() {
        if (!isAnswerShown()) {
            displayCard(true);
        }
        return true;
    }

    @Override
    protected boolean onClickAnswerView() {
        if (!isAnswerShown()) {
            displayCard(true);
        } else if (setting.getCardStyle() == Setting.CardStyle.DOUBLE_SIDED && isAnswerShown()) {
            displayCard(false);
        }
        return true;
    }

    @Override
    protected boolean onVolumeUpKeyPressed() {
        if (isAnswerShown()) {
            gradeButtonsFragment.gradeCurrentCard(0);
            Toast.makeText(this, getString(R.string.grade_text) + " 0", Toast.LENGTH_SHORT).show();
        } else {
            displayCard(true);
        }

        return true;
    }

    @Override
    protected boolean onVolumeDownKeyPressed() {
        if (isAnswerShown()) {
            gradeButtonsFragment.gradeCurrentCard(3);
            Toast.makeText(this, getString(R.string.grade_text) + " 3", Toast.LENGTH_SHORT).show();
        } else {
            displayCard(true);
        }
        return true;
    }

    public static class QuizQueueManagerLoader extends
            DBLoader<QueueManager> {

        private int filterCategoryId = -1;

        private int startCardOrd = -1;

        private int quizSize = 0;

        private boolean shuffleCards = false;

        @Inject Scheduler scheduler;

        public QuizQueueManagerLoader(AppComponents appComponents,
                                      String dbPath, int filterCategoryId,
                                      int startCardOrd, int quizSize,
                                      boolean shuffleCards) {
            super(appComponents.applicationContext(), dbPath);
            appComponents.inject(this);

            this.filterCategoryId = filterCategoryId;

            this.startCardOrd = startCardOrd;

            this.quizSize = quizSize;

            this.shuffleCards = shuffleCards;

        }

        @Override
        public QueueManager dbLoadInBackground() {
            Category filterCategory = null;

            if (filterCategoryId != -1) {
                filterCategory = dbOpenHelper.getCategoryDao().queryForId(filterCategoryId);
            }

            QuizQueueManager.Builder builder = new QuizQueueManager.Builder()
                    .setDbOpenHelper(dbOpenHelper)
                    .setScheduler(scheduler)
                    .setStartCardOrd(startCardOrd)
                    .setFilterCategory(filterCategory)
                    .setShuffle(shuffleCards);

            if (startCardOrd != -1) {
                builder.setStartCardOrd(startCardOrd)
                        .setQuizSize(quizSize);
            }

            return builder.build();
        }

    }

    private class QuizQueueManagerLoaderCallbacks implements
            LoaderManager.LoaderCallbacks<QueueManager> {
        @Override
        public Loader<QueueManager> onCreateLoader(int arg0, Bundle arg1) {
            Loader<QueueManager> loader = new QuizQueueManagerLoader(appComponents(), getDbPath(),
                    categoryId, startCardOrd, quizSize, shuffleCards);
            loader.forceLoad();
            return loader;
        }

        @Override
        public void onLoadFinished(Loader<QueueManager> loader, QueueManager queueManager) {
            QuizActivity.this.queueManager = (QuizQueueManager) queueManager;
            getMultipleLoaderManager().checkAllLoadersCompleted();
        }

        @Override
        public void onLoaderReset(Loader<QueueManager> arg0) {
            // Do nothing now
        }
    }

    private void createQueue() {
    }

    @Override
    public void onPostDisplayCard() {
        // When displaying new card, we should stop the TTS reading.
        getCardTTSUtil().stopSpeak();

        if (isAnswerShown()) {
            gradeButtonsFragment.setVisibility(View.VISIBLE);
        } else {
            // The grade button should be gone for double sided cards.
            if (setting.getCardStyle() == Setting.CardStyle.DOUBLE_SIDED) {
                gradeButtonsFragment.setVisibility(View.GONE);
            } else {
                gradeButtonsFragment.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void setupGradeButtons() {
        gradeButtonsFragment = new GradeButtonsFragment();

        Bundle args = new Bundle();
        args.putString(GradeButtonsFragment.EXTRA_DBPATH, getDbPath());
        gradeButtonsFragment.setArguments(args);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.buttons_root, gradeButtonsFragment);
        ft.commit();

        gradeButtonsFragment.setOnCardChangedListener(onCardChangedListener);
    }

    private CharSequence getActivityTitleString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getString(R.string.quiz_text) + ": " + (totalQuizSize - queueManager.getNewQueueSize()) + "/" + totalQuizSize + " ");
        sb.append(getString(R.string.review_short_text) + ": " + queueManager.getReviewQueueSize() + " ");
        sb.append(getString(R.string.id_text) + ": " + getCurrentCard().getId() + " ");
        if (!Strings.isNullOrEmpty(getCurrentCard().getCategory().getName())) {
            sb.append(getString(R.string.category_short_text) + ": " + getCurrentCard().getCategory().getName());
        }
        return sb.toString();
    }

    private String timeToCompleteQuiz() {
        String timeToCompleteText = "";
        long timeToCompleteInMilliseconds = (timeInSeconds * 1000) - timeLeftInMilliseconds;
        int timeInMinutes = (int) timeToCompleteInMilliseconds / 60000;
        int timeInSeconds = (int) timeToCompleteInMilliseconds % 60000 /1000;

        if (timeToCompleteInMilliseconds <= 0) { return timeToCompleteText; }

        String totalTimeToComplete;
        totalTimeToComplete = "" + timeInMinutes;
        totalTimeToComplete += ":";
        if (timeInSeconds < 10) {
            totalTimeToComplete += "0";
        }

        totalTimeToComplete += timeInSeconds;
        timeToCompleteText = "Completed in " + totalTimeToComplete + " seconds.";
        return timeToCompleteText;
    }

    /* Called when all quiz is completed */
    private void showCompleteAllDialog() {

        new AlertDialog.Builder(this)
                .setTitle(R.string.quiz_completed_text)
                .setMessage(R.string.quiz_complete_summary)
                .setMessage(timeToCompleteQuiz())
                .setPositiveButton(R.string.back_menu_text, flushAndQuitListener)
                .setCancelable(false)
                .show();
    }

    /* Called when all new cards are completed. */
    private void showCompleteNewDialog(int correct) {
        if (mediaPlayer !=null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
        }
        LayoutInflater layoutInflater
                = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.quiz_summary_dialog, null);
        TextView scoreView = (TextView) view.findViewById(R.id.score_text);
        int score = correct * 100 / totalQuizSize;
        quizScore = Integer.toString(score) + "% (" + correct + "/" + totalQuizSize + ")";
        //Create history entity and add to database here
        addToQuizHistory(score);

        scoreView.setText("" + score + "% (" + correct + "/" + totalQuizSize + ")");
        new AlertDialog.Builder(this)
                .setTitle(R.string.quiz_completed_text)
                .setMessage(timeToCompleteQuiz())
                .setView(view)
                .setNeutralButton(R.string.cancel_text, flushAndQuitListener)
                .setNegativeButton(R.string.review_text, reviewScoreListener)
                .setPositiveButton(R.string.restart_text, null)
                .setCancelable(false)
                .show();
    }

    private void addToQuizHistory(int score) {
        AnyMemoDBOpenHelper historyDbHelper = AnyMemoDBOpenHelperManager.getHelper(getApplicationContext(), historyDbPath);
        HistoryDao historyDao = historyDbHelper.getHistoryDao();
        historyDao.setHelper(historyDbHelper);
        List<History> historyForDeck = historyDao.getHistoryForDB(getDbPath());
        int count = historyDao.count(getDbPath());
        String dbPath = getDbPath();
        History result = HistoryHelper.addToHistory(score, count, historyForDeck, dbPath, historyDao);

        Parcel parcel = Parcel.obtain();
        parcel.writeValue(result.getdbPath());
        parcel.writeValue(result.getMark());
        parcel.writeValue(result.getTimeStamp());
    }

    // Current flush is not functional. So this method only quit and does not flush
    // the queue.
    private DialogInterface.OnClickListener flushAndQuitListener =
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            };

    private DialogInterface.OnClickListener reviewScoreListener =
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    Intent intent = new Intent(QuizActivity.this, QuizReviewActivity.class);
                    ArrayList<Card> forgottenCards = gradeButtonsFragment.getForgotCards();
                    ArrayList<Card> rememberedCards = gradeButtonsFragment.getRememberedCards();

                    intent.putParcelableArrayListExtra("FORGOT_CARDS", forgottenCards);
                    intent.putParcelableArrayListExtra("REMEMBERED_CARDS", rememberedCards);
                    intent.putExtra("QUIZ_SCORE", quizScore);
                    startActivity(intent);
                }
            };

    private GradeButtonsFragment.OnCardChangedListener onCardChangedListener =
            new GradeButtonsFragment.OnCardChangedListener() {
                public void onCardChanged(Card prevCard, Card updatedCard) {
                    gradeButtonsFragment.setVisibility(View.INVISIBLE);

                    // Run the task to update the updatedCard in the queue
                    // and dequeue the next card
                    ChangeCardTask task = new ChangeCardTask(QuizActivity.this, updatedCard);
                    task.execute();
                }
            };

    // Task to change the card after a card is graded
    // It needs to update the old card and dequeue the new card
    // and display it.
    private class ChangeCardTask extends AsyncTask<Void, Void, Card> {

        private int newQueueSizeBeforeDequeue;

        private int reviewQueueSizeBeforeDequeue;

        private Card updatedCard;

        private Context context;

        public ChangeCardTask(Context context, Card updatedCard) {
            this.updatedCard = updatedCard;
            this.context = context;
        }

        @Override
        protected Card doInBackground(Void... voids) {
            queueManager.remove(getCurrentCard());
            queueManager.update(updatedCard);

            // Keep track of two values to dermine when to display dialog
            // to promote the quiz completion
            newQueueSizeBeforeDequeue = queueManager.getNewQueueSize();
            reviewQueueSizeBeforeDequeue = queueManager.getReviewQueueSize();

            Card nextCard = queueManager.dequeue();
            return nextCard;
        }

        @Override
        protected void onPostExecute(Card result) {
            setProgressBarIndeterminateVisibility(false);

            // Stat data
            setCurrentCard(result);
            if (result == null) {
                stopTimer();
                showCompleteNewDialog(totalQuizSize - reviewQueueSizeBeforeDequeue);
                return;
            }

            displayCard(false);
            setSmallTitle(getActivityTitleString());

            if (newQueueSizeBeforeDequeue <= 0 && !isNewCardsCompleted) {
                stopTimer();
                showCompleteNewDialog(totalQuizSize - reviewQueueSizeBeforeDequeue);
                isNewCardsCompleted = true;

            }
        }
    }

    private void showNoItemDialog() {
        new AlertDialog.Builder(this)
                .setTitle(this.getString(R.string.memo_no_item_title))
                .setMessage(this.getString(R.string.memo_no_item_message))
                .setNeutralButton(getString(R.string.back_menu_text), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                    /* Finish the current activity and go back to the last activity.
                     * It should be the open screen. */
                        finish();
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                        finish();
                    }
                })
                .create()
                .show();
    }
}
