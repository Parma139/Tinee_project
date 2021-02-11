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

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A {@code READ-REPLY} message: the server reply to a
 * {@linkplain sep.tinee.net.message.ReadRequest READ} request: the list of
 * lines recorded by the server for the specified tag, listed from the specified
 * index.
 * <p>
 * Instances of this class are immutable.
 *
 * @author rhu1 {@literal <r.z.h.hu@herts.ac.uk>}
 */
public final class ReadReply implements Message {

  private static final long serialVersionUID = 1L;  // Default

  /**
   * The header code.
   */
  public static final String HEADER = "READ-REPLY";

  /**
   * A list naming the users who authored the requested tines. The size of this
   * list and the order of the items contained corresponds to that of {@link
   * #lines}. It may be empty.
   */
  public final List<String> users;

  /**
   * A list of the message lines of the requested tines. The size of this list and
   * the order of the items contained corresponds to that of {@link #users}. It
   * may be empty.
   */
  public final List<String> lines;

  /**
   * Create a new {@code READ-REPLY} message. (Used by a server.)
   *
   * @param users The list of user names of the tine authors
   * @param lines The list of requested tine message lines
   * @throws IllegalArgumentException If users or lines is empty, or any of the
   *                                  users names or message lines are invalid
   *
   * @see sep.tinee.net.message.Message#isValidUserId(String)
   * @see sep.tinee.net.message.Message#isValidLine(String)
   */
  public ReadReply(final List<String> users, final List<String> lines) {
    if (users.size() != lines.size()) {
      throw new IllegalArgumentException(
          "users and lines must be the same size: " + users + " , " + lines);
    }
    users.forEach(Message::isValidUserId);
    lines.forEach(Message::isValidLine);
    this.users = Collections.unmodifiableList(users);
    this.lines = Collections.unmodifiableList(lines);
  }

  @Override
  public String getHeader() {
    return ReadReply.HEADER;
  }

  @Override
  public String toString() {
    final Iterator<String> it = this.lines.iterator();
    return ReadReply.HEADER
        + this.users.stream().map(x -> " @" + x + " " + it.next())
            .collect(Collectors.joining());
  }
}
