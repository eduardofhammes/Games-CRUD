
package apoio;

import java.util.ArrayList;

/**
 *
 * @author eduardo
 */
public interface IDAOT<T> {
    
    // métodos que serão implementados nas classes específicas para manipulação das informações com o BD
    public String salvar(T o);

    public String atualizar(T o);

    public String excluir(int id);

    public ArrayList<T> consultarTodos();

    public ArrayList<T> consultar(String criterio);

    public T consultarId(int id);
}

