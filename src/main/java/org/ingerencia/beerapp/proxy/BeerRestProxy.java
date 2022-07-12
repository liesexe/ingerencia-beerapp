package org.ingerencia.beerapp.proxy;

import org.ingerencia.beerapp.bean.Beer;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.ArrayList;

public interface BeerRestProxy {

    @GET("v2/beers")
    Call<ArrayList<Beer>> getBeers();

    @GET("v2/beers/random")
    Call<ArrayList<Beer>> getRandomBeer();
}
