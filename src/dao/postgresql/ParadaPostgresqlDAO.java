package dao.postgresql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Hashtable;
import conexion.BDConexion;
import dao.ParadaDAO;
import datastructures.TreeMap;
import dao.LineaDAO;
import modelo.Parada;
import modelo.Linea;

public class ParadaPostgresqlDAO implements ParadaDAO {
	private Hashtable<String, Linea> lineas;

	public ParadaPostgresqlDAO() {
		lineas = cargarLineas();
	}

	@Override
	public void insertar(Parada parada) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = BDConexion.getConnection();
			String sql = "";
			sql += "INSERT INTO public.paradas (codigo, direccion, codigo_linea) ";
			sql += "VALUES(?,?,?) ";
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, parada.getCodigo());
			pstm.setString(2, parada.getDireccion());
			for (Linea linea : parada.getLineas())
				pstm.setString(3, linea.getCodigo());
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
	public void actualizar(Parada parada) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = BDConexion.getConnection();
			String sql = "UPDATE public.paradas ";
			sql += "SET direccion = ?, codigo_linea = ? ";
			sql += "WHERE codigo = ? ";
			pstm = con.prepareStatement(sql);
			pstm.setString(1, parada.getDireccion());
			for (Linea linea : parada.getLineas())
				pstm.setString(2, linea.getCodigo());
			pstm.setInt(3, parada.getCodigo());
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
	public void borrar(Parada parada) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = BDConexion.getConnection();
			String sql = "";
			sql += "DELETE FROM public.paradas WHERE codigo = ? ";
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, parada.getCodigo());
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
	public TreeMap<Integer, Parada> buscarTodos() {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = BDConexion.getConnection();
			String sql = "SELECT codigo, nombre, codigo_linea FROM public.paradas ";
			pstm = con.prepareStatement(sql);
			rs = pstm.executeQuery();
			TreeMap<Integer, Parada> ret = new TreeMap<>();
			while (rs.next()) {
				ret.put(rs.getInt("codigo"), new Parada(rs.getInt("codigo"), rs.getString("direccion")));
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

	private Hashtable<String, Linea> cargarLineas() {
		Hashtable<String, Linea> lineas = new Hashtable<String, Linea>();
		LineaDAO lineaDAO = new LineaPostgresqlDAO();
		TreeMap<String, Linea> ds = lineaDAO.buscarTodos();
		ds.entrySet().forEach(entry -> {
			lineas.put(entry.getKey(), entry.getValue());
		});
		return lineas;
	}
}
