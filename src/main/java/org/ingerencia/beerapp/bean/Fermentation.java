package org.ingerencia.beerapp.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Fermentation {
    private Measurement temp = new Measurement();
}
