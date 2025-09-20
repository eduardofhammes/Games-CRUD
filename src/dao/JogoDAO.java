
package dao;

import apoio.ConexaoBD;
import apoio.IDAOT;
import entidade.Jogo;
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
public class JogoDAO implements IDAOT<Jogo> {

    @Override
    public String salvar(Jogo j) {
        // exemplo de insercao
        try {
            Statement st = ConexaoBD.getInstance().getConnection().createStatement();

            String sql = "insert into jogo "
                    + "values"
                    + "(default, "
                    + "'" + j.getNome() + "', "
                    + "'" + j.getData_lancamento() + "', "
                    + "" + j.getValor() + ", "
                    + "" + j.getTipo_id()+ ")";

            System.out.println("SQL: " + sql);

            int retorno = st.executeUpdate(sql);
            
            // se retornar null, é porque está funcionando normalmente
            return null;

            // se não retornar null, cairá no catch abaixo, imprimindo o erro que aconteceu
        } catch (Exception e) {
            System.out.println("Erro ao inserir Jogo: " + e);
            return e.toString();
        }
    }

    @Override
    public String atualizar(Jogo j) {
            
        
        try {
            Statement st = ConexaoBD.getInstance().getConnection().createStatement();

            // rodando sql de update no banco de dados
            String sql = "update jogo "
                    + "set nome = "
                    + "'" + j.getNome()+ "', "
                    + "data_lancamento = '" + j.getData_lancamento() + "', "
                    + "valor = " + j.getValor() +", "
                    + "tipo_id = " + j.getTipo_id()
                    + " where id = " + j.getId(); // fazendo update onde o id é igual ao do id
                    // passado no objeto desse método

            System.out.println("SQL: " + sql);

            int retorno = st.executeUpdate(sql);
            
            // se retornar null, é porque está funcionando normalmente
            return null;

            // se não retornar null, cairá no catch abaixo, imprimindo o erro que aconteceu
        } catch (Exception e) {
            System.out.println("Erro ao atualizar Jogo: " + e);
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
                    + "from jogo "
                    + "where id = " + id;
            
            System.out.println(sql); // log da consulta no nosso BD a ser impresso no console
            
            // executa a query de exlusão em nosso BD
            int retorno = st.executeUpdate(sql);
        
            return null;
            // se retornar null está ok           
            
        } catch(Exception e) {
            System.err.print("Erro ao exluir registro: " + e);
            return e.toString(); //retornando o erro em forma de string(com exceção e.toString), já que esse método precisa de retorno
        }       
    }

    @Override
    public ArrayList<Jogo> consultarTodos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<Jogo> consultar(String criterio) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Jogo consultarId(int id) {
        
        Jogo jogo = null;
        
        try {
            //criando conexão com o banco de dados
            Statement st = ConexaoBD.getInstance().getConnection().createStatement();

            String sql = ""
                    + "select * "
                    + "from jogo "
                    + "where id = " + id;

            System.out.println("SQL: " + sql);
            //imprimindo consulta sql feita dentro do BD
            
            // nesse caso, realizar a query de consulta com o .executeQuery e 
            // colocamos ele em uma variável ResultSet, que
            ResultSet retorno = st.executeQuery(sql);
            
            // while: enquanto houver o registro do Result Set com o .next, capturamos 
            // os dados e inserimos ele dentro do Array enderecos com .add(endereco)
            while (retorno.next()) {
                jogo = new Jogo();
                
                //atribuímos o id do nosso idendereco de acordo com o retorno(Result Set que está presente na coluna "id" do BD)
                jogo.setId(retorno.getInt("id"));
                jogo.setNome(retorno.getString("nome"));
                jogo.setData_lancamento(retorno.getString("data_lancamento"));  
                jogo.setValor(retorno.getDouble("valor"));
                jogo.setTipo_id(retorno.getInt("tipo_id"));
            }

        // capturando exceção e imprimindo ela na tela
        } catch (Exception e) {
            System.out.println("Erro ao consultar JOGO: " + e);
        }
        
        // retorna o objeto criado
        return jogo;   
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
        cabecalho[2] = "Data Lançamento";
        cabecalho[3] = "Valor";
        cabecalho[4] = "Tipo";

        // tenta pegar a consulta no BD com o resultadoQ, execurando a query com .executeQuery
        // de acordo com o criterio, que é um dos parametros desse método
        try {
            resultadoQ = ConexaoBD.getInstance().getConnection().createStatement().executeQuery(""
                    + "SELECT count(*) "
                    + "FROM jogo "
                    + "WHERE "
                    + "jogo.nome ILIKE '%" + criterio + "%'");

            // precisa ter esse .next() em função da variável Result Set
            resultadoQ.next();

            // cria matriz de acordo com nº de registros da tabela
            // o [resultadoQ.getInt(1)] pega o número de count(*) que possui na table, ou seja serão impressas 
            // no JTable o mesmo número de count(mesmo n° de colunas que a table no BD tem)
            // o [2] representa o número de colunas da nossa matriz. Como endereco possui apenas id e nome, será
            // 2 colunas apenas que serão impressas no JTable com nossa matriz
            dadosTabela = new Object[resultadoQ.getInt(1)][5];

        } catch (Exception e) {
            System.out.println("Erro ao consultar jogo: " + e);
        }

        int lin = 0;

        // executando a consulta na tabela de fato abaixo:
        try {
            resultadoQ = ConexaoBD.getInstance().getConnection().createStatement().executeQuery(""
                    + "SELECT jogo.id, jogo.nome, jogo.data_lancamento, jogo.valor, tipo.id as tipo_id, tipo.nome as tipo_nome "
                    + "FROM jogo, tipo "
                    + "WHERE jogo.tipo_id = tipo.id " 
                    + "and jogo.nome ILIKE '%" + criterio + "%'"
                    + " order by jogo.id");

            //while para povoar a tabela enquanto houver dados necessários no Result Set
            while (resultadoQ.next()) {

                // coloca os dados do cabeçalho inicialmente
                dadosTabela[lin][0] = resultadoQ.getInt("id"); //linha 1 e coluna 1
                dadosTabela[lin][1] = resultadoQ.getString("Nome"); // linha 1 e coluna 2
                // de acordo com nossa matriz
                dadosTabela[lin][2] = resultadoQ.getString("data_lancamento"); // linha 1 e coluna 3
                dadosTabela[lin][3] = resultadoQ.getDouble("Valor");
                dadosTabela[lin][4] = resultadoQ.getString("tipo_nome");

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
