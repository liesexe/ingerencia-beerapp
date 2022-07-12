package org.ingerencia.beerapp.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(schema="PUBLIC", name="LOG_BEER_APP")
public class LogEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "metodo")
    private String method;

    @Column(name = "url_externo")
    private String urlExterno;

    @Column(name = "tiempo")
    private Long time;

    @Column(name = "endpoint")
    private String endpoint;
}
