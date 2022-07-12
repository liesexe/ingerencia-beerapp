package org.ingerencia.beerapp.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Beer {
    private int id;
    private String name;
    private String tagline;
    private String first_brewed;
    private String description;
    private String image_url;
    private float abv;
    private float ibu;
    private float target_fg;
    private float target_og;
    private float ebc;
    private float srm;
    private float ph;
    private float attenuation_level;
    private Measurement volume = new Measurement();
    private Measurement boil_volume = new Measurement();
    private Method method = new Method();
    private Ingredients ingredients = new Ingredients();
    private String[] food_pairing;
    private String brewers_tips;
    private String contributed_by;
}
