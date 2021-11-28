package facade.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class EncontroDTO implements Serializable {

	private static final long serialVersionUID = 42;
	
	private int numero;
    private LocalDate dataRealizacao;
    private String part1;
    private String part2;
    private int fase;
    
    public EncontroDTO(int numero, LocalDate dataRealizacao, String part1,
	    String part2, int fase) {
	this.numero = numero;
	this.dataRealizacao = dataRealizacao;
	this.part1 = part1;
	this.part2 = part2;
	this.fase = fase;
    }
    
    public int getNumero() {
        return numero;
    }
    
    public LocalDate getDataRealizacao() {
        return dataRealizacao;
    }
    
    public String getPart1() {
        return part1;
    }
    
    public String getPart2() {
        return part2;
    }
    
    public int getFase() {
        return fase;
    }

}
