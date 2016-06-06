package io.loli.box.entity;

import javax.persistence.*;

/**
 * @author choco
 */
@Entity
public class IdSeq {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
