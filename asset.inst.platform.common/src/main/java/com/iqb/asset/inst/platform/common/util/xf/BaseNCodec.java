package com.iqb.asset.inst.platform.common.util.xf;

import java.io.UnsupportedEncodingException;

@SuppressWarnings("unused")
public abstract class BaseNCodec
{
  public static final int MIME_CHUNK_SIZE = 76;
  public static final int PEM_CHUNK_SIZE = 64;
  private static final int DEFAULT_BUFFER_RESIZE_FACTOR = 2;
  private static final int DEFAULT_BUFFER_SIZE = 8192;
  protected static final int MASK_8BITS = 255;
  protected static final byte PAD_DEFAULT = 61;
  protected final byte PAD = 61;
  private final int unencodedBlockSize;
  private final int encodedBlockSize;
  protected final int lineLength;
  private final int chunkSeparatorLength;
  protected byte[] buffer;
  protected int pos;
  private int readPos;
  protected boolean eof;
  protected int currentLinePos;
  protected int modulus;

  protected BaseNCodec(int unencodedBlockSize, int encodedBlockSize, int lineLength, int chunkSeparatorLength)
  {
    this.unencodedBlockSize = unencodedBlockSize;
    this.encodedBlockSize = encodedBlockSize;
    this.lineLength = ((lineLength > 0) && (chunkSeparatorLength > 0) ? lineLength / encodedBlockSize * encodedBlockSize : 0);
    this.chunkSeparatorLength = chunkSeparatorLength;
  }

  boolean hasData()
  {
    return this.buffer != null;
  }

  int available()
  {
    return this.buffer != null ? this.pos - this.readPos : 0;
  }

  protected int getDefaultBufferSize()
  {
    return 8192;
  }

  private void resizeBuffer()
  {
    if (this.buffer == null) {
      this.buffer = new byte[getDefaultBufferSize()];
      this.pos = 0;
      this.readPos = 0;
    } else {
      byte[] b = new byte[this.buffer.length * 2];
      System.arraycopy(this.buffer, 0, b, 0, this.buffer.length);
      this.buffer = b;
    }
  }

  protected void ensureBufferSize(int size)
  {
    if ((this.buffer == null) || (this.buffer.length < this.pos + size))
      resizeBuffer();
  }

  int readResults(byte[] b, int bPos, int bAvail)
  {
    if (this.buffer != null) {
      int len = Math.min(available(), bAvail);
      System.arraycopy(this.buffer, this.readPos, b, bPos, len);
      this.readPos += len;
      if (this.readPos >= this.pos) {
        this.buffer = null;
      }
      return len;
    }
    return this.eof ? -1 : 0;
  }

  protected static boolean isWhiteSpace(byte byteToCheck)
  {
    switch (byteToCheck) {
    case 9:
    case 10:
    case 13:
    case 32:
      return true;
    }
    return false;
  }

  private void reset()
  {
    this.buffer = null;
    this.pos = 0;
    this.readPos = 0;
    this.currentLinePos = 0;
    this.modulus = 0;
    this.eof = false;
  }

  public Object encode(Object pObject)
    throws CoderException
  {
    if (!(pObject instanceof byte[])) {
      throw new CoderException("Parameter supplied to Base-N encode is not a byte[]");
    }
    return encode((byte[])(byte[])pObject);
  }

  public String encodeToString(byte[] pArray)
  {
    try
    {
      return new String(encode(pArray), "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return null;
  }

  public Object decode(Object pObject)
    throws CoderException
  {
    if ((pObject instanceof byte[]))
      return decode((byte[])(byte[])pObject);
    if ((pObject instanceof String)) {
      return decode((String)pObject);
    }
    throw new CoderException("Parameter supplied to Base-N decode is not a byte[] or a String");
  }

  public byte[] decode(String pArray)
  {
    try
    {
      return decode(pArray.getBytes("UTF-8"));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return null;
  }

  public byte[] decode(byte[] pArray)
  {
    reset();
    if ((pArray == null) || (pArray.length == 0)) {
      return pArray;
    }
    decode(pArray, 0, pArray.length);
    decode(pArray, 0, -1);
    byte[] result = new byte[this.pos];
    readResults(result, 0, result.length);
    return result;
  }

  public byte[] encode(byte[] pArray)
  {
    reset();
    if ((pArray == null) || (pArray.length == 0)) {
      return pArray;
    }
    encode(pArray, 0, pArray.length);
    encode(pArray, 0, -1);
    byte[] buf = new byte[this.pos - this.readPos];
    readResults(buf, 0, buf.length);
    return buf;
  }

  public String encodeAsString(byte[] pArray)
  {
    try
    {
      return new String(encode(pArray), "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return null;
  }

  abstract void encode(byte[] paramArrayOfByte, int paramInt1, int paramInt2);

  abstract void decode(byte[] paramArrayOfByte, int paramInt1, int paramInt2);

  protected abstract boolean isInAlphabet(byte paramByte);

  public boolean isInAlphabet(byte[] arrayOctet, boolean allowWSPad)
  {
    for (int i = 0; i < arrayOctet.length; i++) {
      if ((!isInAlphabet(arrayOctet[i])) && ((!allowWSPad) || ((arrayOctet[i] != 61) && (!isWhiteSpace(arrayOctet[i])))))
      {
        return false;
      }
    }
    return true;
  }

  public boolean isInAlphabet(String basen)
  {
    try
    {
      return isInAlphabet(decode(basen.getBytes("UTF-8")), true);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return true;
  }

  protected boolean containsAlphabetOrPad(byte[] arrayOctet)
  {
    if (arrayOctet == null) {
      return false;
    }
    for (int i = 0; i < arrayOctet.length; i++) {
      if ((61 == arrayOctet[i]) || (isInAlphabet(arrayOctet[i]))) {
        return true;
      }
    }
    return false;
  }

  public long getEncodedLength(byte[] pArray)
  {
    long len = (pArray.length + this.unencodedBlockSize - 1) / this.unencodedBlockSize * this.encodedBlockSize;
    if (this.lineLength > 0)
    {
      len += (len + this.lineLength - 1L) / this.lineLength * this.chunkSeparatorLength;
    }
    return len;
  }
}
