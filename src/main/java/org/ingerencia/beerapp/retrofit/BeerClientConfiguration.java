package org.ingerencia.beerapp.retrofit;

import lombok.extern.slf4j.Slf4j;
import org.ingerencia.beerapp.proxy.BeerRestProxy;
import org.ingerencia.beerapp.retrofit.base.ClientConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Slf4j
@Configuration
public class BeerClientConfiguration extends ClientConfiguration {

    @Value("${ingerencia.restapi.proxy.punkapi.baseUrl}")
    private String baseUrl;

    @Bean
    public BeerRestProxy dmBeerRestProxy(){
        Retrofit retrofit = new Retrofit
                .Builder()
                .client(getOkHttpClient())
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(BeerRestProxy.class);
    }


}
