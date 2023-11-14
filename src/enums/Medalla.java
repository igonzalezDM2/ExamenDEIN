package enums;

import java.util.Arrays;

import utilities.StringUtils;

/**
 * Enumeración Medalla
 * 
 * Esta enumeración representa las medallas posibles en los juegos olímpicos.
 */
public enum Medalla {
	ORO("Gold"),
	PLATA("Silver"),
	BRONCE("Bronze"),
	NINGUNA(null);
	
	private String valor;
	
	private Medalla(String valor) {
		this.valor = valor;
	}
	
	public String getValor() {
		return valor;
	}
	
	public static Medalla getByValor(String valor) {
		return Arrays.stream(Medalla.values())
		.filter(s -> StringUtils.equals(StringUtils.trimToNull(s.getValor()), valor))
		.findFirst().orElse(NINGUNA);
	}
}
