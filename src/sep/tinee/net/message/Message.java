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

import java.io.Serializable;

/**
 * A base interface for messages that can be communicated using
 * {@link sep.tinee.net.channel.ClientChannel} with an instance of
 * {@link sep.tinee.server.Server}.
 *
 * @author rhu1 {@literal <r.z.h.hu@herts.ac.uk>}
 */
public interface Message extends Serializable {

  /**
   * Returns the <i>unique</i> header code for this message. The code should be
   * a non-empty word composed of digits, letters, underscores and hyphens.
   *
   * @return The header code
   */
  String getHeader();

  /* Name well-formedness checks */

  /**
   * Validate a user name.
   *
   * @param name The name to validate
   */
  static void isValidUserId(String name) {
    if (name.isEmpty() || name.length() > 8) {
      throw new IllegalArgumentException(
          "User name should be non-empty and not longer than 8 characters.");
    }
    if (!name.matches("^[a-zA-Z0-9]*$")) {
      throw new IllegalArgumentException(
          "User name should be alphanumeric, not: " + name);
    }
  }

  /**
   * Validate a ticket tag name.
   *
   * @param name The name to validate
   */
  static void isValidTag(String name) {
    if (name.isEmpty() || name.length() > 8) {
      throw new IllegalArgumentException(
          "Tag name should be non-empty and not longer than 8 characters.");
    }
    if (!name.matches(
        "^[a-zA-Z0-9]*$")) {
      throw new IllegalArgumentException(
          "Toag should be alphanumeric, not: " + name);
    }
  }

  /**
   * Validate a tine msg line.
   *
   * @param msg The line to validate
   */
  static void isValidLine(String msg) {
    if (msg.isEmpty() || msg.length() > 48) {
      throw new IllegalArgumentException(
          "Tine message line should be non-empty and not longer than 48"
          + "characters.");
    }
    if (msg.contains(System.getProperty("line.separator"))) {
      throw new IllegalArgumentException(
          "Tine message line should be a single line, not: " + msg);
    }
  }
}
