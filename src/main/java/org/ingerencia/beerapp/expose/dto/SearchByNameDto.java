package org.ingerencia.beerapp.expose.dto;

import lombok.Getter;
import lombok.Setter;

public class SearchByNameDto {

    @Getter
    @Setter
    public static class Request {
        private String name;
    }
}
