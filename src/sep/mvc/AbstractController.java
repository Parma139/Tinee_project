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
package sep.mvc;

/**
 * A Controller is used to assemble the
 * {@linkplain sep.mvc.AbstractModel Model} and
 * {@linkplain sep.mvc.AbstractView view}(s), and is responsible for
 * coordinating operations between them to form the application as a whole. The
 * Controller is the central component that processes commands (from the
 * user-driven view) and other events of interest (e.g., network I/O) by
 * performing operations on the Model and view as appropriate.
 * <p>
 * Alternative application behaviours can be realised by different Controllers
 * over the same Model and views. Likewise, a Controller should be able to
 * handle any view that observes a compatible command protocol.
 * <p>
 * The binding of the Model and view to this Controller is fixed on creation.
 *
 * @author rhu1 {@literal <r.z.h.hu@herts.ac.uk>}
 */
public abstract class AbstractController {

  private final AbstractModel model;
  private final AbstractView view;

  /**
   * Binds the supplied Model and View to this Controller. Also implicitly
   * registers this Controller to the supplied View - this Controller will be
   * shutdown if a Controller has already been bound to the View.
   *
   * @param model The Model
   * @param view  The View
   *
   * @see sep.mvc.AbstractView#setController(sep.mvc.AbstractController)
   */
  protected AbstractController(final AbstractModel model,
      final AbstractView view) {
    this.model = model;
    this.view = view;
    view.setController(this);
  }

  /**
   * Shutdown this Controller, and the MVC application as whole. Implicitly
   * closes the bound View.
   */
  public abstract void shutdown();

  /* Getters */

  /**
   * Get the Model bound to this Controller upon creation.
   *
   * @return The bound model
   */
  protected AbstractModel getModel() {
    return this.model;
  }

  /**
   * Get the View bound to this Controller upon creation.
   *
   * @return The bound View
   */
  protected AbstractView getView() {
    return this.view;
  }

}
