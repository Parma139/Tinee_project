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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * A {@code SHOW-REPLY} message: the server reply to a
 * {@linkplain sep.tinee.net.message.ShowRequest SHOW} request: the set of
 * tags explicitly recorded by the server. The set may be empty.
 *
 * @author rhu1 {@literal <r.z.h.hu@herts.ac.uk>}
 */
public final class ShowReply implements Message {

  private static final long serialVersionUID = 1L;  // Default

  /**
   * The header code.
   */
  public static final String HEADER = "SHOW-REPLY";

  /**
   * A set of the ticket tags explicitly recorded by the server. Possibly empty.
   * Not modifiable.
   */
  public final Map<String, String> tags;

  /**
   * Create a new {@code SHOW-REPLY} message. (Used by a server.)
   *
   * @param tags The set of requested tags
   * @throws IllegalArgumentException If any tag is invalid
   *
   * @see sep.tinee.net.message.Message#isValidTag(String)
   */
  public ShowReply(final LinkedHashMap<String, String> tags) {
    for (Entry<String, String> e : tags.entrySet()) {
      Message.isValidTag(e.getKey());
      Message.isValidUserId(e.getValue());
    }
    this.tags = Collections.unmodifiableMap(new LinkedHashMap<>(tags));
  }

  @Override
  public String getHeader() {
    return ShowReply.HEADER;
  }

  @Override
  public String toString() {
    return ShowReply.HEADER + " "
        + this.tags.entrySet().stream()
            .map(x -> "(#" + x.getKey() + ", " + x.getValue() + ")")
            .collect(Collectors.joining(" "));
  }
}
