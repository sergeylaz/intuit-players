package com.lazarev.players;


import com.opencsv.bean.CsvToBeanFilter;
import com.opencsv.bean.MappingStrategy;

public class PlayerFilter implements CsvToBeanFilter {


    private String playerIdValue;

    public PlayerFilter( String playerIdValue ) {
        this.playerIdValue = playerIdValue;
    }

    public boolean allowLine(String[] line) {
        String playerId = line[0];
        return playerId.equals(playerIdValue);
    }
}