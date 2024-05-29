package org.fiap.entities;

import java.io.Serializable;

public abstract class _BaseEntity implements Serializable {
    private int id;

    public _BaseEntity() {
    }

    public _BaseEntity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "_BaseEntity{" +
                "id=" + id +
                '}';
    }
}
