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
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import sep.tinee.net.message.Message;
import sep.tinee.net.message.Push;

/**
 * {@code Server} is a minimal but adequately functional and robust Tinee
 * server for you to develop and test your client against.
 * <p>
 * The argument required to run the server corresponds to {@link #Server(int)}:
 * the port number that the server should listen on.
 * <p>
 * You can compile and run this server using <b>NetBeans</b>; e.g., right-click
 * this file in the NetBeans editor and select "Run File".  Note, to provide
 * the above argument, you should set up a <b>run configuration</b> for this
 * class: {@literal ->} "Set Project Configuration" {@literal ->} "Customize..."
 * {@literal ->} "New..." -- the "Main Class" is
 * {@code sep.tinee.server.Server}, and write the port value (e.g.,
 * {@code 8888}) in "Arguments".
 * <p>
 * Assuming compilation using NetBeans (etc.), you can also run {@code Server}
 * from the command line, e.g., on Windows, run:
 * <ul>
 * <li style="list-style-type: none;">
 * {@code C:\...\tinee> java -cp build\classes sep.tinee.server.Server 8888}
 * </ul>
 * <p>
 * <i>Note:</i> On some platforms (e.g., Windows), you may be prompted
 * with a security warning due to the server opening a network socket. You must
 * grant this permission for the server to function properly.
 * <p>
 * An instance of {@code Server} understands and communicates the messages in
 * {@link sep.tinee.net.message}.
 * <ul>
 * <li> {@link sep.tinee.net.message.Bye} - tells the {@code Server} to end
 * this communication session with the client.
 * <li> {@link sep.tinee.net.message.ShowRequest} - request the set of ticket
 * tags and creators (user names) recorded by the {@code Server}.
 * <ul>
 * <li> {@link sep.tinee.net.message.ShowReply} - the {@code Server}'s
 * reply: the set of ticket tags and creators (possibly empty).
 * </ul>
 * <li> {@link sep.tinee.net.message.ReadRequest} - request the (sub)list of
 * tines recorded by the {@code Server} for the specified tag, from the
 * specified index (inclusive, starting from index 0). Note: The tag may not
 * be <i>explicitly</i> recorded by the {@code Server}.
 * <ul>
 * <li> {@link sep.tinee.net.message.ReadReply} - the {@code Server}'s reply:
 * the list of tines (possibly empty).
 * </ul>
 * <li> {@link sep.tinee.net.message.Push} - append a non-empty list of
 * tines to each (possibly new) tag in the non-empty set of ticket tags.
 * </ul>
 * <p>
 * Your client should use {@link sep.tinee.net.channel.ClientChannel} top send
 * and receive messages of the above types with a server: see the
 * {@link sep.tinee.net.channel.ClientChannel#send(Message)} and
 * {@link sep.tinee.net.channel.ClientChannel#receive()} methods.
 *
 * @author rhu1 {@literal <r.z.h.hu@herts.ac.uk>}
 */
public class Server implements AutoCloseable {

  protected final boolean debug;

  private final ServerSocket servSock;

  private final Object mutex = new Object();

  private final ConcurrentLinkedQueue<Thread> threads
      = new ConcurrentLinkedQueue<>();

  /*
   * {@code db} maps a tag to a list of (user, line) pairs.
   * {@code Server} is neutral w.r.t. tag/line data types used by a client -
   * it simply records tags, user names and text as strings.
   * Invariant: Push.CLOSE_LINE only occurs in last pair of any tag.
   * Invariant: a CLOSE_LINE user is the same as the first line user
   */
  protected final LinkedHashMap<String, List<Pair<String, String>>> db
      = new LinkedHashMap<>();

  /**
   * Create a new Tinee server bound to the specified port. This is
   * equivalent to
   * {@linkplain #Server(boolean,int) Server}{@code (false, port)}.
   *
   * @param port The port number that the server will listen on
   * @throws IOException If the server socket could not be opened
   */
  public Server(final int port) throws IOException {
    this(false, port);
  }

  /**
   * Create a new Tinee server, bound to the specified port, with debug
   * output enabled or disabled.
   *
   * @param debug Enable or disable local debug output printing ({@code true}
   *              means enabled)
   * @param port  The port number that the servSock will listen on
   * @throws IOException If the server socket could not be opened
   */
  public Server(final boolean debug, final int port) throws IOException {
    this.debug = debug;
    this.servSock = new ServerSocket(port);
  }

  /**
   * To be called once, and once only, on a {@code Server} instance to start
   * accepting and serving client connections.
   */
  public void run() {
    while (true) {
      try {
        final Socket s = this.servSock.accept();
        Thread t = new ServerThread(this, s);
        this.threads.add(t);  // TODO: currently never cleaned
        t.start();
      } catch (SocketException ex) {
        // Server socket closed -- swallow silently
      } catch (IOException ex) {
        Logger.getLogger(Server.class.getName()).log(Level.WARNING,
            "Connection accept error: ", ex);
      }
    }
  }

  /**
   * Shut down this Tinee server.
   */
  @Override
  public void close() {
    for (Thread t : this.threads) {
      try {
        t.join(1000);
      } catch (InterruptedException ex) {
        throw new RuntimeException(ex);
      }
    }
    try {
      this.servSock.close();
    } catch (IOException ex) {
      Logger.getLogger(Server.class.getName()).log(Level.WARNING,
          "Server socket close error: ", ex);
    }
  }

  /* Tines -- synchronized routines (keep critical sections small) */

  protected void addTines(final Set<String> tags,
      List<Pair<String, String>> tines) {

    synchronized (this.mutex) {
      //Date now = Calendar.getInstance().getTime();
      //String stamp = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(now);  // Inconvenient for testing
      nextTag:
      for (final String tag : tags) {
        List<Pair<String, String>> curr = this.db.get(tag);
        if (curr == null) {
          curr = new LinkedList<>();
          this.db.put(tag, curr);
        }

        boolean isClosed = !curr.isEmpty()
            && curr.get(curr.size() - 1).right.equals(Push.CLOSE_LINE);
        int i = 0;
        for (Pair<String, String> tine : tines) {
          if (isClosed) {
            String msg = "Tag already closed, discarding: "
                + tines.subList(i, tines.size());
            Logger.getLogger(Server.class.getName()).log(Level.WARNING, msg);
            continue nextTag;
          }
          if (tine.right.equals(Push.CLOSE_LINE)) {
            if (!curr.isEmpty() && !tine.left.equals(curr.get(0).left)) {
              String msg = "Discarding invalid close by " + tine.left
                  + ", tag owned by " + curr.get(0).left;
              Logger.getLogger(Server.class.getName()).log(Level.WARNING, msg);
            } else {
              isClosed = true;
              curr.add(tine);
            }
          } else {
            curr.add(tine);
          }
          i++;
        }

      }
    }
  }

  // @return map: tag -> creator  -- keyset ordered following this.db
  protected LinkedHashMap<String, String> getTags() {
    synchronized (this.mutex) {
      return this.db.entrySet().stream().collect(
          Collectors.toMap(Entry::getKey, e -> e.getValue().get(0).left,
              (x, y) -> {
                throw new RuntimeException("Tags not distinct?");
              }, LinkedHashMap::new));
    }
  }

  // Pre: index >= 0 -- guaranteed by ReadRequest
  protected List<Pair<String, String>> getTines(final String tag,
      final int index) {
    synchronized (this.mutex) {
      if (!this.db.containsKey(tag)) {
        return Collections.emptyList();
      }
      final List<Pair<String, String>> tines = this.db.get(tag);
      if (index >= tines.size()) {
        return Collections.emptyList();
      }
      return Collections.unmodifiableList(tines.subList(index, tines.size()));
      // ^Because SubList is not Seriablizable
    }
  }

  /* Main */

  /**
   * Create and run a {@code Server} instance.
   *
   * @param args Use {@code -h} to list the supported options
   * @throws IOException If the servSock could not be created
   */
  public static void main(final String[] args) throws IOException {
    final String help
        = "Usage:  java [-cp ...] sep.tinee.server.Server [-s] port\n"
        + "            --silent -s  Suppress debug messages\n"
        + "            --help   -h  Print this help message";
    if (Arrays.stream(args)
        .anyMatch(x -> "--help".equals(x) || "-h".equals(x))) {
      System.out.println(help);
      System.exit(0);
    } else if (args.length < 1) {
      System.err.println(help);
      System.exit(1);
    }
    final int port = Integer.parseInt(args[args.length - 1]);
    final boolean silent = args.length > 1
        && Stream.of("--silent", "-s").anyMatch(x -> x.equals(args[0]));
    try (final Server s = new Server(!silent, port)) {
      s.run();
    }
  }
}
