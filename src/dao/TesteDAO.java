/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import apoio.ConexaoBD;
import apoio.IDAOT;
import entidade.Teste;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author eduardo
 */
public class TesteDAO implements IDAOT<Teste> {

    @Override
    public String salvar(Teste t) {
        // exemplo de insercao
        try {
            Statement st = ConexaoBD.getInstance().getConnection().createStatement();

            String sql = "insert into teste "
                    + "values"
                    + "(default, "
                    + "'" + t.getNome() + "', "
                    + "'" + t.getCpf() + "', "
                    + "'" + t.getTelefone() + "', "
                    + "'" + t.getEmail()+ "')";

            System.out.println("SQL: " + sql);

            int retorno = st.executeUpdate(sql);
            
            // se retornar null, é porque está funcionando normalmente
            return null;

            // se não retornar null, cairá no catch abaixo, imprimindo o erro que aconteceu
        } catch (Exception e) {
            System.out.println("Erro ao inserir Teste: " + e);
            return e.toString();
        }
    }

    @Override
    public String atualizar(Teste t) {
            
        
        try {
            Statement st = ConexaoBD.getInstance().getConnection().createStatement();

            // rodando sql de update no banco de dados
            String sql = "update teste "
                    + "set nome = "
                    + "'" + t.getNome()+ "', "
                    + "cpf = '" + t.getCpf() + "', "
                    + "telefone = '" + t.getTelefone() +"', "
                    + "email = '" + t.getEmail()
                    + "' where id = " + t.getId(); // fazendo update onde o id é igual ao do id
                    // passado no objeto desse método

            System.out.println("SQL: " + sql);

            int retorno = st.executeUpdate(sql);
            
            // se retornar null, é porque está funcionando normalmente
            return null;

            // se não retornar null, cairá no catch abaixo, imprimindo o erro que aconteceu
        } catch (Exception e) {
            System.out.println("Erro ao atualizar Teste: " + e);
            return e.toString();
        }
    }

    @Override
    public String excluir(int id) {
        
        // tentando criar conexão com BD e armazenando no BD através da variável st
        try {
            Statement st = ConexaoBD.getInstance().getConnection().createStatement();
            
            // script sql de delete, de acordo com o id informado como parametro do método
            String sql = "delete "  
                    + "from teste "
                    + "where id = " + id;
            
            System.out.println(sql); // log da consulta no nosso BD a ser impresso no console
            
            // executa a query de exlusão em nosso BD
            int retorno = st.executeUpdate(sql);
        
            return null;
            // se retornar null está ok           
            
        } catch(Exception e) {
            System.err.print("Erro ao exluir registro" + e);
            return e.toString(); //retornando o erro em forma de string(com exceção e.toString), já que esse método precisa de retorno
        }       
    }
    
    
    @Override
    public ArrayList consultarTodos() {
        
        ArrayList<Teste> testes = new ArrayList();
        
        try {
            //criando conexão com o banco de dados
            Statement st = ConexaoBD.getInstance().getConnection().createStatement();

            String sql = ""
                    + "select * "
                    + "from teste"
                    + " order by id";

            System.out.println("SQL: " + sql);
            //imprimindo consulta sql feita dentro do BD
            
            // nesse caso, realizar a query de consulta com o .executeQuery e 
            // colocamos ele em uma variável ResultSet, que
            ResultSet retorno = st.executeQuery(sql);
            
            // while: enquanto houver o registro do Result Set com o .next, capturamos 
            // os dados e inserimos ele dentro do Array
            while (retorno.next()) {
                Teste teste = new Teste();
                
                teste.setId(retorno.getInt("id"));
                teste.setNome(retorno.getString("nome"));
                teste.setCpf(retorno.getString("cpf"));
                teste.setTelefone(retorno.getString("telefone"));
                teste.setEmail(retorno.getString("email"));
                
                testes.add(teste);                
            }

        // capturando exceção e imprimindo ela na tela
        } catch (Exception e) {
            System.out.println("Erro ao consultar Teste: " + e);
        }
        
        return testes; // retornando o arraylist ao final do método
    }


    @Override
    public ArrayList<Teste> consultar(String criterio) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Teste consultarId(int id) {
        
        Teste teste = null;
        
        try {
            //criando conexão com o banco de dados
            Statement st = ConexaoBD.getInstance().getConnection().createStatement();

            String sql = ""
                    + "select * "
                    + "from teste "
                    + "where id = " + id;

            System.out.println("SQL: " + sql);
            //imprimindo consulta sql feita dentro do BD
            
            // nesse caso, realizar a query de consulta com o .executeQuery e 
            // colocamos ele em uma variável ResultSet, que
            ResultSet retorno = st.executeQuery(sql);
            
            // while: enquanto houver o registro do Result Set com o .next, capturamos 
            // os dados e inserimos ele dentro do Array enderecos com .add(endereco)
            while (retorno.next()) {
                teste = new Teste();
                
                //atribuímos o id do nosso idendereco de acordo com o retorno(Result Set que está presente na coluna "id" do BD)
                teste.setId(retorno.getInt("id"));
                teste.setNome(retorno.getString("nome"));
                teste.setCpf(retorno.getString("cpf"));  
                teste.setTelefone(retorno.getString("telefone"));
                teste.setEmail(retorno.getString("email"));
            }

        // capturando exceção e imprimindo ela na tela
        } catch (Exception e) {
            System.out.println("Erro ao consultar FORNECEDOR: " + e);
        }
        
        // retorna o objeto criado
        return teste;   
    }
    // método para popular a tabela com o banco de dados
    public void popularTabela(JTable tabela, String criterio) {
        
       // váriavel do tipo ResultSet para consultar no BD e gerar consulta no JTable
        ResultSet resultadoQ;
        
        // dados da tabela em uma matriz de objetos
        Object[][] dadosTabela = null;

        // cabecalho da tabela em forma de vetor de objetos
        // o cabeçalho será armazenado em vetor enquanto que os dados da table aem matrizes
        Object[] cabecalho = new Object[5];
        cabecalho[0] = "Id"; // posição 0 do vetor será o id
        cabecalho[1] = "Nome";
        cabecalho[2] = "CPF";
        cabecalho[3] = "Telefone";
        cabecalho[4] = "E-mail";

        // tenta pegar a consulta no BD com o resultadoQ, execurando a query com .executeQuery
        // de acordo com o criterio, que é um dos parametros desse método
        try {
            resultadoQ = ConexaoBD.getInstance().getConnection().createStatement().executeQuery(""
                    + "SELECT count(*) "
                    + "FROM teste "
                    + "WHERE "
                    + "nome ILIKE '%" + criterio + "%'");

            // precisa ter esse .next() em função da variável Result Set
            resultadoQ.next();

            // cria matriz de acordo com nº de registros da tabela
            // o [resultadoQ.getInt(1)] pega o número de count(*) que possui na table, ou seja serão impressas 
            // no JTable o mesmo número de count(mesmo n° de colunas que a table no BD tem)
            // o [2] representa o número de colunas da nossa matriz. Como endereco possui apenas id e nome, será
            // 2 colunas apenas que serão impressas no JTable com nossa matriz
            dadosTabela = new Object[resultadoQ.getInt(1)][5];

        } catch (Exception e) {
            System.out.println("Erro ao consultar teste: " + e);
        }

        int lin = 0;

        // executando a consulta na tabela de fato abaixo:
        try {
            resultadoQ = ConexaoBD.getInstance().getConnection().createStatement().executeQuery(""
                    + "SELECT * "
                    + "FROM teste "
                    + "WHERE "
                    + "nome ILIKE '%" + criterio + "%'"
                    + " order by id");

            //while para povoar a tabela enquanto houver dados necessários no Result Set
            while (resultadoQ.next()) {

                // coloca os dados do cabeçalho inicialmente
                dadosTabela[lin][0] = resultadoQ.getInt("id"); //linha 1 e coluna 1
                dadosTabela[lin][1] = resultadoQ.getString("Nome"); // linha 1 e coluna 2
                // de acordo com nossa matriz
                dadosTabela[lin][2] = resultadoQ.getString("CPF"); // linha 1 e coluna 3
                dadosTabela[lin][3] = resultadoQ.getString("Telefone");
                dadosTabela[lin][4] = resultadoQ.getString("Email");

                lin++; // a cada nova passagem pelo while, será incrementado o valor das linhas
                // armazenando novos dados em novos linhas conforme lin utilizando na nossa matriz
                // as colunas serão sempre 1 e 2 de acordo com [0] e [1] definindo anteriormente na matriz
            }
        } catch (Exception e) {
            System.out.println("problemas para popular tabela...");
            System.out.println(e);
        }

        // configuracoes adicionais no componente tabela
        // table.setModel passa os dados para nossa tabela JTable com os parametros definidos nesse método
        tabela.setModel(new DefaultTableModel(dadosTabela, cabecalho) {
            @Override
            // isCellEdatible = a célula é editável
            // quando retorno for FALSE, a tabela nao é editavel, ou seja, deixamos que nossas células
            // não sejam editáveis já que o retorno será false, tornando não editável
            public boolean isCellEditable(int row, int column) {
                return false;
                /*  
                 if (column == 3) {  // apenas a coluna 3 sera editavel(exemplo para deixar editável coluna específica)
                 return true;
                 } else {
                 return false;
                 }
                 */
            }

            // alteracao no metodo que determina a coluna em que o objeto ImageIcon devera aparecer
            @Override
            public Class getColumnClass(int column) {

                if (column == 2) {
//                    return ImageIcon.class;
                }
                return Object.class; // object diz que são objetos das nossas entidades
            }
        });

        // permite selecao de apenas uma linha da nossa tabela seja editavel com o método abaixo
        tabela.setSelectionMode(0);

        // redimensiona as colunas de uma tabela d3e acordo com o tamanho
        TableColumn column = null;
        for (int i = 0; i < tabela.getColumnCount(); i++) {
            column = tabela.getColumnModel().getColumn(i);
            switch (i) {
                case 0: // se tiver uma coluna só, de acordo com i < tabela.getColumnCount(); 
                    column.setPreferredWidth(5); // define a largura da coluna
                    break;
                case 1: // se tiver mais de uma coluna
                    column.setPreferredWidth(80);
                    break;
                case 2:
                    column.setPreferredWidth(80);
                    break;
                case 3: 
                    column.setPreferredWidth(40);
                case 4:
                    column.setPreferredWidth(100);
            }
        }
    }
}
