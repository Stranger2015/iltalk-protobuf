
package org.ltc.iltalk.core.protobuf.pro;


/**
 *
 */
public class DbEntity implements Identifiable <Integer> {

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
    public Integer getId () {
        return id;
    }

}