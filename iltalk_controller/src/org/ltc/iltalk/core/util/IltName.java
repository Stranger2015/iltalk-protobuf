package org.ltc.iltalk.core.util;


import javax.lang.model.element.Na           me;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 *
 */
    public class IltName extends ByteString implements Name
    {
        private ByteString delegate;
        private String delegate16;
        private int length;
        private int size;

    public IltName ( String s, ByteString delegate ) {
        super(s);
        this.delegate = delegate;

    }

        public IltName ( String propValue ) {

        }

        /**
         * Gets the byte at the given index. This method should be used only for
         * random access to individual bytes. To access bytes sequentially, use the
         * {@link ByteIterator} returned by {@link #iterator()}, and call {@link
         * #substring(int, int)} first if necessary.
         *
         * @param index index of byte
         * @return the value
         * @throws IndexOutOfBoundsException {@code index < 0 or index >= size}
         */
        @Override
        public byte byteAt ( int index ) {
            return delegate.byteAt( index );
        }

        /**
         * Gets the number of bytes.
         *
         * @return size in bytes
         */
        @Override
        public int size () {
            return delegate.size();
        }

        /**
         * Return the substring from {@code beginIndex}, inclusive, to {@code
         * endIndex}, exclusive.
         *
         * @param beginIndex start at this index
         * @param endIndex   the last character is the one before this index
         * @return substring sharing underlying data
         * @throws IndexOutOfBoundsException if {@code beginIndex < 0},
         *                                   {@code endIndex > size()}, or {@code beginIndex > endIndex}.
         */
        @Override
        public ByteString substring ( int beginIndex, int endIndex ) {
            return delegate.substring( beginIndex, endIndex );
        }

        /**
         * Internal (package private) implementation of
         * {@link #copyTo(byte[], int, int, int)}.
         * It assumes that all error checking has already been performed and that
         * {@code numberToCopy > 0}.
         *
         * @param target
         * @param sourceOffset
         * @param targetOffset
         * @param numberToCopy
         */
//        @Override
//        protected void copyToInternal ( byte[] target, int sourceOffset, int targetOffset, int numberToCopy ) {
//            delegate.copy(  )
//        }

        /**
         * Copies bytes into a ByteBuffer.
         *
         * @param target ByteBuffer to copy into.
         * @throws ReadOnlyBufferException if the {@code target} is read-only
         * @throws BufferOverflowException if the {@code target}'s
         *                                 remaining() space is not large enough to hold the data.
         */
        @Override
        public void copyTo ( ByteBuffer target ) {

        }

        /**
         * Writes a copy of the contents of this byte string to the specified output stream argument.
         *
         * @param out the output stream to which to write the data.
         * @throws IOException if an I/O error occurs.
         */
        @Override
        public void writeTo ( OutputStream out ) throws IOException {
            delegate.writeTo( out );
        }

        /**
         * Constructs a read-only {@code java.nio.ByteBuffer} whose content
         * is equal to the contents of this byte string.
         * The result uses the same backing array as the byte string, if possible.
         *
         * @return wrapped bytes
         */
        @Override
        public ByteBuffer asReadOnlyByteBuffer () {
            return delegate.asReadOnlyByteBuffer();
        }

        /**
         * Constructs a list of read-only {@code java.nio.ByteBuffer} objects
         * such that the concatenation of their contents is equal to the contents
         * of this byte string.  The result uses the same backing arrays as the
         * byte string.
         * <p>
         * By returning a list, implementations of this method may be able to avoid
         * copying even when there are multiple backing arrays.
         *
         * @return a list of wrapped bytes
         */
        @Override
        public List <ByteBuffer> asReadOnlyByteBufferList () {
            return delegate.asReadOnlyByteBufferList();
        }

        /**
         * Constructs a new {@code String} by decoding the bytes using the
         * specified charset.
         *
         * @param charset encode using this charset
         * @return new string
         */
        @Override
        protected String toStringInternal ( Charset charset ) {
            return delegate.toString( charset );
        }

        /**
         * Tells whether this {@code ByteString} represents a well-formed UTF-8
         * byte sequence, such that the original bytes can be converted to a
         * String object and then round tripped back to bytes without loss.
         * <p>
         * <p>More precisely, returns {@code true} whenever: <pre> {@code
         * Arrays.equals(byteString.toByteArray(),
         *     new String(byteString.toByteArray(), "UTF-8").getBytes("UTF-8"))
         * }</pre>
         * <p>
         * <p>This method returns {@code false} for "overlong" byte sequences,
         * as well as for 3-byte sequences that would map to a surrogate
         * character, in accordance with the restricted definition of UTF-8
         * introduced in Unicode 3.1.  Note that the UTF-8 decoder included in
         * Oracle's JDK has been modified to also reject "overlong" byte
         * sequences, but (as of 2011) still accepts 3-byte surrogate
         * character byte sequences.
         * <p>
         * <p>See the Unicode Standard,<br>
         * Table 3-6. <em>UTF-8 Bit Distribution</em>,<br>
         * Table 3-7. <em>Well Formed UTF-8 Byte Sequences</em>.
         *
         * @return whether the bytes in this {@code ByteString} are a
         * well-formed UTF-8 byte sequence
         */
        @Override
        public boolean isValidUtf8 () {
            return delegate.isValidUtf8();
        }
        //        @Override
//        protected int partialIsValidUtf8 ( int state, int offset, int length ) {
//            return delegate.partialisva;i );
//        }

        @Override
        public boolean equals ( Object o ) {
            return delegate.equals( o );
        }

        /**
         * Obeys the general contract of {@link Object#hashCode Object.hashCode}.
         *
         * @see #equals
         */
        public int hashCode () {
            return 0;
        }

        /**
         * Compares this name to the specified {@code CharSequence}. The result
         * is {@code true} if and only if this name represents the same sequence
         * of {@code char} values as the specified sequence.
         *
         * @param cs The sequence to compare this name against
         * @return {@code true} if this name represents the same sequence
         * of {@code char} values as the specified sequence, {@code false}
         * otherwise
         * @see String#contentEquals(CharSequence)
         */
        @Override
        public boolean contentEquals ( CharSequence cs ) {
            return equals( cs );
        }

        /**
         * Creates an {@code InputStream} which can be used to read the bytes.
         * <p>
         * The {@link InputStream} returned by this method is guaranteed to be
         * completely non-blocking.  The method {@link InputStream#available()}
         * returns the number of bytes remaining in the stream. The methods
         * {@link InputStream#read(byte[])}, {@link InputStream#read(byte[], int, int)}
         * and {@link InputStream#skip(long)} will read/skip as many bytes as are
         * available.  The method {@link InputStream#markSupported()} returns
         * {@code true}.
         * <p>
         * The methods in the returned {@link InputStream} might <b>not</b> be
         * thread safe.
         *
         * @return an input stream that returns the bytes of this byte string.
         */
        @Override
        public InputStream newInput () {
            return delegate.newInput();
        }

        /**
         * Creates a {@link CodedInputStream} which can be used to read the bytes.
         * Using this is often more efficient than creating a {@link CodedInputStream}
         * that wraps the result of {@link #newInput()}.
         *
         * @return stream based on wrapped data
         */
        @Override
        public CodedInputStream newCodedInput () {
            return  delegate.newCodedInput();
        }

        /**
         * Return the depth of the tree representing this {@code ByteString}, if any,
         * whose root is this node. If this is a leaf node, return 0.
         *
         * @return tree depth or zero
         */
        @Override
        protected int getTreeDepth () {
            return delegate.getTreeDepth();
        }

        /**
         * Return {@code true} if this ByteString is literal (a leaf node) or a
         * flat-enough tree in the sense of {@link RopeByteString}.
         *
         * @return true if the tree is flat enough
         */
        @Override
        protected boolean isBalanced () {
            return false;
        }

        /**
         * Compute the hash across the value bytes starting with the given hash, and
         * return the result.  This is used to compute the hash across strings
         * represented as a set of pieces by allowing the hash computation to be
         * continued from piece to piece.
         *
         * @param h      starting hash value
         * @param offset offset into this value to start looking at data values
         * @param length number of data values to include in the hash computation
         * @return ending hash value
         */
        @Override
        protected int partialHash ( int h, int offset, int length ) {
            return 0;
        }

        /**
         * Returns the length of this character sequence.  The length is the number
         * of 16-bit <code>char</code>s in the sequence.
         *
         * @return the number of <code>char</code>s in this sequence
         */
        @Override
        public int length () {
            return delegate.length();
        }

        /**
         * Returns the <code>char</code> value at the specified index.  An index ranges from zero
         * to <tt>length() - 1</tt>.  The first <code>char</code> value of the sequence is at
         * index zero, the next at index one, and so on, as for array
         * indexing.
         * <p>
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
        public char charAt ( int index ) {
            return delegate16.charAt(index);
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
        public CharSequence subSequence ( int start, int end ) {
            return (CharSequence) delegate.substring( start, end );
        }

        /**
         * Returns a string containing the characters in this sequence in the same
         * order as this sequence.  The length of the string will be the length of
         * this sequence.
         *
         * @return a string consisting of exactly this sequence of characters
         */
        public String toString () {
            return null;
        }
    }
+