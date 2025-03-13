package model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "emprestimos")
public class EmprestimoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEmprestimo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idLivro", nullable = false)
    private LivrosModel livro;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idUsuario", nullable = false)
    private UsuariosModel usuario;

    @Temporal(TemporalType.DATE)
    private Date dataEmprestimo;

    @Temporal(TemporalType.DATE)
    private Date dataPrevistaDevolucao;

    @Temporal(TemporalType.DATE)
    private Date dataDevolvida;

    public int getIdEmprestimo() {
        return idEmprestimo;
    }

    public void setIdEmprestimo(int idEmprestimo) {
        this.idEmprestimo = idEmprestimo;
    }

    public LivrosModel getLivro() {
        return livro;
    }

    public void setLivro(LivrosModel livro) {
        this.livro = livro;
    }

    public UsuariosModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuariosModel usuario) {
        this.usuario = usuario;
    }

    public Date getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(Date dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public Date getDataPrevistaDevolucao() {
        return dataPrevistaDevolucao;
    }

    public void setDataPrevistaDevolucao(Date dataPrevistaDevolucao) {
        this.dataPrevistaDevolucao = dataPrevistaDevolucao;
    }

    public Date getDataDevolvida() {
        return dataDevolvida;
    }

    public void setDataDevolvida(Date dataDevolvida) {
        this.dataDevolvida = dataDevolvida;
    }
}