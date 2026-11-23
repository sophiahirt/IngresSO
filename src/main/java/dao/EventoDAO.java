package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Evento;

public class EventoDAO {

    public List<Evento> listar() {
        String sql = "SELECT e.id, e.nome, e.preco, e.capacidade, "
                + "COALESCE(SUM(v.quantidade), 0) AS vendidos "
                + "FROM eventos e LEFT JOIN vendas v ON v.evento_id = e.id "
                + "GROUP BY e.id, e.nome, e.preco, e.capacidade "
                + "ORDER BY e.id";
        List<Evento> eventos = new ArrayList<>();
        try (Connection con = ConexaoDB.getConexao();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                eventos.add(new Evento(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getDouble("preco"),
                        rs.getInt("capacidade"),
                        rs.getInt("vendidos")));
            }
        } catch (SQLException e) {
            throw erro(e);
        }
        return eventos;
    }

    public void inserir(String nome, double preco, int capacidade) {
        String sql = "INSERT INTO eventos (nome, preco, capacidade) VALUES (?, ?, ?)";
        try (Connection con = ConexaoDB.getConexao();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nome);
            ps.setDouble(2, preco);
            ps.setInt(3, capacidade);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw erro(e);
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM eventos WHERE id = ?";
        try (Connection con = ConexaoDB.getConexao();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw erro(e);
        }
    }

    private BancoException erro(SQLException e) {
        return new BancoException("Erro ao acessar o banco de dados: " + e.getMessage(), e);
    }
}
