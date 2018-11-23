package com.example.pauld.morsecode;

public class MorseTrie {
    private MorseTrieBranch StartBranch;
    private MorseTrieBranch CurrentBranch;
    private MorseTrieBranch TailBranch;

    public MorseTrie() {
        // Iteratively creates everything. Turtles all the way down.
        TailBranch = new MorseTrieBranch();
        StartBranch = new MorseTrieBranch(MorseCodeStandards.InternationalStandard[0], TailBranch);
        CurrentBranch = StartBranch;
    }

    public MorseTrieBranch InputDot() {
        CurrentBranch = CurrentBranch.DotBranch;
        return CurrentBranch;
    }

    public MorseTrieBranch InputDash() {
        CurrentBranch = CurrentBranch.DashBranch;
        return CurrentBranch;
    }

    public void ResetTrie() {
        CurrentBranch = StartBranch;
    }

    public MorseTrieBranch GetCurrentBranch() {
        return CurrentBranch;
    }

    public MorseTrieBranch GetStartBranch() {
        return StartBranch;
    }

}
