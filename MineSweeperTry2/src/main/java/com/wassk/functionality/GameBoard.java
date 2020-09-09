package com.wassk.functionality;

public class GameBoard {
    private boolean explored;
    private boolean mined;
    private String modifiedAppearance;

    public GameBoard(boolean mined) {
        explored = false;
        this.mined = mined;
        modifiedAppearance = null;
    }

    public String getAppearance() {
        if (modifiedAppearance == null) {
            if (explored) {
                if (mined) {
                    return "X";
                } else {
                    return "O";
                }
            } else {
                return "_";
            }

        } else {
            return modifiedAppearance;
        }
    }

    public String lostGame() {
        if (modifiedAppearance == null) {
            if (explored) {
                return "X";
            } else if (!explored) {
                if (mined) {
                    return "X";
                } else {
                    return "_";
                }
            }
        }
        return modifiedAppearance;
    }

    public void setAppearance(String appereance) {
        modifiedAppearance = appereance;
    }

    public boolean isExplored() {
        if (isMined()) {
            modifiedAppearance = null;
        }
        return explored;
    }

    public boolean isMined() {
        return mined;
    }

    public void explored() {
        this.explored = true;
    }


}