/*
 * This file is part of the base framework for the 6COM1038 Software Engineering
 * Practice coursework assessments -- a command line client application for a
 * text-based Ticket System.
 *
 * <i>Warning:</i> you are advised <b>not</b> to modify this file, nor any of
 * the base framework, i.e., the packages {@link sep.tinee.server},
 * {@link sep.tinee.server}, {@link sep.tinee.server} and
 * {@link sep.tinee.server}. Finally, you submit only your client code, which
 * will be tested against the base framework as provided.
 *
 * If you do wish to (temporarily) modify the base framework for debugging or
 * your own interest, please bear in mind the above point.
 */
package sep.tinee.net.message;

/**
 * A {@code READ} message: tells a {@link sep.tinee.server.Server} instance to
 return the currently recorded (sub)list of tines for a given tag, as
 listed from the specified index (inclusive, starting from index 0).
 * <p>
 * After sending this message, the client should expected receive a
 * {@link sep.tinee.net.message.ReadReply}.
 * <p>
 * Instances of this class are immutable.
 *
 * @author rhu1 {@literal <r.z.h.hu@herts.ac.uk>}
 */
public final class ReadRequest implements Message {

  private static final long serialVersionUID = 1L;  // Default

  /**
   * The header code.
   */
  public static final String HEADER = "READ";

  /**
   * The specified tag.
   */
  public final String tag;

  /**
   * The specified index.
   */
  public final int index;  // Invariant: index >= 0 (starts at 0)

  /**
   * Create a {@code READ} message with implicit index 0. This is equivalent to
   * {@linkplain #ReadRequest(String, int)}{@code (tag, 0)}.
   *
   * @param tag The target tag
   * @throws IllegalArgumentException If the tag is invalid or the index is
                                  negative
   *
   * @see sep.tinee.net.message.Message#isValidTag(String)
   */
  public ReadRequest(final String tag) {
    this(tag, 0);
  }

  /**
   * Create a {@code READ} message. Note: it does not matter whether or not the
 tag is explicitly recorded by the server.
   *
   * @param tag The target tag
   * @param index The starting index (inclusive) of the list of tines to return
   * @throws IllegalArgumentException If the tag is invalid or the index is
                                  negative
   *
   * @see sep.tinee.net.message.Message#isValidTag(String)
   */
  public ReadRequest(final String tag, final int index) {
    Message.isValidTag(tag);
    if (index < 0) {
      throw new IllegalArgumentException("Invalid index value: " + index);
    }
    this.tag = tag;
    this.index = index;
  }

  @Override
  public String getHeader() {
    return ReadRequest.HEADER;
  }

  @Override
  public String toString() {
    return ReadRequest.HEADER + " " + this.index + " " + this.tag;

  }
}
