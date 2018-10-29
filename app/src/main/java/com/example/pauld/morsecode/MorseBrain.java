package com.example.pauld.morsecode;

import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MorseBrain {
    // Local variables
    private Timer morseTimer;
    private long startInputTime;
    private interSymbolTask symbolTask;
    private interCharTask charTask;
    private interWordTask wordTask;
    private Handler uiHandler; // To allow the timer thread to change textViews

    // Constants
    private int timeUnit = 100; // In milliseconds, TODO: Pull from settings

    // Input variables
    private MorseTrie InternationalStandardTrie;
    private TextView morseTextView, charTextView, overallTextView;

    // TODO: Add input variable to change standard
    public MorseBrain(TextView inputMorseTextView, TextView inputCharTextView, TextView inputOverallTextView) {
        InternationalStandardTrie = new MorseTrie(MorseCodeStandards.InternationalStandard);
        morseTextView = inputMorseTextView;
        charTextView = inputCharTextView;
        overallTextView = inputOverallTextView;
        morseTimer = new Timer();
        uiHandler = new Handler();
    }

    // When triggered: Current symbol is over?
    // Should: ?? Anything ??
    private class interSymbolTask extends TimerTask {
        @Override
        public void run() {
            // TODO: Determine if this is necessary, reattach if so

            // Schedule "Space between characters" task
            morseTimer.schedule(new interCharTask(), 2*timeUnit);
        }
    }

    // When triggered: Current character is finished and should be displayed
    // Should: 1) Clear morse code and character textViews
    //         2) Append character to overall string
    //         3) Reset Trie
    //         4) Schedule "Space between words" task
    private class interCharTask extends TimerTask {
        @Override
        public void run() {
            Log.e("[interCharTask]", "STARTING!");

            uiHandler.post(new Runnable() {
                @Override
                public void run() {
                    morseTextView.setText("");
                    charTextView.setText("");

                    overallTextView.append(InternationalStandardTrie.GetCurrentBranch().BranchChar);
                    Log.e("[interCharTask]", "TEST! >>" + InternationalStandardTrie.GetCurrentBranch().BranchChar + "<<");

                    InternationalStandardTrie.ResetTrie();
                }
            });

            wordTask = new interWordTask();
            morseTimer.schedule(wordTask, 4*timeUnit);
        }
    }

    // When triggered: Current word is finished
    // Should: 1) Append space to overall string
    private class interWordTask extends TimerTask {
        @Override
        public void run() {
            Log.e("[interWordTask]", "STARTING!");
            uiHandler.post(new Runnable() {
                @Override
                public void run() {
                    overallTextView.append(" ");
                }
            });
        }
    }

    private void CancelTimerTasks() {
        //if (symbolTask != null) symbolTask.cancel(); // Currently not used
        if (charTask != null) charTask.cancel();
        if (wordTask != null) wordTask.cancel();
    }

    // PUBLIC API BELOW

    public void StartInput() {
        // Cancel currently scheduled task (if any)
        CancelTimerTasks();

        // Store user input start time
        startInputTime = System.currentTimeMillis();
    }

    public void EndInput() {
        MorseTrieBranch tmpBranch;

        // Store user input end time
        long inputTimeDiff = System.currentTimeMillis() - startInputTime;

        // Add dot or dash depending on time
        if (inputTimeDiff < timeUnit) {
            tmpBranch = InternationalStandardTrie.InputDot();
            morseTextView.setText(tmpBranch.BranchMorseCode);
            charTextView.setText(tmpBranch.BranchChar);
        }
        else if ( inputTimeDiff > 3*timeUnit) {
            tmpBranch = InternationalStandardTrie.InputDash();
            morseTextView.setText(tmpBranch.BranchMorseCode);
            charTextView.setText(tmpBranch.BranchChar);
        }
        else {
            Log.e("[EndInput]", "Time in between two options!");
        }

        // Schedule "Space between Symbol" task
        charTask = new interCharTask();
        morseTimer.schedule(charTask, 3*timeUnit);
    }

    // Purely for debugging
    public void TestBrain(String[] testStr) {
        Log.e("[TestBrain]: ", "Starting Test! >>" + testStr[0] + "<< >>" + testStr[1] + "<<");

        MorseTrieBranch tmpBranch;

        char testChar;

        for (int i = 0; i < testStr[0].length(); i++) {
            testChar = testStr[0].charAt(i);
            if (testChar == '.')     tmpBranch = InternationalStandardTrie.InputDot();
            else if (testChar== '-') tmpBranch = InternationalStandardTrie.InputDash();
            else {
                // Error case
                Log.d("[TestBrain]: ", "Invalid character found! >>" + testChar + "<< ENDING");
                break;
            }

            if (tmpBranch != null) Log.d("[TestBrain]: ", tmpBranch.BranchChar);
            else {
                Log.e("[TestBrain]: ", "Hit NULL: Ending Test!");
                break;
            }
        }

        // Reset trie
        InternationalStandardTrie.ResetTrie();

        Log.e("[TestBrain]: ", "Ending Test!");
    }
}
