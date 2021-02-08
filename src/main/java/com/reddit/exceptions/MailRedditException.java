package com.reddit.exceptions;

public class MailRedditException extends RuntimeException {
    public MailRedditException (String msgError){
        super(msgError);
    }
}
