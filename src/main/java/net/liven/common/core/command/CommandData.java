package net.liven.common.core.command;


import net.liven.common.core.server.wrappers.WrappedPlayer;
import net.liven.common.core.callables.TriConsumer;
import net.liven.common.core.command.args.ArgType;
import net.liven.common.core.messages.Message;

import java.util.function.BiConsumer;
import java.util.function.Consumer;


public record CommandData(String name,
                          int args,
                          Object consumer,
                          String permission,
                          Message successMessage, ArgType type) {


    public void execute(WrappedPlayer a, Object b, Object c) {
        if (consumer instanceof Runnable) {
            ((Runnable) consumer).run();
        } else if (consumer instanceof Consumer) {
            @SuppressWarnings("unchecked")
            Consumer<WrappedPlayer> consumer = (Consumer<WrappedPlayer>) this.consumer;
            consumer.accept(a);
        } else if (consumer instanceof BiConsumer) {
            @SuppressWarnings("unchecked")
            BiConsumer<WrappedPlayer, Object> biConsumer = (BiConsumer<WrappedPlayer, Object>) consumer;
            biConsumer.accept(a, b);
        } else if (consumer instanceof TriConsumer<?,?,?>) {
            @SuppressWarnings("unchecked")
            TriConsumer<WrappedPlayer, Object, Object> triConsumer = (TriConsumer<WrappedPlayer, Object, Object>) consumer;
            triConsumer.accept(a, b, c);
        }
    }

}
