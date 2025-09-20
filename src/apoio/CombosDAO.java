
package apoio;

import java.sql.ResultSet;
import javax.swing.JComboBox;

/**
 *
 * @author eduardo
 */
public class CombosDAO {
    ResultSet resultado = null;

    // construtor 1, que recebe apenas o nome da nossa tabela no BD e o elemento JComboBox utilizado
    public void popularCombo(String tabela, JComboBox combo) {

        combo.removeAllItems(); // remove todos os item que estiverem dentro do combo

        ComboItem item = new ComboItem(); //objeto da classe ComboItem.java
        item.setId(0); // define o código desse item como 0
        item.setDescricao("Selecione"); // define a descrição do desse item que tem cód 0 como Selecione
        combo.addItem(item); // adiciona esse objeto de cód 0 e descrição "Selecione" para dentro do nosso combo box, assim
        // ele ficará junto das cidades já existentes, mas como inicial pois tem cód 0

        // tentativa de acesso ao banco de dados, pegando tudo da tabela informada como parametro
        try {
            resultado = new ConexaoBD().getConnection().createStatement().executeQuery(""
                    + "select * "
                    + "from " + tabela + " "
                    + "order by 2"); // order by 2, significado que será ordenado pela segunda coluna ta bela
            // nesse caso, será ordenado pela ordem alfabética da descrição de Cidades

            // nesse while, é criado um novo ComboItem, que salva dentro de cada objeto criado no loop
            // o cód. e a descrição presente dentro do BD de acordo com a variável ResultSet resultado
            if (resultado.isBeforeFirst()) {
                while (resultado.next()) {
                    item = new ComboItem();
                    item.setId(resultado.getInt(1));
                    item.setDescricao(resultado.getString(2));

                    combo.addItem(item); // adiciona um novo item para dentro do ComboBox depois que pegar o cód e descrição
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao popular Combo = " + e.toString());
        }
    }

    // construtor 2 - sobrecarga
    // nesse método, é feita a mesma coisa, porém, temos parametros de campos(campo1 e campo2), assim, precisará ser 
    // passado os parametros como cód no campo1 e descrição no campo2 para evitar maiores problemas
    // também é adicionado uma String de complementoSQL, que permite filtrar no select de acordo com o desejo(por exemplo:
    // "where id = 5" como complementoSQL
    public void popularCombo(String tabela, String campo1, String campo2, JComboBox combo, String complementoSQL) {

        combo.removeAllItems();

        ComboItem item = new ComboItem();
        item.setId(0);
        item.setDescricao("Selecione");
        combo.addItem(item);

        try {
            resultado = new ConexaoBD().getConnection().createStatement().executeQuery(""
                    + "select * "
                    + "from " + tabela + " "
                    + "" + complementoSQL);

            if (resultado.isBeforeFirst()) {
                while (resultado.next()) {
                    item = new ComboItem();
                    item.setId(resultado.getInt(campo1));
                    item.setDescricao(resultado.getString(campo2));

                    combo.addItem(item);
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao popular Combo = " + e.toString());
        }
    }

    public void definirItemCombo(JComboBox combo, ComboItem item) {
        for (int i = 0; i < combo.getItemCount(); i++) {
            if (((ComboItem) combo.getItemAt(i)).getId() == (item.getId())) {
                combo.setSelectedIndex(i);
                return;
            }
        }
    }
}
