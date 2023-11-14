package enums;

import java.util.Arrays;

/**
 * Enumeración para las temporadas.
 */
public enum Temporada {
	VERANO("Summer"),
	
	INVIERNO("Winter");
	
	private String valor;
	
	private Temporada(String valor) {
		this.valor = valor;
	}
	
	public String getValor() {
		return valor;
	}
	
	public static Temporada getByValor(String valor) {
		return Arrays.stream(Temporada.values())
		.filter(s -> s.getValor().equals(valor))
		.findFirst().orElse(null);
	}
	
}
