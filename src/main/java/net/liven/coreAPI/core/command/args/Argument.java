package net.liven.coreAPI.core.command.args;

import lombok.Getter;
import net.liven.coreAPI.core.callables.ListSupplier;
import net.liven.coreAPI.core.messages.Message;



public record Argument(String name, Class<?> type, boolean optional, Message invalidValue3, ListSupplier tabSupplier) {

}
