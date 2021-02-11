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
 * A {@code SHOW} message: tells a {@link sep.tinee.server.Server} instance
 * to return the currently recorded set of ticket tags and creators (user
 * names). This set may be empty.
 * <p>
 * After sending this message, the client should expected receive a
 * {@link sep.tinee.net.message.ShowReply}.
 * <p>
 * Instances of this class are immutable.
 *
 * @author rhu1 {@literal <r.z.h.hu@herts.ac.uk>}
 */
public final class ShowRequest implements Message {

  private static final long serialVersionUID = 1L;  // Default

  /**
   * The header code.
   */
  public static final String HEADER = "SHOW";

  /**
   * Create a new {@code SHOW} message.
   */
  public ShowRequest() {
    // Empty constructor
  }

  @Override
  public String getHeader() {
    return ShowRequest.HEADER;
  }

  @Override
  public String toString() {
    return ShowRequest.HEADER;

  }
}
