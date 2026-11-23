package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Evento;
import model.Venda;

public class VendaDAO {

    public List<Venda> listar(List<Evento> eventos) {
        Map<Integer, Evento> porId = new HashMap<>();
        for (Evento e : eventos) {
            porId.put(e.getId(), e);
        }

        String sql = "SELECT id, evento_id, comprador, quantidade FROM vendas ORDER BY id";
        List<Venda> vendas = new ArrayList<>();
        try (Connection con = ConexaoDB.getConexao();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Evento evento = porId.get(rs.getInt("evento_id"));
                if (evento != null) {
                    vendas.add(new Venda(
                            rs.getInt("id"),
                            rs.getString("comprador"),
                            evento,
                            rs.getInt("quantidade")));
                }
            }
        } catch (SQLException e) {
            throw erro(e);
        }
        return vendas;
    }

    public void inserir(int eventoId, String comprador, int quantidade) {
        String sql = "INSERT INTO vendas (evento_id, comprador, quantidade) VALUES (?, ?, ?)";
        try (Connection con = ConexaoDB.getConexao();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, eventoId);
            ps.setString(2, comprador);
            ps.setInt(3, quantidade);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw erro(e);
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM vendas WHERE id = ?";
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
