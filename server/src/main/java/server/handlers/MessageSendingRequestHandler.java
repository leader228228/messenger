package server.handlers;

import common.entities.message.Message;
import common.entities.message.MessageStatus;
import server.client.ClientListener;
import server.exceptions.RoomNotFoundException;
import server.processing.RoomProcessing;

import java.io.IOException;

import static common.Utils.buildMessage;

public class MessageSendingRequestHandler extends RequestHandler {

    public MessageSendingRequestHandler() {
    }

    @Override
    public Message handle(ClientListener clientListener, Message message) {
        return sendMessage(clientListener, message);
    }

    /**
     *  The method {@code sendMessage} sends an instance of {@code Message} of the {@code MessageStatus.MESSAGE}
     * to the certain {@code Room}. It is expected, that {@code message} contains (at least) set following parameters :
     * {@code fromId, roomId} and {@code text}
     *
     * @param           message a {@code Message} to be sent
     *
     * @return          an instance of {@code Message} containing information about the operation execution
     *                  it may be of {@code MessageStatus.ERROR} either {@code MessageStatus.ACCEPTED}
     *                  or {@code MessageStatus.DENIED} status
     */
    private Message sendMessage(ClientListener clientListener, Message message) {
        if (clientListener.isMessageNotFromThisLoggedClient(message)) {
            return new Message(MessageStatus.DENIED).setText("Please, log in first");
        }
        if (message.getText().isEmpty()) {
            return new Message(MessageStatus.ERROR).setText("Message text has not been set");
        }
        if (message.getFromId() == null) {
            return new Message(MessageStatus.ERROR).setText("Addresser's id has not been set");
        }
        if (message.getRoomId() == null) {
            return new Message(MessageStatus.ERROR).setText("The room id is not set");
        }
        Message responseMessage;
        try {
            RoomProcessing.sendMessage(clientListener.getServer(), message);
            responseMessage = new Message(MessageStatus.ACCEPTED);
        } catch (IOException e) {
            LOGGER.error(e.getLocalizedMessage());
            responseMessage = new Message(MessageStatus.ERROR).setText("An internal error occurred");
        } catch (RoomNotFoundException e) {
            LOGGER.trace(buildMessage("Room id", message.getRoomId(), "has not been found"));
            responseMessage = new Message(MessageStatus.ERROR)
                    .setText(buildMessage("Unable to find the room (id", message.getRoomId(), ')'));
        }
        return responseMessage;
    }
}