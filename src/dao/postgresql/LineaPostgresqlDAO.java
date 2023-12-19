package dao.postgresql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import conexion.BDConexion;
import dao.LineaDAO;
import java.util.ArrayList;
import java.util.List;

import datastructures.TreeMap;
import modelo.Linea;
import modelo.Parada;

/**
 * Implementación de la interfaz LineaDAO para PostgreSQL.
 */
public class LineaPostgresqlDAO implements LineaDAO {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void insertar(Linea linea) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = BDConexion.getConnection();
			String sql = "INSERT INTO poo2023.lineas_juanmaty (codigo, comienza, finaliza, frecuencia) VALUES(?,?,?,?)";
			pstm = con.prepareStatement(sql);
			pstm.setString(1, linea.getCodigo());
			pstm.setInt(2, linea.getComienza());
			pstm.setInt(3, linea.getFinaliza());
			pstm.setInt(4, linea.getFrecuencia());
			pstm.executeUpdate();
			pstm.close();
			sql = "INSERT INTO poo2023.lineas_paradas_juanmaty (cod_lineas, cod_paradas) VALUES(?,?)";
			pstm = con.prepareStatement(sql);
			for (Parada p : linea.getParadas()) {
				pstm.setString(1, linea.getCodigo());
				pstm.setInt(2, p.getCodigo());
				pstm.executeUpdate();
			}
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
	public void actualizar(Linea linea) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = BDConexion.getConnection();
			String sql = "UPDATE poo2023.lineas_juanmaty SET comienza = ?, finaliza = ?, frecuencia = ? WHERE codigo = ?";
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, linea.getComienza());
			pstm.setInt(2, linea.getFinaliza());
			pstm.setInt(3, linea.getFrecuencia());
			pstm.setString(4, linea.getCodigo());
			pstm.executeUpdate();
			pstm.close();
			sql = "DELETE FROM poo2023.lineas_paradas_juanmaty WHERE cod_lineas = ?";
			pstm = con.prepareStatement(sql);
			pstm.setString(1, linea.getCodigo());
			pstm.executeUpdate();
			pstm.close();
			sql = "INSERT INTO poo2023.lineas_paradas_juanmaty (cod_lineas, cod_paradas) VALUES(?,?)";
			pstm = con.prepareStatement(sql);
			for (Parada p : linea.getParadas()) {
				pstm.setString(1, linea.getCodigo());
				pstm.setInt(2, p.getCodigo());
				pstm.executeUpdate();
			}
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
	public void borrar(Linea linea) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = BDConexion.getConnection();
			String sql = "DELETE FROM poo2023.lineas_juanmaty WHERE codigo = ?";
			pstm = con.prepareStatement(sql);
			pstm.setString(1, linea.getCodigo());
			pstm.executeUpdate();
			pstm.close();
			sql = "DELETE FROM poo2023.lineas_paradas_juanmaty WHERE cod_lineas = ?";
			pstm = con.prepareStatement(sql);
			pstm.setString(1, linea.getCodigo());
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
	public TreeMap<String, Linea> buscarTodos() {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = BDConexion.getConnection();
			String sql = "SELECT codigo, comienza, finaliza, frecuencia FROM poo2023.lineas_juanmaty";
			pstm = con.prepareStatement(sql);
			rs = pstm.executeQuery();
			TreeMap<String, Linea> ret = new TreeMap<>();
			while (rs.next()) {
				Linea nuevaLinea = new Linea(rs.getString("codigo"), rs.getInt("comienza"), rs.getInt("finaliza"),
						rs.getInt("frecuencia"));
				for (Parada p : cargarParadas(nuevaLinea))
					nuevaLinea.agregarParada(p);
				ret.put(rs.getString("codigo"), nuevaLinea);
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

	private List<Parada> cargarParadas(Linea linea) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = BDConexion.getConnection();
			String sql = "SELECT cod_paradas, direccion from poo2023.lineas_paradas_juanmaty JOIN poo2023.paradas_juanmaty ON (cod_paradas = codigo)";
			sql += " WHERE (cod_lineas = ?)";
			sql += " ORDER BY orden ASC";
			pstm = con.prepareStatement(sql);
			pstm.setString(1, linea.getCodigo());
			rs = pstm.executeQuery();
			List<Parada> ret = new ArrayList<Parada>();
			while (rs.next())
				ret.add(new Parada(rs.getInt("cod_paradas"), rs.getString("direccion")));
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
