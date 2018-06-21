/*
 * Copyright (c) 2006, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package org.ltc.iltalk.lang.model;

import org.ltc.iltalk.lang.model.element.Element;

import javax.lang.model.element.Name;

/**
 * An immutable sequence of characters.  When created by the same
 * implementation, objects implementing this interface must obey the
 * general {@linkplain Object#equals equals contract} when compared
 * with each other.  Therefore, {@code Name} objects from the same
 * implementation are usable in collections while {@code Name}s from
 * different implementations may not work properly in collections.
 *
 * <p>An empty {@code Name} has a length of zero.
 *
 * <p>In the context of {@linkplain
 * javax.annotation.processing.ProcessingEnvironment annotation
 * processing}, the guarantees for "the same" implementation must
 * include contexts where the {@linkplain javax.annotation.processing
 * API mediated} side effects of {@linkplain
 * javax.annotation.processing.Processor processors} could be visible
 * to each other, including successive annotation processing
 * {@linkplain javax.annotation.processing.RoundEnvironment rounds}.
 *
 * @author Joseph D. Darcy
 * @author Scott Seligman
 * @author Peter von der Ah&eacute;
 * @since 1.6
 */
public interface IName extends Name, CharSequence, ICharIterator {


    /**
     * Returns {@code true} if the argument represents the same
     * name as {@code this}, and {@code false} otherwise.
     *
     * <p>Note that the identity of a {@code Name} is a function both
     * of its content in terms of a sequence of characters as well as
     * the implementation which created it.
     *
     * @param obj  the object to be compared with this element
     * @return {@code true} if the specified object represents the same
     *          name as this
     * @see Element#equals
     */
    boolean equals(Object obj);

    /**
     * Obeys the general contract of {@link Object#hashCode Object.hashCode}.
     *
     * @see #equals
     */
    int hashCode();

    /**
     * Compares this name to the specified {@code CharSequence}. The result
     * is {@code true} if and only if this name represents the same sequence
     * of {@code char} values as the specified sequence.
     *
     * @return {@code true} if this name represents the same sequence
     * of {@code char} values as the specified sequence, {@code false}
     * otherwise
     *
     * @param cs The sequence to compare this name against
     * @see String#contentEquals(CharSequence)
     */
    boolean contentEquals(CharSequence cs);

    /**
     * Returns the length of this character sequence.  The length is the number
     * of 16-bit <code>char</code>s in the sequence.
     *
     * @return the number of <code>char</code>s in this sequence
     */
    @Override
    default int length() {
        return 0;
    }

    /**
     * Returns the <code>char</code> value at the specified index.  An index ranges from zero
     * to <tt>length() - 1</tt>.  The first <code>char</code> value of the sequence is at
     * index zero, the next at index one, and so on, as for array
     * indexing.
     *
     * <p>If the <code>char</code> value specified by the index is a
     * <a href="{@docRoot}/java/lang/Character.html#unicode">surrogate</a>, the surrogate
     * value is returned.
     *
     * @param index the index of the <code>char</code> value to be returned
     * @return the specified <code>char</code> value
     * @throws IndexOutOfBoundsException if the <tt>index</tt> argument is negative or not less than
     *                                   <tt>length()</tt>
     */
    @Override
    default char charAt(int index) {
        return 0;
    }

    /**
     * Returns a <code>CharSequence</code> that is a subsequence of this sequence.
     * The subsequence starts with the <code>char</code> value at the specified index and
     * ends with the <code>char</code> value at index <tt>end - 1</tt>.  The length
     * (in <code>char</code>s) of the
     * returned sequence is <tt>end - start</tt>, so if <tt>start == end</tt>
     * then an empty sequence is returned.
     *
     * @param start the start index, inclusive
     * @param end   the end index, exclusive
     * @return the specified subsequence
     * @throws IndexOutOfBoundsException if <tt>start</tt> or <tt>end</tt> are negative,
     *                                   if <tt>end</tt> is greater than <tt>length()</tt>,
     *                                   or if <tt>start</tt> is greater than <tt>end</tt>
     */
    @Override
    default CharSequence subSequence(int start, int end) {
        return null;
    }

    /**
     * Returns a string containing the characters in this sequence in the same
     * order as this sequence.  The length of the string will be the length of
     * this sequence.
     *
     * @return a string consisting of exactly this sequence of characters
     */


    /**
     * Sets the position to getBeginIndex() and returns the character at that
     * position.
     *
     * @return the first character in the text, or DONE if the text is empty
     * @see #getBeginIndex()
     */

    /**
     * Sets the position to getEndIndex()-1 (getEndIndex() if the text is empty)
     * and returns the character at that position.
     *
     * @return the last character in the text, or DONE if the text is empty
     * @see #getEndIndex()
     */
    /**
     * Gets the character at the current position (as returned by getIndex()).
     *
     * @return the character at the current position or DONE if the current
     * position is off the end of the text.
     * @see #getIndex()
     */

    /**
     * Returns {@code true} if the iteration has more elements.
     * (In other words, returns {@code true} if {@link #next} would
     * return an element rather than throwing an exception.)
     *
     * @return {@code true} if the iteration has more elements
     */
    @Override
    default boolean hasNext() {
        return false;
    }

    /**
     * Increments the iterator's index by one and returns the character
     * at the new index.  If the resulting index is greater or equal
     * to getEndIndex(), the current index is reset to getEndIndex() and
     * a value of DONE is returned.
     *
     * @return the character at the new position or DONE if the new
     * position is off the end of the text range.
     */
    @Override
    default Character next() {
        return 0;
    }

    /**
     * Decrements the iterator's index by one and returns the character
     * at the new index. If the current index is getBeginIndex(), the index
     * remains at getBeginIndex() and a value of DONE is returned.
     *
     * @return the character at the new position or DONE if the current
     * position is equal to getBeginIndex().
     */
    @Override
    default char previous() {
        return 0;
    }

    /**
     * Sets the position to the specified position in the text and returns that
     * character.
     *
     * @param position the position within the text.  Valid values range from
     *                 getBeginIndex() to getEndIndex().  An IllegalArgumentException is thrown
     *                 if an invalid value is supplied.
     * @return the character at the specified position or DONE if the specified position is equal to getEndIndex()
     */
    @Override
    default char setIndex(int position) {
        return 0;
    }

    /**
     * Returns the start index of the text.
     *
     * @return the index at which the text begins.
     */
    @Override
    default int getBeginIndex() {
        return 0;
    }

    /**
     * Returns the end index of the text.  This index is the index of the first
     * character following the end of the text.
     *
     * @return the index after the last character in the text
     */
    @Override
    default int getEndIndex() {
        return getBeginIndex()+length();
    }

    /**
     * Returns the current index.
     *
     * @return the current index.
     */
    @Override
    default int getIndex() {
        return 0;
    }

    /**
     * Create a copy of this iterator
     *
     * @return A copy of this
     */
}
