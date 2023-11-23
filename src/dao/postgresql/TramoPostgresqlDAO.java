package dao.postgresql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import conexion.BDConexion;
import dao.ParadaDAO;
import dao.TramoDAO;
import datastructures.TreeMap;
import modelo.Parada;
import modelo.Tramo;

/**
 * Implementación de la interfaz TramoDAO para PostgreSQL.
 */
public class TramoPostgresqlDAO implements TramoDAO {

	private Hashtable<Integer, Parada> paradas;

	/**
	 * Constructor que carga las paradas al crear una instancia del DAO.
	 */
	public TramoPostgresqlDAO() {
		paradas = cargarParadas();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void insertar(Tramo tramo) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = BDConexion.getConnection();
			String sql = "INSERT INTO public.tramos (codigo_parada1, codigo_parada2, tiempo, tipo) VALUES(?,?,?,?)";
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, tramo.getInicio().getCodigo());
			pstm.setInt(2, tramo.getFin().getCodigo());
			pstm.setInt(3, tramo.getTiempo());
			pstm.setInt(4, tramo.getTipo());
			pstm.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstm != null)
					pstm.close();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new RuntimeException(ex);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void actualizar(Tramo tramo) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = BDConexion.getConnection();
			String sql = "UPDATE public.tramos SET tiempo = ?, tipo = ? WHERE codigo_parada1 = ? AND codigo_parada2 = ?";
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, tramo.getTiempo());
			pstm.setInt(2, tramo.getTipo());
			pstm.setInt(3, tramo.getInicio().getCodigo());
			pstm.setInt(4, tramo.getFin().getCodigo());
			pstm.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstm != null)
					pstm.close();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new RuntimeException(ex);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void borrar(Tramo tramo) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = BDConexion.getConnection();
			String sql = "DELETE FROM public.tramos WHERE codigo_parada1 = ? AND codigo_parada2 = ?";
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, tramo.getInicio().getCodigo());
			pstm.setInt(2, tramo.getFin().getCodigo());
			pstm.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstm != null)
					pstm.close();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new RuntimeException(ex);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Tramo> buscarTodos() {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = BDConexion.getConnection();
			String sql = "SELECT codigo_parada1, codigo_parada2, tiempo, tipo FROM public.tramos";
			pstm = con.prepareStatement(sql);
			rs = pstm.executeQuery();
			List<Tramo> ret = new ArrayList<>();
			while (rs.next()) {
				Tramo t = new Tramo();
				t.setInicio(paradas.get(rs.getInt("codigo_parada1")));
				t.setFin(paradas.get(rs.getInt("codigo_parada2")));
				t.setTiempo(rs.getInt("tiempo"));
				t.setTipo(rs.getInt("tipo"));
				ret.add(t);
			}
			return ret;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstm != null)
					pstm.close();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new RuntimeException(ex);
			}
		}
	}

	/**
	 * Carga las paradas desde la base de datos y las devuelve en un Hashtable.
	 * 
	 * @return Un Hashtable con las paradas donde la clave es el código de la parada
	 *         y el valor es la parada.
	 */
	private Hashtable<Integer, Parada> cargarParadas() {
		Hashtable<Integer, Parada> paradas = new Hashtable<>();
		ParadaDAO paradaDAO = new ParadaPostgresqlDAO();
		TreeMap<Integer, Parada> ds = paradaDAO.buscarTodos();
		ds.entrySet().forEach(entry -> {
			paradas.put(entry.getKey(), entry.getValue());
		});
		return paradas;
	}
}
