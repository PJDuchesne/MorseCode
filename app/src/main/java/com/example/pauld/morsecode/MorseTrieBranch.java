package com.example.pauld.morsecode;

import android.util.Log;

public class MorseTrieBranch {
    public String BranchChar;
    public String BranchMorseCode;
    public MorseTrieBranch DotBranch;
    public MorseTrieBranch DashBranch;

    // Initialize an empty branch
    public MorseTrieBranch() {
        BranchChar = "?";
        BranchMorseCode = "";
        DotBranch = this;
        DashBranch = this;
    }

    // Iteratively creates a trie based on the standards
    public MorseTrieBranch(String InputLine[], MorseTrieBranch TailBranch) {
        BranchChar      = InputLine[0];
        BranchMorseCode = InputLine[1];

        int tmpRow;
        tmpRow = MorseCodeStandards.GetRow(InputLine[2].charAt(0), InputLine[1], true);
        if (tmpRow != -1) DotBranch = new MorseTrieBranch(MorseCodeStandards.InternationalStandard[tmpRow], TailBranch);
        else DotBranch = TailBranch;

        tmpRow = MorseCodeStandards.GetRow(InputLine[3].charAt(0), InputLine[1], false);
        if (tmpRow != -1) DashBranch = new MorseTrieBranch(MorseCodeStandards.InternationalStandard[tmpRow], TailBranch);
        else DashBranch = TailBranch;
    }
}
