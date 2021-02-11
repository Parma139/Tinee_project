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
package sep.tinee.server;

import java.util.Objects;

/**
 * Helper utility for {@link sep.tinee.server.Server} - namely, a tine is a
 * pair of strings (username, line).
 * <p>
 * The references of a <tt>Pair</tt> to its elements cannot be changed after it
 * is created. A <tt>Pair</tt> is thus immutable if its elements are immutable.
 *
 * @param <L> The type of the left element
 * @param <R> The type of the right element
 *
 * @author rhu1 {@literal <r.z.h.hu@herts.ac.uk>}
 */
public class Pair<L, R> {

  /**
   * The left element of this pair.
   */
  public final L left;

  /**
   * The right element of this pair.
   */
  public final R right;

  /**
   * Create a new pair.
   *
   * @param left  The left element of the pair
   * @param right The right element of the pair
   */
  public Pair(final L left, final R right) {
    this.left = left;
    this.right = right;
  }

  @Override
  public String toString() {
    return "(" + this.left + ", " + this.right + ")";
  }

  // NetBeans generated
  @Override
  public int hashCode() {
    int hash = 3;
    hash = 89 * hash + Objects.hashCode(this.left);
    hash = 89 * hash + Objects.hashCode(this.right);
    return hash;
  }

  // NetBeans generated
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Pair<?, ?> other = (Pair<?, ?>) obj;
    if (!Objects.equals(this.left, other.left)) {
      return false;
    }
    if (!Objects.equals(this.right, other.right)) {
      return false;
    }
    return true;
  }
}
