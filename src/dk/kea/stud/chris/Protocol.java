package dk.kea.stud.chris;

public class Protocol {
  private ProtocolState state = ProtocolState.HELLO;

  public ProtocolState getState() {
    return this.state;
  }

  public String parse(String input) {
    String reply = null;
    switch (state) {
      case HELLO:
        if (input.equals("HELLO")) {
          this.state = ProtocolState.COMMUNICATING;
          reply = "HELLO";
        } else {
          reply = "You're supposed to start a conversation with \"HELLO\"";
        }
        break;
      case COMMUNICATING:
        if (input.equals("GOODBYE")) {
          this.state = ProtocolState.GOODBYE;
          reply = "GOODBYE";
        } else {
          reply = "MESSAGE RECEIVED: " + input;
        }
        break;
    }
    return reply;
  }
}