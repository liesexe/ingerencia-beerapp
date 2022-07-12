package org.ingerencia.beerapp.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MashTemp {
    private Measurement temp = new Measurement();
    private int duration;
}
