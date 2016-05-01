package org.hope6537.exception;

/**
 * Created by hope6537 on 16/1/30.
 */
public class ComicBaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public ComicBaseException() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public ComicBaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     * @param cause
     */
    public ComicBaseException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     */
    public ComicBaseException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param cause
     */
    public ComicBaseException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

}

