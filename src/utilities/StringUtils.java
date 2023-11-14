package utilities;

/**
 * Clase de utilidades para manipular cadenas de texto.
 */
public class StringUtils {
	
    private StringUtils() throws IllegalAccessException {
        throw new IllegalAccessException("Clase de utilidad");
    }
	
    /**
     * Elimina los espacios en blanco al principio y al final de una cadena de texto.
     * Si la cadena es nula, se devuelve una cadena vacía.
     *
     * @param str la cadena de texto a recortar
     * @return la cadena de texto recortada o una cadena vacía si la cadena es nula
     */
    public static String trimToEmpty(final String str) {
        return str == null ? "" : str.trim();
    }
    
    /**
     * Elimina los espacios en blanco al principio y al final de una cadena de texto.
     * Si la cadena es nula, se devuelve nula.
     *
     * @param str la cadena de texto a recortar
     * @return la cadena de texto recortada o nula si la cadena es nula
     */
    public static String trim(final String str) {
        return str == null ? null : str.trim();
    }
    
    /**
     * Comprueba si una secuencia de caracteres es nula o está vacía.
     *
     * @param cs la secuencia de caracteres a comprobar
     * @return true si la secuencia de caracteres es nula o está vacía, false en caso contrario
     */
    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }
    
    /**
     * Comprueba si una secuencia de caracteres es nula, está vacía o contiene solo espacios en blanco.
     *
     * @param cs la secuencia de caracteres a comprobar
     * @return true si la secuencia de caracteres es nula, está vacía o contiene solo espacios en blanco, false en caso contrario
     */
    public static boolean isBlank(final CharSequence cs) {
        final int strLen = length(cs);
        if (strLen == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Obtiene la longitud de una secuencia de caracteres.
     *
     * @param cs la secuencia de caracteres
     * @return la longitud de la secuencia de caracteres o 0 si la secuencia es nula
     */
    public static int length(final CharSequence cs) {
        return cs == null ? 0 : cs.length();
    }

    /**
     * Elimina los espacios en blanco al principio y al final de una cadena de texto.
     * Si la cadena resultante es vacía, se devuelve nula.
     *
     * @param str la cadena de texto a recortar
     * @return la cadena de texto recortada o nula si la cadena resultante es vacía
     */
    public static String trimToNull(final String str) {
        final String ts = trim(str);
        return isEmpty(ts) ? null : ts;
    }
    
    /**
     * Compara dos secuencias de caracteres y devuelve true si representan la misma secuencia de caracteres,
     * ignorando las diferencias de mayúsculas y minúsculas.
     *
     * @param cs1 la primera secuencia de caracteres, puede ser nula
     * @param cs2 la segunda secuencia de caracteres, puede ser nula
     * @return true si las secuencias de caracteres son iguales (ignorando mayúsculas y minúsculas), o ambas son nulas
     */
    public static boolean equals(final CharSequence cs1, final CharSequence cs2) {
        if (cs1 == cs2) {
            return true;
        }
        if (cs1 == null || cs2 == null) {
            return false;
        }
        if (cs1.length() != cs2.length()) {
            return false;
        }
        if (cs1 instanceof String && cs2 instanceof String) {
            return cs1.equals(cs2);
        }
        // Comparación paso a paso
        final int length = cs1.length();
        for (int i = 0; i < length; i++) {
            if (cs1.charAt(i) != cs2.charAt(i)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Implementación propia de regionMatches.
     *
     * @param cs la secuencia de caracteres a procesar
     * @param ignoreCase si se debe ignorar el caso al comparar
     * @param thisStart el índice de inicio en la secuencia de caracteres cs
     * @param substring la secuencia de caracteres a buscar
     * @param start el índice de inicio en la secuencia de caracteres substring
     * @param length la longitud de la región
     * @return true si la región coincide, false en caso contrario
     */
    static boolean regionMatches(final CharSequence cs, final boolean ignoreCase, final int thisStart,
            final CharSequence substring, final int start, final int length)    {
        if (cs instanceof String && substring instanceof String) {
            return ((String) cs).regionMatches(ignoreCase, thisStart, (String) substring, start, length);
        }
        int index1 = thisStart;
        int index2 = start;
        int tmpLen = length;

        // Extraer estos valores primero para detectar NullPointerExceptions de la misma manera que la versión de java.lang.String
        final int srcLen = cs.length() - thisStart;
        final int otherLen = substring.length() - start;

        // Comprobar parámetros inválidos
        if (thisStart < 0 || start < 0 || length < 0) {
            return false;
        }

        // Comprobar que las regiones tienen suficiente longitud
        if (srcLen < length || otherLen < length) {
            return false;
        }

        while (tmpLen-- > 0) {
            final char c1 = cs.charAt(index1++);
            final char c2 = substring.charAt(index2++);

            if (c1 == c2) {
                continue;
            }

            if (!ignoreCase) {
                return false;
            }

            // La misma comprobación real que en String.regionMatches():
            final char u1 = Character.toUpperCase(c1);
            final char u2 = Character.toUpperCase(c2);
            if (u1 != u2 && Character.toLowerCase(u1) != Character.toLowerCase(u2)) {
                return false;
            }
        }

        return true;
    }
    
    /**
     * Compara dos secuencias de caracteres, devolviendo true si representan
     * secuencias de caracteres iguales, ignorando el caso.
     *
     * <p>Se manejan los casos de null sin lanzar excepciones. Dos referencias null se consideran iguales.
     * La comparación es <strong>insensible a mayúsculas y minúsculas</strong>.</p>
     *
     * @param cs1 la primera secuencia de caracteres, puede ser nula
     * @param cs2 la segunda secuencia de caracteres, puede ser nula
     * @return true si las secuencias de caracteres son iguales (ignorando el caso), o ambas son nulas
     * @since 3.0 Cambió la firma de equalsIgnoreCase(String, String) a equalsIgnoreCase(CharSequence, CharSequence)
     * @see #equals(CharSequence, CharSequence)
     */
    public static boolean equalsIgnoreCase(final CharSequence cs1, final CharSequence cs2) {
        if (cs1 == cs2) {
            return true;
        }
        if (cs1 == null || cs2 == null) {
            return false;
        }
        if (cs1.length() != cs2.length()) {
            return false;
        }
        return regionMatches(cs1, true, 0, cs2, 0, cs1.length());
    }
    
}
