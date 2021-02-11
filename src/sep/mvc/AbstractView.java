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

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A View provides the front-end user interface, which includes appropriate
 * presentation of the relevant model data to the user. The lifetime of an MVC
 * application is often tied to its front-end UI.
 * <p>
 * The View accepts user input, and conveys the corresponding commands to the
 * registered {@linkplain sep.mvc.AbstractController Controller} for
 * processing. The View interacts closely with the Controller, but should not
 * be coupled to any particular Controller (i.e., a View should be
 * interoperable with different Controllers).
 * <p>
 * A View may itself determine when it should be updated, and retrieve the
 * required information directly from the
 * {@linkplain sep.mvc.AbstractModel Model}, or operations performed on the
 * Model (as directed by the Controller) may actively push updates to the View,
 * or some combination thereof. The design can depend on whether the UI
 * supports synchronous or asynchronous user interactions.
 *
 * @author rhu1 {@literal <r.z.h.hu@herts.ac.uk>}
 */
public abstract class AbstractView {

  private AbstractController control;

  /**
   * This View is created without a registered Controller. A Controller is
   * registered by passing this View when the Controller is created.
   *
   * @see #setController(sep.mvc.AbstractController)
   */
  protected AbstractView() {
    this.control = null;
  }

  /**
   * Initialise this View. To be called once, and only once, before
   * {@link #run()}.  It checks that this View has been registered with a
   * {@linkplain sep.mvc.AbstractController Controller} (by supplying it as an
   * argument to the Controller constructor call).
   * <p>
   * May be called by the top-level application bootstrapper (e.g., the
   * {@code main} method).
   *
   * @throws IllegalStateException If no Controller has been registered.
   */
  public void init() {
    if (this.control == null) {
      throw new IllegalStateException("[MVC error] "
          + "This View has not been passed to a Controller.");
    }
  }

  /**
   * Close this View. This operation is idempotent.
   */
  public abstract void close();

  /* UI */

  /**
   * Start the overall MVC application after it has been assembled -- starts
   * the main UI loop. Maintains an up-to-date presentation of the front-end
   * user interface (e.g., via {@link #update()}), and interprets user
   * input as commands for the Controller to process.
   * <p>
   * Should be called by the top-level application bootstrapper (e.g.,
   * the {@code main} method) <i>after</i> {@code init} has been called.
   * <p>
   * Precondition: {@link #init()} has been called.
   *
   * @throws IOException If a user input error occurs
   */
  public abstract void run() throws IOException;

  /**
   * Update the presentation of the front-end UI. May be called by
   * {@link #run()}, or from the model/Controller, as appropriate for the
   * application and UI design.
   */
  public abstract void update();

  /* Getters */

  /**
   * Get the Controller registered to this View, if any.
   *
   * @return The registered Controller, if any
   * @throws IllegalStateException If no Controller has been registered
   */
  protected AbstractController getController() {
    if (this.control == null) {
      throw new IllegalStateException("[MVC error] "
          + "This View has not been bound to a Controller.");
    }
    return this.control;
  }

  /**
   * Get the model bound to the registered Controller, if a Controller has been
   * registered.
   *
   * @return The bound model, if a Controller has been registered
   * @throws IllegalStateException If no Controller has been registered
   */
  protected AbstractModel getModel() {
    return getController().getModel();  // Checks if a Controller registered
  }

  /* Auxiliary */

  /**
   * Registers the Controller if one has not been registered so far; otherwise
   * the supplied Controller is shut down. Implicitly called by the
   * {@link sep.mvc.AbstractController#AbstractController(AbstractModel, AbstractView)}
   * constructor. Normally, you should not need to override this method.
   *
   * @param control The Controller to register
   * @throws IllegalArgumentException If registering a null Controller
   */
  protected void setController(final AbstractController control) {
    if (control == null) {
      throw new IllegalArgumentException(
          "[MVC error] Registering a null Controller.");
    }
    if (this.control != null) {
      Logger.getLogger(AbstractView.class.getName()).log(Level.SEVERE,
          "[MVC error] Controller already set.  Shutting down.");
      control.shutdown();
    }
    this.control = control;
  }
}
