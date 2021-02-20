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

import java.io.ByteArrayInputStream;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A {@code PUSH} message: tells a {@link sep.tinee.server.Server} instance to
 * append a list of tines from a user to each (possibly new) tag in the set
 * of tags.
 * <p>
 * Instances of this class are immutable.
 *
 * @author rhu1 {@literal <r.z.h.hu@herts.ac.uk>}
 */
public final class Push implements Message {

  /** 
   * Pushing a Tine with this exact message line causes the server to close the
   * ticket, if the user is the ticket's creator (initial author)
   */
  public static final String CLOSE_LINE = "##CLOSE##";

  private static final long serialVersionUID = 1L;  // Generated

  /**
   * The header code.
   */
  public static final String HEADER = "PUSH";

  /**
   * The name of the author of these tines.
   *
   * @see sep.tinee.net.message.Message#isValidUserId(String)
   */
  public final String user;

  /**
   * The non-empty set of target tags. The set is not modifiable.
   *
   * @see sep.tinee.net.message.Message#isValidTag(String)
   */
  public final Set<String> tags;

  /**
   * The non-empty list of tine message lines. The list is not modifiable.
   *
   * @see sep.tinee.net.message.Message#isValidLine(String)
   */
  public final List<String> lines;

  /**
   * Create a {@code PUSH} message for a single tag. This is equivalent to
   * {@linkplain #Push(String, LinkedHashSet, List)} where the set is a
   * singleton containing {@code tag}.
   *
   * @param user  A user name
   * @param tag The target tag
   * @param lines A non-empty list of tine message lines to append to each tag
   *              non-empty
   * @throws IllegalArgumentException If tags or lines is empty, or any of
                                  the user name, tags or message lines are
                                  invalid
   *
   * @see sep.tinee.net.message.Message#isValidUserId(String)
   * @see sep.tinee.net.message.Message#isValidTag(String)
   * @see sep.tinee.net.message.Message#isValidLine(String)
   */
  public Push(final String user, final String tag,
      final List<String> lines) {
    //this(user, Stream.of(tag).collect(Collectors.toCollection(LinkedHashSet::new)), lines);
    this(user, new LinkedHashSet<>(Stream.of(tag).collect(Collectors.toSet())),
        lines);
  }

  /**
   * Create a {@code PUSH} message.
   *
   * @param user   A user name
   * @param tags A non-empty set of target tags
   * @param lines  A non-empty list of tine message lines to append to each tag
   *               non-empty
   * @throws IllegalArgumentException If tags or lines is empty, or any of
                                  the user name, tags or message lines are
                                  invalid
   *
   * @see sep.tinee.net.message.Message#isValidUserId(String)
   * @see sep.tinee.net.message.Message#isValidTag(String)
   * @see sep.tinee.net.message.Message#isValidLine(String)
   */
  public Push(final String user, final LinkedHashSet<String> tags,
      final List<String> lines) {
    if (tags.isEmpty()) {
      throw new IllegalArgumentException("Tags set should be non-empty.");
    }
    if (lines.isEmpty()) {
      throw new IllegalArgumentException("Tines list should be non-empty.");
    }
    Message.isValidUserId(user);
    tags.forEach(Message::isValidTag);
    lines.forEach(Message::isValidLine);
    this.user = user;
    this.tags = Collections.unmodifiableSet(new LinkedHashSet<>(tags));
    this.lines = Collections.unmodifiableList(new LinkedList<>(lines));
  }

  @Override
  public String getHeader() {
    return Push.HEADER;
  }

  @Override
  public String toString() {
    return Push.HEADER
        + this.tags.stream().map(x -> " #" + x)
            .collect(Collectors.joining())
        + this.lines.stream().map(x -> " @" + this.user + " " + x)
            .collect(Collectors.joining());
  }
}
