package com.reddit.model;

import javassist.NotFoundException;

import java.util.Arrays;

public enum VoteType {
UPVOTE(1),DOWVOTE(2),;
private int direction;
VoteType(int direction){

}
public Integer getDirection(){
    return this.direction;
}
public static VoteType lookup(Integer direction) throws NotFoundException {
    return Arrays.stream(VoteType.values())
            .filter(value->value.getDirection().equals(direction))
            .findAny()
            .orElseThrow(()->new NotFoundException(""));

}
}
