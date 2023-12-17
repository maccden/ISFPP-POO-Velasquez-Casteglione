package dao.postgresql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import conexion.BDConexion;
import dao.ParadaDAO;
import datastructures.TreeMap;
import modelo.Parada;

/**
 * Implementación de la interfaz ParadaDAO para PostgreSQL.
 */
public class ParadaPostgresqlDAO implements ParadaDAO {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void insertar(Parada parada) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = BDConexion.getConnection();
			String sql = "INSERT INTO poo2023.paradas_juanmaty (codigo, direccion) VALUES(?,?)";
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, parada.getCodigo());
			pstm.setString(2, parada.getDireccion());
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
	public void actualizar(Parada parada) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = BDConexion.getConnection();
			String sql = "UPDATE poo2023.paradas_juanmaty SET direccion = ? WHERE codigo = ?";
			pstm = con.prepareStatement(sql);
			pstm.setString(1, parada.getDireccion());
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void borrar(Parada parada) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = BDConexion.getConnection();
			String sql = "DELETE FROM poo2023.paradas_juanmaty WHERE codigo = ?";
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TreeMap<Integer, Parada> buscarTodos() {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = BDConexion.getConnection();
			String sql = "SELECT codigo, direccion FROM poo2023.paradas_juanmaty";
			pstm = con.prepareStatement(sql);
			rs = pstm.executeQuery();
			TreeMap<Integer, Parada> ret = new TreeMap<>();
			while (rs.next())
				ret.put(rs.getInt("codigo"), new Parada(rs.getInt("codigo"), rs.getString("direccion")));
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
}
