
package apoio;

/**
 *
 * @author eduardo
 */
public class ComboItem {
    
    private int id;
    private String descricao;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    // o toString é muito importante, pois no combo box, sempre é impresso o retorno do toString desse objeto específico, como foi definido nesse caso aqui
    @Override
    public String toString() {
        return descricao;
    }
}
