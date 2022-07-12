package org.ingerencia.beerapp.business.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ingerencia.beerapp.bean.Beer;
import org.ingerencia.beerapp.business.BeerService;
import org.ingerencia.beerapp.dao.entity.LogEntity;
import org.ingerencia.beerapp.dao.repository.LogRepository;
import org.ingerencia.beerapp.exception.NoSuchElementFoundException;
import org.ingerencia.beerapp.exception.RetrofitCallException;
import org.ingerencia.beerapp.expose.dto.SearchByNameDto;
import org.ingerencia.beerapp.proxy.BeerRestProxy;
import org.ingerencia.beerapp.retrofit.base.ProxyConsumer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.ingerencia.beerapp.retrofit.base.ProxyConsumer.consume;

@Slf4j
@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {

    private final BeerRestProxy beerRestProxy;
    private final LogRepository logRepository;

    @Override
    public ArrayList<Beer> getBeers() {
        ArrayList<Beer> response = new ArrayList<>();
        try{
            response = consume(beerRestProxy.getBeers());
            ProxyConsumer.LogEntityG.setEndpoint("Obtener todas las cervezas");
            saveLog(ProxyConsumer.LogEntityG);
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return response;
    }

    @Override
    public Beer getBeer(SearchByNameDto.Request request) {
        try{
            ArrayList<Beer> response = consume(beerRestProxy.getBeers());
            ProxyConsumer.LogEntityG.setEndpoint("Obtener cerveza por nombre");
            saveLog(ProxyConsumer.LogEntityG);
            List<Beer> beer = response
                    .stream()
                    .filter(b -> b.getName().equals(request.getName()))
                    .collect(Collectors.toList());
            return beer.get(0);
        }catch (IndexOutOfBoundsException ex){
            throw new NoSuchElementFoundException("No existe algun registro con ese nombre", HttpStatus.NOT_FOUND);
        } catch (Exception e){
            log.info(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public Beer getRandomBeer() {
        ArrayList<Beer> response = new ArrayList<>();
        try{
            response = consume(beerRestProxy.getRandomBeer());
            ProxyConsumer.LogEntityG.setEndpoint("Obtener cerveza aleatoria");
            saveLog(ProxyConsumer.LogEntityG);
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return response.get(0);
    }

    private void saveLog(LogEntity logs){
        try{
            logRepository.save(logs);
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }
}
