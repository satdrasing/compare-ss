package com.satendra;

public enum SearchId {

    BSSDID("bsddid"),

    PERSONEN_ID("personenID"),

    KASSENZEICHEN("kassenzeichen");

    private final String searchValue;

    SearchId(String searchValue) {
        this.searchValue = searchValue;
    }

    @Override
    public String toString() {
        return searchValue;
    }
}
