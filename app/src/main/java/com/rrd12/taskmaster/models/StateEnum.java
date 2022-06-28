package com.rrd12.taskmaster.models;

import androidx.annotation.NonNull;

public enum StateEnum {
    NEW("New"),
    ASSIGNED("Assigned"),
    IN_PROGRESS("In Progress"),
    COMPLETE("Complete");

    private final String stateText;

    StateEnum(String stateText) {
        this.stateText = stateText;
    }

    public static StateEnum fromString(String possibleStateText){
        for(StateEnum state : StateEnum.values()){
            if(state.stateText.equals(possibleStateText)){
                return state;
            }
        }
        return null;
    }

    @NonNull
    @Override
    public String toString() {
        if(stateText == null){
            return "";
        }
        return stateText;
    }

    public String getStateText() {
        return stateText;
    }
}
