package net.liven.coreapi.placeholder;

import lombok.Getter;

public record Placeholder(String holder, @Getter String value) {

    @Override
    public String holder() {
        return "<" + holder + ">";
    }
}
