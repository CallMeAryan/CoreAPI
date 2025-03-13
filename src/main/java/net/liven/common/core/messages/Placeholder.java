package net.liven.common.core.messages;

import lombok.Getter;

public record Placeholder(String holder, @Getter String value) {

    @Override
    public String holder() {
        return "<" + holder + ">";
    }
}
