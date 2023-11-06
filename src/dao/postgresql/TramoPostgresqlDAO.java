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

public class TramoPostgresqlDAO implements TramoDAO {
	private Hashtable<Integer, Parada> paradas;

	public TramoPostgresqlDAO() {
		paradas = cargarParadas();
	}

	@Override
	public void insertar(Tramo tramo) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = BDConexion.getConnection();
			String sql = "";
			sql += "INSERT INTO public.tramos (codigo_parada1, codigo_parada2, tiempo, tipo) ";
			sql += "VALUES(?,?,?,?) ";
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

	@Override
	public void actualizar(Tramo tramo) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = BDConexion.getConnection();
			String sql = "UPDATE public.tramos ";
			sql += "SET tiempo = ?, tipo = ? ";
			sql += "WHERE codigo_parada1 = ? AND codigo_parada2 = ? ";
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

	@Override
	public void borrar(Tramo tramo) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = BDConexion.getConnection();
			String sql = "";
			sql += "DELETE FROM public.tramos WHERE codigo_parada1 = ? AND codigo_parada2 = ? ";
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

	@Override
	public List<Tramo> buscarTodos() {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = BDConexion.getConnection();
			String sql = "SELECT codigo_parada1, codigo_parada2, tiempo, tipo FROM public.tramos ";
			pstm = con.prepareStatement(sql);
			rs = pstm.executeQuery();
			List<Tramo> ret = new ArrayList<Tramo>();
			while (rs.next()) {
				Tramo t = new Tramo();
				t.setInicio(paradas.get(rs.getInt("codigo_parada")));
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

	private Hashtable<Integer, Parada> cargarParadas() {
		Hashtable<Integer, Parada> paradas = new Hashtable<Integer, Parada>();
		ParadaDAO paradaDAO = new ParadaPostgresqlDAO();
		TreeMap<Integer, Parada> ds = paradaDAO.buscarTodos();
		ds.entrySet().forEach(entry -> {
			paradas.put(entry.getKey(), entry.getValue());
		});
		return paradas;
	}
}