package frgp.utn.edu.ar.quepasa.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

/**
 * Entidad que representa un país.
 * <p>
 * Datos obtenidos de <a href="https://public.opendatasoft.com/explore/dataset/countries-codes/table/">OpenDataSoft</a>.
 * </p>
 */
public class Country {

    private String iso3;
    private String label;

    public Country() {}

    /**
     * Devuelve el código ISO3 relacionado al país.
     * <p>
     *     El código ISO 3166-1 alpha-3 (O ISO3), es un código de tres letras que define un país según el estándar ISO 3166-1.
     * </p>
     */
    @Id
    public String getIso3() { return iso3; }

    /**
     * Devuelve el nombre oficial del país, en español.
     */
    @Column
    public String getLabel() { return label; }

    public void setIso3(String iso3) { this.iso3 = iso3; }
    public void setLabel(String label) { this.label = label; }

}
