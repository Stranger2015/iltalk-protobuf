
package org.ltc.iltalk.core;


import java.util.Objects;

/**
 *
 */
public class DbEntity implements Identifiable<Integer> {

    /**
     *
     */
    private static int idCount = 0;
    private final Integer id;

    /**
     *
     */

    public DbEntity() {
        id = ++idCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DbEntity)) return false;
        DbEntity dbEntity = (DbEntity) o;
        return Objects.equals(getId(), dbEntity.getId());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId());
    }

    @Override
    public Integer getId () {
        return id;
    }

}