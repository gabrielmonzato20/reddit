package com.reddit.exceptions;

public class RedditException extends RuntimeException {
    public RedditException(String msgError){
        super(msgError);
    }
}
