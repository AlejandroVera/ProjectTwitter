package interfacesComunes;

import java.sql.ResultSet;
import java.util.List;

public interface Conexion {

	/**
	 * Ejecuta una query de consulta (inseguro si no se ha filtrado la query previamente).
	 * @param query Texto de la query a ejecutar (debe ser un SELECT).
	 * @return Un ResultSet si la query se ha ejecutado correctamente; null en caso de error.
	 */
	public abstract ResultSet query(String query);

	/**
	 * Ejecuta una query de actualización (inseguro si no se ha filtrado la query previamente).
	 * @param query Texto de la query de actualización a ejecutar (UPDATE, INSERT, DELETE...).
	 * @return Un entero con el número de filas afectadas o 0 en caso de que esa consulta no devuelva nada. Null en caso de error.
	 */
	public abstract Integer updateQuery(String query);

	/**
	 * Ejecuta una query de consulta de manera segura (filtrando parámetros).
	 * @param query Texto de la query a ejecutar  conteniendo '?' en donde van los parámetros.
	 * @param params Parámetros de la query (debe haber el mismo número que '?' hay en la query).
	 * @return Un ResultSet si la query se ha ejecutado correctamente; null en caso de error.
	 */
	public abstract ResultSet query(String query, List<Object> params);

	/**
	 * Ejecuta una query de actualización de manera segura (filtrando parámetros).
	 * @param query Texto de la query a ejecutar  conteniendo '?' en donde van los parámetros.
	 * @param params Parámetros de la query (debe haber el mismo número que '?' hay en la query).
	 * @return Un entero con el número de filas afectadas o 0 en caso de que esa consulta no devuelva nada. Null en caso de error.
	 */
	public abstract Integer updateQuery(String query, List<Object> params);

}