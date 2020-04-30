package com.ftn.osa.projekat_osa.model;

import javax.persistence.*;

@Entity
@Table(name = "RULES")
public class Rule extends Identifiable {

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "_condition")
    private Condition condition;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "operation")
    private Operation operation;

    @OneToOne(fetch = FetchType.LAZY)
    private Folder destinationFolder;

    @Column(name = "_value")
    private String value;

    public Rule(){
        this(null, null, null, null);
    }
    public Rule(Condition condition, Operation operation, Folder destinationFolder, String value) {
        this.condition = condition;
        this.operation = operation;
        this.destinationFolder = destinationFolder;
        this.value = value;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Folder getDestinationFolder() {
        return destinationFolder;
    }

    public void setDestinationFolder(Folder destinationFolder) {
        this.destinationFolder = destinationFolder;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
