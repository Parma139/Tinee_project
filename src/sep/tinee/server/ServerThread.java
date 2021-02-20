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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import sep.tinee.net.message.Bye;
import sep.tinee.net.message.Message;
import sep.tinee.net.message.ShowReply;
import sep.tinee.net.message.ReadRequest;
import sep.tinee.net.message.ReadReply;
import sep.tinee.net.message.Push;
import sep.tinee.net.message.ShowRequest;

/**
 * To be spawned by {@link sep.tinee.server.Server} to serve one Tinee
 * client (concurrently with any other clients in other server threads).
 *
 * @author rhu1 {@literal <r.z.h.hu@herts.ac.uk>}
 */
public class ServerThread extends Thread {

  private final Server server;
  private final Socket sock;
  private final ObjectInputStream in;
  private final ObjectOutputStream out;

  /**
   * Create a thread object for serving the connected Tinee client.
   *
   * @param server The {@link sep.tinee.server.Server} that is spawning this
   *               thread
   * @param sock   The server-side {@link java.net.Socket} of the channel
   *               established with the client
   * @throws IOException If I/O streams cannot be established with the client
   *                     over the socket.
   */
  public ServerThread(final Server server, final Socket sock) throws
      IOException {
    this.server = server;
    this.sock = sock;
    this.in = new ObjectInputStream(sock.getInputStream());
    this.out = new ObjectOutputStream(sock.getOutputStream());
  }

  @Override
  public void run() {
    debugPrintln("Accepted connection.");
    try {
      while (true) {
        Object read = this.in.readObject();
        debugPrintln("Received: " + read);
        if (!(read instanceof Message)) {  // Could replace by a separate header communication
          throw new IOException(getFormattedClientAddr()
              + "Client error, invalid message: " + read.getClass());
        }
        if (handleMessage((Message) read)) {
          return;
        }
      }
    } catch (IOException | ClassNotFoundException ex) {
      final String msg = getFormattedClientAddr() + " I/O error: "
          + ex.getMessage() + " (" + ex.getClass().getSimpleName() + ")";
      Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, msg, ex);
    } finally {
      close();
      debugPrintln("Session ended.");
    }
  }

  // @return  is terminal
  private boolean handleMessage(final Message msg) throws IOException {
    switch (msg.getHeader()) {
      case Bye.HEADER:
        return true;  // close done by calling method (run) finally
      case ShowRequest.HEADER: {
        final Message rep = new ShowReply(this.server.getTags());  // Non-empty
        this.out.reset();  // O/w seems to cache the List<Pair<String, String>> instance reused throughout
        this.out.writeObject(rep);
        this.out.flush();
        debugPrintln("Sent: " + rep);
        break;
      }
      case ReadRequest.HEADER: {
        final ReadRequest req = (ReadRequest) msg;  // pub.index >= 0
        final List<Pair<String, String>> tines
            = this.server.getTines(req.tag, req.index);
        final Pair<Stream<String>, Stream<String>> reduce
            = tines.stream().map(x -> new Pair<>(
            Stream.of(x.left), Stream.of(x.right)))
                .reduce(new Pair<>(Stream.empty(), Stream.empty()),
                    (z, y) -> new Pair<>(
                        Stream.concat(z.left, y.left),
                        Stream.concat(z.right, y.right)));
        final Pair<List<String>, List<String>> pair
            = new Pair<>(reduce.left.collect(Collectors.toList()),
                reduce.right.collect(Collectors.toList()));
        final Message rep = new ReadReply(pair.left, pair.right);
        this.out.reset();  // O/w seems to cache the List<Pair<String, String>> instance reused throughout
        this.out.writeObject(rep);
        this.out.flush();
        debugPrintln("Sent: " + rep);
        break;
      }
      case Push.HEADER: {
        final Push pub = (Push) msg;
        Message.isValidUserId(pub.user);  // Only needed if Message further subclassed
        pub.tags.forEach(Message::isValidTag);
        pub.lines.stream().forEach(Message::isValidLine);
        final List<Pair<String, String>> tines
            = pub.lines.stream().map(x -> new Pair<>(pub.user, x))
                .collect(Collectors.toList());
        this.server.addTines(pub.tags, tines);
        break;
      }
      default:
        throw new IOException(getFormattedClientAddr()
            + "Client error, unexpected message: " + msg.getClass());
    }
    return false;
  }

  private void close() {
    debugPrintln("Closing connection.");
    IOException err = null;
    try {
      this.in.close();
    } catch (IOException ex) {
      err = ex;  // msg currently null
    }
    try {
      this.out.close();
    } catch (IOException ex) {
      if (err == null) {
        err = ex;
      }
    }
    try {
      this.sock.close();
    } catch (IOException ex) {
      if (err == null) {
        err = ex;
      }
    }
    if (err != null) {
      final String msg = "Connection close error: " + err.getMessage();
      Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, msg);
    }
  }

  private void debugPrintln(final String msg) {
    if (this.server.debug) {
      System.out.println(getFormattedClientAddr() + " " + msg);
    }
  }

  private String getFormattedClientAddr() {
    return "(Client " + sock.getRemoteSocketAddress() + ")";
  }

}
