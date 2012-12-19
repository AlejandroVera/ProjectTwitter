package interfacesComunes;

import java.sql.ResultSet;
import java.util.List;

/**
 * Representa la conexión con la base de datos. Solo utilizada por el servidor.
 */
public interface Conexion {

	/**
	 * Ejecuta una query de consulta (inseguro si no se ha filtrado la query previamente).
	 * @param query Texto de la query a ejecutar (debe ser un SELECT).
	 * @return Un ResultSet si la query se ha ejecutado correctamente; null en caso de error.
	 */
	public abstract ResultSet query(String query);

	/**
	 * Ejecuta una query de actualizacion (inseguro si no se ha filtrado la query previamente).
	 * @param query Texto de la query de actualizacion a ejecutar (UPDATE, INSERT, DELETE...).
	 * @return Un entero con el numero de filas afectadas o 0 en caso de que esa consulta no devuelva nada. Negativo en caso de error.
	 */
	public abstract Integer updateQuery(String query);

	/**
	 * Ejecuta una query de consulta de manera segura (filtrando parametros).
	 * @param query Texto de la query a ejecutar  conteniendo '?' en donde van los parametros.
	 * @param params Parametros de la query (debe haber el mismo numero que '?' hay en la query).
	 * @return Un ResultSet si la query se ha ejecutado correctamente; null en caso de error.
	 */
	public abstract ResultSet query(String query, List<Object> params);

	/**
	 * Ejecuta una query de actualizacion de manera segura (filtrando parametros).
	 * @param query Texto de la query a ejecutar  conteniendo '?' en donde van los parametros.
	 * @param params Parametros de la query (debe haber el mismo numero que '?' hay en la query).
	 * @return Un entero con el numero de filas afectadas o 0 en caso de que esa consulta no devuelva nada. Negativo en caso de error.
	 */
	public abstract Integer updateQuery(String query, List<Object> params);

}