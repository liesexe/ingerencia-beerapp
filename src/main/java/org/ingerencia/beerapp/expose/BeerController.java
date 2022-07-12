package org.ingerencia.beerapp.expose;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import org.ingerencia.beerapp.bean.Beer;
import org.ingerencia.beerapp.bean.Excepcion;
import org.ingerencia.beerapp.business.BeerService;
import org.ingerencia.beerapp.exception.NoSuchElementFoundException;
import org.ingerencia.beerapp.expose.dto.SearchByNameDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/beer")
public class BeerController {

    private final BeerService beerService;

    @GetMapping(value = "")
    public ArrayList<Beer> getBeers(){
        return beerService.getBeers();
    }

    @PostMapping(value = "")
    public Beer getBeer(@RequestBody SearchByNameDto.Request request){ return beerService.getBeer(request);}

    @GetMapping(value = "/random")
    public Beer getRandomBeer(){
        return beerService.getRandomBeer();
    }

    @ExceptionHandler(NoSuchElementFoundException.class)
    public ResponseEntity<Excepcion> handleNoSuchElementFoundException(NoSuchElementFoundException exception) {
        Excepcion excepcion = new Excepcion();
        excepcion.setCode(404);
        excepcion.setMessage(exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(excepcion);
    }
}
