package model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tarefas")
public class TarefasModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    private String Descricao;
    private int Status;
    private LocalDate DataVencimento;

    public TarefasModel() {}

    public int getId() {return Id;}
    public void setId(int id) {Id = id;}

    public String getDescricao() {
        return Descricao;
    }
    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    public int getStatus() {
        return Status;
    }
    public void setStatus(int status) {
        Status = status;
    }

    public LocalDate getDataVencimento() {
        return DataVencimento;
    }
    public void setDataVencimento(LocalDate dataVencimento) {
        DataVencimento = dataVencimento;
    }
}
