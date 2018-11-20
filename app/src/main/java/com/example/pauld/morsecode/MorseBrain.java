package com.example.pauld.morsecode;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.animation.LinearInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import static com.example.pauld.morsecode.MorseCodeStandards.GiveLetterGetMorse;

public class MorseBrain {
    // Local variables
    private Timer morseTimer;
    private long startInputTime;
    private interSymbolTask symbolTask;
    private interCharTask charTask;
    private interWordTask wordTask;
    private Handler uiHandler; // To allow the timer thread to change textViews
    private ObjectAnimator progressAnimation;
    private Toast currentToast;

    // Settings
    private boolean EndOfWordTaskOn;
    private boolean EndOfCharTaskOn;

    private String lastChar;

    // Constants
    private int timeUnit = 100; // In milliseconds, TODO: Pull from settings

    // Input variables
    private MorseTrie InternationalStandardTrie;
    private TextView morseTextView, charTextView, overallTextView;
    private Context parentContext;
    private ProgressBar progressBar;

    // Sound/Vibrate/Light Driver
    private Driver feedbackDriver;

    // Handlers to handle outputting Morse Code
    private final Handler delayHandler1 = new Handler();
    private final Handler delayHandler2 = new Handler();
    private String TokenizedOutput[];
    private String CurrentMorseOutput;
    private int OutputWordIdx;
    private int OutputCharIdx;
    private int OutputMorseIdx;

    // TODO: Add input variable to change standard
    public MorseBrain(Context context, TextView inputMorseTextView, TextView inputCharTextView, TextView inputOverallTextView, ProgressBar inputProgressBar, Driver inputDriver) {
        InternationalStandardTrie = new MorseTrie(MorseCodeStandards.InternationalStandard);
        morseTextView = inputMorseTextView;
        charTextView = inputCharTextView;
        overallTextView = inputOverallTextView;
        progressBar = inputProgressBar;
        morseTimer = new Timer();
        uiHandler = new Handler();
        currentToast = null;

        parentContext = context;

        EndOfWordTaskOn = true;
        EndOfCharTaskOn = false;

        feedbackDriver = inputDriver;

        ElectricShock();
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

                    lastChar = InternationalStandardTrie.GetCurrentBranch().BranchChar;

                    if (lastChar != null && !lastChar.equals("?") && overallTextView != null) overallTextView.append(lastChar);

                    InternationalStandardTrie.ResetTrie();
                }
            });

            if (EndOfWordTaskOn & lastChar != null && !lastChar.equals("?")) {
                wordTask = new interWordTask();
                morseTimer.schedule(wordTask, 4*timeUnit);
            }
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
                    if (overallTextView != null) overallTextView.append(" ");
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
        // Start any feedback
        feedbackDriver.on();

        // Cancel any displaying toast
        if (currentToast != null) currentToast.cancel();

        // Cancel currently scheduled task (if any)
        CancelTimerTasks();

        // Start progress animation
        StartProgressBar();

        // Store user input start time
        startInputTime = System.currentTimeMillis();
    }

    public void EndInput() {
        // Turn feedback off
        feedbackDriver.off();

        // Reset progress bar and toast
        ResetProgressBar();
        if (currentToast != null) currentToast.cancel();

        MorseTrieBranch tmpBranch;

        // Store user input end time
        long inputTimeDiff = System.currentTimeMillis() - startInputTime;

        // Add dot or dash depending on time
        if (inputTimeDiff < timeUnit) {
            tmpBranch = InternationalStandardTrie.InputDot();
            charTextView.setText(tmpBranch.BranchChar);
            if (tmpBranch.BranchChar.equals("?")) {
                if (morseTextView.getText().length() <= 10) morseTextView.append("•");
            }
            else morseTextView.setText(tmpBranch.BranchMorseCode);
        }
        else if (inputTimeDiff < 3*timeUnit) {
            currentToast = Toast.makeText(parentContext, "Time in between Dot and Dash!", Toast.LENGTH_SHORT);
            currentToast.show();
        }
        else if (inputTimeDiff < 5*timeUnit) {
            tmpBranch = InternationalStandardTrie.InputDash();
            charTextView.setText(tmpBranch.BranchChar);
            if (tmpBranch.BranchChar.equals("?")) {
                if (morseTextView.getText().length() <= 10)morseTextView.append("-");
            }
            else morseTextView.setText(tmpBranch.BranchMorseCode);
        }
        else {
            currentToast = Toast.makeText(parentContext, "Time after Dash!", Toast.LENGTH_SHORT);
            currentToast.show();
        }

        // Schedule "Space between Word" task
        if (EndOfCharTaskOn) {
            charTask = new interCharTask();
            morseTimer.schedule(charTask, 3*timeUnit);
        }
    }

    // Reset the brain (Via electric shock therapy)
    public void ElectricShock() {
        InternationalStandardTrie.ResetTrie();
        morseTextView.setText("");
        charTextView.setText("");
        if (overallTextView != null) overallTextView.setText("");
        if (currentToast != null) currentToast.cancel();
        ResetProgressBar();
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

    public void StartProgressBar() {
        progressAnimation = ObjectAnimator.ofInt(progressBar, "Progress", 100);
        progressAnimation.setDuration(5*timeUnit);
        progressAnimation.setInterpolator(new LinearInterpolator());
        progressAnimation.start();
    }

    public void ResetProgressBar() {
        if (progressAnimation != null) progressAnimation.cancel();
        progressBar.setProgress(0);
    }

    // API for Output

    public void OutputMorse(String MorseCodeToOutput) {
        // Check that the string is proper characters
        if (!MorseCodeToOutput.matches("[a-zA-Z ]+")) {
            Log.d("MorseBrain", "Invalid Character found in OutputMorse");
        }

        if(MorseCodeToOutput.length() == 0) {
            Log.d("MorseBrain", "Empty OutputMorse input");
        }

        // TODO: Need to check for an input of all spaces

        // Set up tail chaining values
        TokenizedOutput = MorseCodeToOutput.split("\\s+");
        String firstCharString = TokenizedOutput[0].substring(0, 1);
        CurrentMorseOutput = GiveLetterGetMorse(firstCharString);
        OutputWordIdx = 0;
        OutputCharIdx = 0;
        OutputMorseIdx = 0;

        overallTextView.setText(TokenizedOutput[OutputWordIdx]);
        charTextView.setText(firstCharString);
        morseTextView.setText(CurrentMorseOutput);

        // Call First Tail Chaining Function
        TailChain();
    }

    private void TailChain() {
        // Maybe check a global boolean flag here to end this process

        // Check for end of character and iterate to next character index if necessary
        if (OutputMorseIdx >= CurrentMorseOutput.length()) {
            OutputCharIdx++;
            OutputMorseIdx = 0; // Reset morse index

            // Temporarily blank morse text view as the character switches
            morseTextView.setText(""); // Char Text view stays the same to give the user continuity

            // Check for end of word and iterate to next word index if necessary
            if (OutputCharIdx >= TokenizedOutput[OutputWordIdx].length()) {
                OutputWordIdx++;
                OutputCharIdx = 0; // Reset char index

                // Check for end of words and return if finished
                if (OutputWordIdx >= TokenizedOutput.length) return;

                // Update text view
                overallTextView.setText(TokenizedOutput[OutputWordIdx]);

                // Add delay between words and return to allow it to run
                delayHandler2.postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        TailChain();
                    }
                }, 7*timeUnit); // Delay between words is 7 time units
                return;
            }
            else {
                // Add delay between characters and return to allow it to run
                delayHandler2.postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        String currentCharString = TokenizedOutput[OutputWordIdx].substring(OutputCharIdx, OutputCharIdx + 1);
                        CurrentMorseOutput = GiveLetterGetMorse(currentCharString);

                        charTextView.setText(currentCharString);
                        morseTextView.setText(CurrentMorseOutput);

                        TailChain();
                    }
                }, 3*timeUnit); // Delay between characters is 3 time units
                return;
            }
        }

        feedbackDriver.on();
        delayHandler1.postDelayed(new Runnable(){
            @Override
            public void run(){
                // Turn off feedback
                feedbackDriver.off();

                // Set delay function to start next character after a time unit of silence
                delayHandler2.postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        OutputMorseIdx++;

                        TailChain();
                    }
                }, timeUnit); // Delay between dots & dashes within a character is 1 time unit
            }
        }, ((CurrentMorseOutput.charAt(OutputMorseIdx) == '-') ? 3 : 1)*timeUnit);
    }

    public void EndOutput() {
        delayHandler1.removeCallbacksAndMessages(null);
        delayHandler2.removeCallbacksAndMessages(null);

        feedbackDriver.off();

        morseTextView.setText("");
        charTextView.setText("");
        overallTextView.setText("");
    }

}
