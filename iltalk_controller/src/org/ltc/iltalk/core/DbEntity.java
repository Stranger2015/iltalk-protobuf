package org.ltc.iltalk.core;

import org.ltc.iltalk.protobuf.IRegisterable;

import javax.lang.model.element.Name;
import java.util.Objects;

public class DbEntity implements IDbEntity, INameable {

    private static int idCount;
    protected Integer id;
    protected Name name;

    /**
     *
     */
    public DbEntity() {
        id = ++idCount;
    }

    /**
     * @param o
     * @return
     */
    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DbEntity)) {
            return false;
        }
        DbEntity dbEntity = (DbEntity) o;
        return Objects.equals(getId(), dbEntity.getId());
    }

    /**
     * @return
     */
    @Override
    public final int hashCode() {
        return Objects.hash(getId());
    }

    /**
     * @return
     */
    @Override
    public Name getName() {
        return name;
    }

    /**
     * @param name
     * @return
     */
    @Override
    public Name setName(Name name) {
        return this.name = name;
    }

    /**
     * @return
     */
    public Integer register(IRegisterable registerable) {
        return -1; //todo
    }

    /**
     * @return
     */
    @Override
    public Integer getId() {
        return id;
    }
}
