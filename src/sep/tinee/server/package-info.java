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
/**
 * The server-side classes for you to stand up a minimal but functional Tinee
 * server and run your client against.
 * <p>
 * A <b>tine</b> is simply a pair of strings: the name (a single, non-empty
 * alphanumeric word of maximum length 8) of the <b>user</b> who wrote this
 * tine, and its message <b>line</b> (a single, non-empty line of maximum length
 * 48 characters). A Tinee server records an ordered list of tines per
 * <b>tag</b> (again, a non-empty alphanumeric word of maximum length 8), and
 * allows clients to push and read tines for the client-specified tag.
 * <p>
 * An instance of {@link sep.tinee.server.Server} understands and communicates
 * messages as defined in {@link sep.tinee.net.message}.
 */
package sep.tinee.server;
