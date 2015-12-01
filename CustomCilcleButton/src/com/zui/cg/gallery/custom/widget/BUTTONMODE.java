package com.zui.cg.gallery.custom.widget;
/**
 * @author stone
 * */
public enum BUTTONMODE {
    SINGLEMODE("single"), CHOICEMODE("mutil");
    private String mode;
    
    private BUTTONMODE(String mode){
        this.mode = mode;
    }

    @Override
    public String toString() {
        return super.toString() + " (" + mode + ")";
    }
    
    public String getMode(){
        return mode;
    }
    
    public void setMode(String mode){
        this.mode = mode;
    }
}
