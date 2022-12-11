package kr.com._29cm.homework.enumeration;

import lombok.Getter;

public enum OrderInput {
    o(false),
    q(true),
    quit(true);

    @Getter
    private boolean isQuit;

    OrderInput(boolean isQuit) {
        this.isQuit = isQuit;
    }

}
