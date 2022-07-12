package org.ingerencia.beerapp.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class Method {
    private ArrayList<MashTemp> mash_temp = new ArrayList<>();
    private Fermentation fermentation = new Fermentation();
    private String twist;
}
