package org.ltc.iltalk.lang.model.type;

public interface TypeModel {

    TypeModelKind getKind();

    boolean equals(Object var1);

    int hashCode();

    String toString();

    <R, P> R accept(TypeModelVisitor<R, P> var1, P var2);

}
