package org.gudartem.aars.db.model;

import java.util.Set;

public interface HasNullFields {
    Set<String> getNullFields();

    void setNullFields(Set<String> nullFields);
}
