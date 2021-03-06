package common.entities.message;

public enum MessageStatus {
    AUTH,
    REGISTRATION,
    MESSAGE,
    CLIENT_BAN,
    CLIENT_UNBAN,
    CREATE_ROOM,
    DELETE_ROOM,
    INVITE_CLIENT,
    UNINVITE_CLIENT,
    ACCEPTED,
    DENIED,
    ERROR,
    STOP_SERVER,
    NEW_MESSAGE,
    NEW_ROOM_MEMBER,
    MEMBER_LEFT_ROOM,
    ROOM_LIST,
    RESTART_SERVER,
    ROOM_MEMBERS,
    MESSAGE_HISTORY,
    GET_CLIENT_NAME
}
