class OpenALException extends RuntimeException {
    OpenALException(int errorCode) {
        super("errorCode:" + errorCode);
    }
}
