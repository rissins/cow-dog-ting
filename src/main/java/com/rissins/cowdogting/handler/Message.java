package com.rissins.cowdogting.handler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    private String type;
    private String sender;
    private String receiver;
    private Object data;

    public void newConnect() {
        this.type = "new";
    }
    public void closeConnect() {
        this.type = "close";
    }
}
