package com.lazarev.players;


import com.opencsv.bean.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.*;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PlayersService {


    @Value("${file-name}" )
    private String fileName;

    public List<Player> findAllPlayers() throws  ResponseStatusException{
        return parseCvs(null);
    }

    public Player findPlayer(String playerId) throws ResponseStatusException {

        try{
            return  parseCvs(playerId)
                    .stream()
                    .findFirst()
                    .orElseThrow();
        }
        catch (NoSuchElementException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The playerId is not found", ex);
        }
    }

    private List<Player>  parseCvs(String playerId) throws ResponseStatusException{
        try(Reader reader = new BufferedReader(new FileReader(fileName))){
            HeaderColumnNameMappingStrategy<Player> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(Player.class);
            CsvToBeanBuilder builder = new CsvToBeanBuilder<Player>(reader)
                .withMappingStrategy(strategy)
                .withIgnoreEmptyLine(true)
                .withIgnoreLeadingWhiteSpace(true);
            if(playerId!=null) {
                builder.withFilter(new PlayerFilter(playerId));
            }
            return builder.build().parse();
        } catch (FileNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The file is not found", e);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , e.getMessage(), e);
        }
    }

}
