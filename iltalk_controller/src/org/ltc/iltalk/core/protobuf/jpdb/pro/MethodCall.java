package org.ltc.iltalk.core.protobuf.jpdb.pro;

import javax.lang.model.element.*;
import javax.lang.model.type.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.List;
import java.util.Set;

public class MethodCall implements ExecutableType {

//    /*
//    /*
// * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
// * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
// *
// *
// *
// *
// *
// *
// *
// *
// *
// *
// *
// *
// *
// *
// *
// *
// *
// *
// *
// *
// */
//
//package javax.lang.model.element;
//
//import java.util.List;
//import javax.lang.model.type.*;
//
//    /**
//     * Represents a method, constructor, or initializer (static or
//     * instance) of a class or interface, including annotation type
//     * elements.
//     *
//     * @author Joseph D. Darcy
//     * @author Scott Seligman
//     * @author Peter von der Ah&eacute;
//     * @see ExecutableType
//     * @since 1.6
//     */
//    public interface ExecutableElement extends Element, Parameterizable {
//        /**
//         * Returns the formal type parameters of this executable
//         * in declaration order.
//         *
//         * @return the formal type parameters, or an empty list
//         * if there are none
//         */
//        List<? extends TypeParameterElement> getTypeParameters();
//
//        /**
//         * Returns the return type of this executable.
//         * Returns a {@link NoType} with kind {@link TypeKind#VOID VOID}
//         * if this executable is not a method, or is a method that does not
//         * return a value.
//         *
//         * @return the return type of this executable
//         */
//        TypeMirror getReturnType();
//
//        /**
//         * Returns the formal parameters of this executable.
//         * They are returned in declaration order.
//         *
//         * @return the formal parameters,
//         * or an empty list if there are none
//         */
//        List<? extends VariableElement> getParameters();
//
//        /**
//         * Returns the receiver type of this executable,
//         * or {@link javax.lang.model.type.NoType NoType} with
//         * kind {@link javax.lang.model.type.TypeKind#NONE NONE}
//         * if the executable has no receiver type.
//         *
//         * An executable which is an instance method, or a constructor of an
//         * inner class, has a receiver type derived from the {@linkplain
//         * #getEnclosingElement declaring type}.
//         *
//         * An executable which is a static method, or a constructor of a
//         * non-inner class, or an initializer (static or instance), has no
//         * receiver type.
//         *
//         * @return the receiver type of this executable
//         * @since 1.8
//         */
//        TypeMirror getReceiverType();
//
//        /**
//         * Returns {@code true} if this method or constructor accepts a variable
//         * number of arguments and returns {@code false} otherwise.
//         *
//         * @return {@code true} if this method or constructor accepts a variable
//         * number of arguments and {@code false} otherwise
//         */
//        boolean isVarArgs();
//
//        /**
//         * Returns {@code true} if this method is a default method and
//         * returns {@code false} otherwise.
//         *
//         * @return {@code true} if this method is a default method and
//         * {@code false} otherwise
//         * @since 1.8
//         */
//        boolean isDefault();
//
//        /**
//         * Returns the exceptions and other throwables listed in this
//         * method or constructor's {@code throws} clause in declaration
//         * order.
//         *
//         * @return the exceptions and other throwables listed in the
//         * {@code throws} clause, or an empty list if there are none
//         */
//        List<? extends TypeMirror> getThrownTypes();
//
//        /**
//         * Returns the default value if this executable is an annotation
//         * type element.  Returns {@code null} if this method is not an
//         * annotation type element, or if it is an annotation type element
//         * with no default value.
//         *
//         * @return the default value, or {@code null} if none
//         */
//        AnnotationValue getDefaultValue();
//
//        /**
//         * Returns the simple name of a constructor, method, or
//         * initializer.  For a constructor, the name {@code "<init>"} is
//         * returned, for a static initializer, the name {@code "<clinit>"}
//         * is returned, and for an anonymous class or instance
//         * initializer, an empty name is returned.
//         *
//         * @return the simple name of a constructor, method, or
//         * initializer
//         */
//        @Override
//        Name getSimpleName();
//    }

   // machine, thread, process, instance
    //this
    protected Name name;
    protected Element _this;
    protected List<Element> args;
    protected Element returnValue;
    //protected CaughtException
//    protected CallMode callMode;
//    protected SchemaMode schemaMode;


    public MethodCall(Name name){
        this.name = name;
    }

    /**
     * @return
     */
    @Override
    public Name getName(){
        return name;
    }

    /**
     * @param name
     * @return
     */
    @Override
    public Name setName(Name name){
        return this.name = name;
    }

    /**+
     * Returns the type variables declared by the formal type parameters
     * of this executable.
     *
     * @return the type variables declared by the formal type parameters,
     * or an empty list if there are none
     */
    @Override
    public List<? extends TypeVariable> getTypeVariables(){
        return null;
    }
+
    /**
     * Returns the formal type parameters of this executable
     * in declaration order.
     *
     * @return the formal type parameters, or an empty list
     * if there are none
     */
    @Override
    public List<? extends TypeParameterElement> getTypeParameters(){
        return null;
    }

    /**+
     * Returns the return type of this executable.
     * Returns a {@link NoType} with kind {@link TypeKind#VOID VOID}
     * if this executable is not a method, or is a method that does no++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++t
     * return a value.
     *
     * @return the return type of this executable
     */
    @Override
    public TypeMirror getReturnType(){
        return null;
    }

    /**
     * Returns the formal parameters of this executable.
     * They are returned in declaration order.
     *
     * @return the formal parameters,
     * or an empty list if there are none
     */
    @Override
    public List<? extends VariableElement> getParameters(){
        return null;
    }

    /**
     * Returns the types of this executable's formal parameters.
     *
     * @return the types of this executable's formal parameters,
     * or an empty list if there are none
     */
    @Override
    public List<? extends TypeMirror> getParameterTypes(){
        return null;
    }

    /**
     * Returns the receiver type of this executable,
     * or {@link NoType NoType} with
     * kind {@link TypeKind#NONE NONE}
     * if the executable has no receiver type.
     * <p>
     * An executable which is an instance method, or a constructor of an
     * inner class, has a receiver type derived from the {@linkplain
     * ExecutableElement#getEnclosingElement declaring type}.
     * <p>
     * An executable which is a static method, or a constructor of a
     * non-inner class, or an initializer (static or instance), has no
     * receiver type.
     *
     * @return the receiver type of this executable
     * @since 1.8
     */
    @Override
    public TypeMirror getReceiverType(){
        return null;
    }

    /**
     * Returns {@code true} if this method or constructor accepts a variable
     * number of arguments and returns {@code false} otherwise.
     *
     * @return {@code true} if this method or constructor accepts a variable
     * number of arguments and {@code false} otherwise
     */
    @Override
    public boolean isVarArgs(){
        return false;
    }

    /**
     * Returns {@code true} if this method is a default method and
     * returns {@code false} otherwise.
     *
     * @return {@code true} if this method is a default method and
     * {@code false} otherwise
     * @since 1.8
     */
    @Override
    public boolean isDefault(){
        return false;
    }

    /**
     * Returns the exceptions and other throwables listed in this
     * executable's {@code throws} clause.
     *
     * @return the exceptions and other throwables listed in this
     * executable's {@code throws} clause,
     * or an empty list if there are none.
     */
    @Override
    public List<? extends TypeMirror> getThrownTypes(){
        return null;
    }

    /**
     * Returns the default value if this executable is an annotation
     * type element.  Returns {@code null} if this method is not an
     * annotation type element, or if it is an annotation type element
     * with no default value.
     *
     * @return the default value, or {@code null} if none
     */
    @Override
    public AnnotationValue getDefaultValue(){
        return null;
    }

    /**
     * Returns the simple name of a constructor, method, or
     * initializer.  For a constructor, the name {@code "<init>"} is
     * returned, for a static initializer, the name {@code "<clinit>"}
     * is returned, and for an anonymous class or instance
     * initializer, an empty name is returned.
     *
     * @return the simple name of a constructor, method, or
     * initializer
     */
    @Override
    public Name getSimpleName(){
        return null;
    }

    /**
     * Returns the innermost element
     * within which this element is, loosely speaking, enclosed.
     * <ul>
     * <li> If this element is one whose declaration is lexically enclosed
     * immediately within the declaration of another element, that other
     * element is returned.
     * <p>
     * <li> If this is a {@linkplain TypeElement#getEnclosingElement
     * top-level type}, its package is returned.
     * <p>
     * <li> If this is a {@linkplain
     * PackageElement#getEnclosingElement package}, {@code null} is
     * returned.
     * <p>
     * <li> If this is a {@linkplain
     * TypeParameterElement#getEnclosingElement type parameter},
     * {@linkplain TypeParameterElement#getGenericElement the
     * generic element} of the type parameter is returned.
     * <p>
     * <li> If this is a {@linkplain
     * VariableElement#getEnclosingElement method or constructor
     * parameter}, {@linkplain ExecutableElement the executable
     * element} which declares the parameter is returned.
     * <p>
     * </ul>
     *
     * @return the enclosing element, or {@code null} if there is none
     * @see Elements#getPackageOf
     */
    @Override
    public Element getEnclosingElement(){
        return null;
    }

    /**
     * Returns the elements that are, loosely speaking, directly
     * enclosed by this element.
     * <p>
     * A {@linkplain TypeElement#getEnclosedElements class or
     * interface} is considered to enclose the fields, methods,
     * constructors, and member types that it directly declares.
     * <p>
     * A {@linkplain PackageElement#getEnclosedElements package}
     * encloses the top-level classes and interfaces within it, but is
     * not considered to enclose subpackages.
     * <p>
     * Other kinds of elements are not currently considered to enclose
     * any elements; however, that may change as this API or the
     * programming language evolves.
     * <p>
     * <p>Note that elements of certain kinds can be isolated using
     * methods in {@link ElementFilter}.
     *
     * @return the enclosed elements, or an empty list if none
     * @jls 8.8.9 Default Constructor
     * @jls 8.9 Enums
     * @see PackageElement#getEnclosedElements
     * @see TypeElement#getEnclosedElements
     * @see Elements#getAllMembers
     */
    @Override
    public List<? extends Element> getEnclosedElements(){
        return null;
    }

    /**
     * Returns the type defined by this element.
     * <p>
     * <p> A generic element defines a family of types, not just one.
     * If this is a generic element, a <i>prototypical</i> type is
     * returned.  This is the element's invocation on the
     * type variables corresponding to its own formal type parameters.
     * For example,
     * for the generic class element {@code C<N extends NumberTerm>},
     * the parameterized type {@code C<N>} is returned.
     * The {@link Types} utility interface has more general methods
     * for obtaining the full range of types defined by an element.
     *
     * @return the type defined by this element
     * @see Types
     */
    @Override
    public TypeMirror asType(){
        return null;
    }

    /**
     * Returns the {@code kind} of this type.
     *
     * @return the kind of this type
     */
    @Override
    public TypeKind getKind(){
        return null;
    }

    /**
     * Returns the modifiers of this element, excluding annotations.
     * Implicit modifiers, such as the {@code public} and {@code static}
     * modifiers of interface members, are included.
     *
     * @return the modifiers of this element, or an empty set if there are none
     */
    @Override
    public Set<Modifier> getModifiers(){
        return null;
    }

    /**
     * Applies a visitor to this type.
     *
     * @param v the visitor operating on this type
     * @param p additional parameter to the visitor
     * @return a visitor-specified result
     */
    @Override
    public <R, P> R accept(TypeVisitor<R, P> v, P p){
        return null;
    }

    /**
     * Returns the annotations that are <em>directly present</em> on
     * this construct.
     *
     * @return the annotations <em>directly present</em> on this
     * construct; an empty list if there are none
     */
    @Override
    public List<? extends AnnotationMirror> getAnnotationMirrors(){
        return null;
    }

    /**
     * Returns this construct's annotation of the specified type if
     * such an annotation is <em>present</em>, else {@code null}.
     * <p>
     * <p> The annotation returned by this method could contain an element
     * whose value is of type {@code Class}.
     * This value cannot be returned directly:  information necessary to
     * locate and loadNew a class (such as the class loader to use) is
     * not available, and the class might not be loadable at all.
     * Attempting to read a {@code Class} object by invoking the relevant
     * method on the returned annotation
     * will result in a {@link MirroredTypeException},
     * from which the corresponding {@link TypeMirror} may be extracted.
     * Similarly, attempting to read a {@code Class[]}-valued element
     * will result in a {@link MirroredTypesException}.
     * <p>
     * <blockquote>
     * <i>Note:</i> This method is unlike others in this and related
     * interfaces.  It operates on runtime reflective information &mdash;
     * representations of annotation types currently loaded into the
     * VM &mdash; rather than on the representations defined by and used
     * throughout these interfaces.  Consequently, calling methods on
     * the returned annotation object can throw many of the exceptions
     * that can be thrown when calling methods on an annotation object
     * returned by core reflection.  This method is intended for
     * callers that are written to operate on a known, fixed set of
     * annotation types.
     * </blockquote>
     *
     * @param annotationType the {@code Class} object corresponding to
     *                       the annotation type
     * @return this construct's annotation for the specified
     * annotation type if present, else {@code null}
     * @jls 9.6.1 Annotation Type Elements
     * @see #getAnnotationMirrors()
     * @see AnnotatedElement#getAnnotation
     * @see EnumConstantNotPresentException
     * @see AnnotationTypeMismatchException
     * @see IncompleteAnnotationException
     * @see MirroredTypeException
     * @see MirroredTypesException
     */
    @Override
    public <A extends Annotation> A getAnnotation(Class<A> annotationType){
        return null;
    }

    /**
     * Applies a visitor to this element.
     *
     * @param v the visitor operating on this element
     * @param p additional parameter to the visitor
     * @return a visitor-specified result
     */
    @Override
    public <R, P> R accept(ElementVisitor<R, P> v, P p){
        return null;
    }

    /**
     * Returns annotations that are <em>associated</em> with this construct.
     * <p>
     * If there are no annotations associated with this construct, the
     * return value is an array of length 0.
     * <p>
     * The order of annotations which are directly or indirectly
     * present on a construct <i>C</i> is computed as if indirectly present
     * annotations on <i>C</i> are directly present on <i>C</i> in place of their
     * container annotation, in the order in which they appear in the
     * value element of the container annotation.
     * <p>
     * The difference between this method and {@link #getAnnotation(Class)}
     * is that this method detects if its argument is a <em>repeatable
     * annotation type</em>, and if so, attempts to find one or more
     * annotations of that type by "looking through" a container annotation.
     * <p>
     * <p> The annotations returned by this method could contain an element
     * whose value is of type {@code Class}.
     * This value cannot be returned directly:  information necessary to
     * locate and loadNew a class (such as the class loader to use) is
     * not available, and the class might not be loadable at all.
     * Attempting to read a {@code Class} object by invoking the relevant
     * method on the returned annotation
     * will result in a {@link MirroredTypeException},
     * from which the corresponding {@link TypeMirror} may be extracted.
     * Similarly, attempting to read a {@code Class[]}-valued element
     * will result in a {@link MirroredTypesException}.
     * <p>
     * <blockquote>
     * <i>Note:</i> This method is unlike others in this and related
     * interfaces.  It operates on runtime reflective information &mdash;
     * representations of annotation types currently loaded into the
     * VM &mdash; rather than on the representations defined by and used
     * throughout these interfaces.  Consequently, calling methods on
     * the returned annotation object can throw many of the exceptions
     * that can be thrown when calling methods on an annotation object
     * returned by core reflection.  This method is intended for
     * callers that are written to operate on a known, fixed set of
     * annotation types.
     * </blockquote>
     *
     * @param annotationType the {@code Class} object corresponding to
     *                       the annotation type
     * @return this construct's annotations for the specified annotation
     * type if present on this construct, else an empty array
     * @jls 9.6 Annotation types
     * @jls 9.6.1 Annotation Type Elements
     * @see #getAnnotationMirrors()
     * @see #getAnnotation(Class)
     * @see AnnotatedElement#getAnnotationsByType(Class)
     * @see EnumConstantNotPresentException
     * @see AnnotationTypeMismatchException
     * @see IncompleteAnnotationException
     * @see MirroredTypeException
     * @see MirroredTypesException
     */
    @Override
    public <A extends Annotation> A[] getAnnotationsByType(Class<A> annotationType){
        return null;
    }
}
