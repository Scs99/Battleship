package battleship;

/**
 *
 * @author Louis Rast
 */
public class StatusMessage {
    
    public final String message;
    public final StatusMessageType type;

    public StatusMessage(final String message, final StatusMessageType type) {
        this.message = message;
        this.type = type;
    }   
}
