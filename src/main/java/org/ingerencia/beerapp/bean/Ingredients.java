package org.ingerencia.beerapp.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class Ingredients {
    private ArrayList<ValueName> malt = new ArrayList<>();
    private ArrayList<ValueName2> hops = new ArrayList<>();
    private String yeast;
}
