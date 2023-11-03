package dao.postgresql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import conexion.BDConexion;
import dao.LineaDAO;
import datastructures.TreeMap;
import modelo.Linea;

public class LineaPostgresqlDAO implements LineaDAO {

	@Override
	public void insertar(Linea linea) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = BDConexion.getConnection();
			String sql = "";
			sql += "INSERT INTO public.lineas (codigo, comienza, finaliza, frecuencia) ";
			sql += "VALUES(?,?,?,?) ";
			pstm = con.prepareStatement(sql);
			pstm.setString(1, linea.getCodigo());
			pstm.setInt(2, linea.getComienza());
			pstm.setInt(3, linea.getFinaliza());
			pstm.setInt(4, linea.getFrecuencia());
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
	public void actualizar(Linea linea) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = BDConexion.getConnection();
			String sql = "UPDATE public.lineas ";
			sql += "SET comienza = ?, finaliza = ?, frecuencia = ? ";
			sql += "WHERE codigo = ? ";
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, linea.getComienza());
			pstm.setInt(2, linea.getFinaliza());
			pstm.setInt(3, linea.getFrecuencia());
			pstm.setString(4, linea.getCodigo());
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
	public void borrar(Linea linea) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = BDConexion.getConnection();
			String sql = "";
			sql += "DELETE FROM public.lineas WHERE codigo = ? ";
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

	@Override
	public TreeMap<String, Linea> buscarTodos() {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = BDConexion.getConnection();
			String sql = "SELECT codigo, comienza, finaliza, frecuencia FROM public.lineas ";
			pstm = con.prepareStatement(sql);
			rs = pstm.executeQuery();
			TreeMap<String, Linea> ret = new TreeMap<>();
			while (rs.next()) {
				ret.put(rs.getString("codigo"), new Linea(rs.getString("codigo"), 
				rs.getInt("comienza"), rs.getInt("finaliza"),rs.getInt("frecuencia")));		
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
}
