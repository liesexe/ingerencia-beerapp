package org.ingerencia.beerapp.business;

import org.ingerencia.beerapp.bean.Beer;
import org.ingerencia.beerapp.expose.dto.SearchByNameDto;

import java.util.ArrayList;

public interface BeerService {
    ArrayList<Beer> getBeers();

    Beer getBeer(SearchByNameDto.Request request);

    Beer getRandomBeer();
}
