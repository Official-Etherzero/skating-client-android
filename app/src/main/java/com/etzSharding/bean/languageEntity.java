package com.etzSharding.bean;

public class languageEntity {
    private int lid;
    private String language;
    private boolean isSelect;

    public languageEntity(int id, String language,boolean isSelect) {
        this.lid = id;
        this.language = language;
        this.isSelect=isSelect;
    }

    public int getLid() {
        return lid;
    }

    public String getLanguage() {
        return language;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
