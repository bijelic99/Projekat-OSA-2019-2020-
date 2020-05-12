package com.ftn.osa.projekat_osa.android_dto;

import com.ftn.osa.projekat_osa.model.JpaEntity;

import java.io.Serializable;

public abstract class DtoObject<T extends JpaEntity> implements Serializable {

    public DtoObject(){

    }
    public DtoObject(T entity) throws Exception {
        throw new Exception("Not implemented");
    };

    public abstract T getJpaEntity();
}
