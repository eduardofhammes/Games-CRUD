/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import apoio.ConexaoBD;
import apoio.IDAOT;
import entidade.Usuario;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author eduardo
 */
public class UsuarioDAO implements IDAOT<Usuario>{

    @Override
    public String salvar(Usuario o) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String atualizar(Usuario o) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String excluir(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<Usuario> consultarTodos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<Usuario> consultar(String criterio) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Usuario consultarId(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    // classe autenticar, que recebe um Usuario como parametro e verifica a validação deste com o email e senha correspondente no BD
    public boolean autenticar(Usuario u){
        // tenta encontrar no banco de dados um usuario com o usuario(através do u.getEmail()) e 
        // senha já criptografada com md5 ('u.getSenha') e usuários com situação ativas(a)
        // só dará certo se ambas forem verdadeiras(AND na consulta)
        try {
            String sql = "SELECT email, senha from usuario " +
                    "WHERE email = '"+ u.getEmail() + "' AND senha = md5('" +
                    u.getSenha() + "') AND situacao = 'a'";
                    
            // print no console do sql rodado no BD        
            System.out.println("SQL: "+ sql);
            
            // executando a query no banco de dados
            ResultSet resultadoQ = ConexaoBD.getInstance().getConnection().createStatement().executeQuery(sql);
            
            // Se retornou alguma coisa com .next(), significado que este é usuário que tem o mesmo senha e login registrado
            // assim, será feito o acesso com o return true;
            if (resultadoQ.next()) {
                return true;
            } else {
                return false; // se não encontrou nenhum registro de email e senha no BD, retorna false e impede o acesso
            }
        } catch(Exception e) {
            System.err.println(e);
            return false; // significa que não foi possível autenticar
        }
        
    }
}
