package net.liven.coreAPI.core.command;



import net.liven.coreAPI.core.callables.TriConsumer;
import net.liven.coreAPI.core.messages.Message;

import java.util.function.BiConsumer;
import java.util.function.Consumer;


public record CommandData<A, B, C>(String name,
                                   int args,
                                   Object consumer,
                                   String permission,
                                   Message successMessage) {


    public void execute(A a, B b, C c) {
        if (consumer instanceof Runnable) {
            ((Runnable) consumer).run();
        } else if (consumer instanceof Consumer) {
            @SuppressWarnings("unchecked")
            Consumer<A> consumer = (Consumer<A>) this.consumer;
            consumer.accept(a);
        } else if (consumer instanceof BiConsumer) {
            @SuppressWarnings("unchecked")
            BiConsumer<A, B> biConsumer = (BiConsumer<A, B>) consumer;
            biConsumer.accept(a, b);
        } else if (consumer instanceof TriConsumer<?,?,?>) {
            @SuppressWarnings("unchecked")
            TriConsumer<A, B, C> triConsumer = (TriConsumer<A, B, C>) consumer;
            triConsumer.accept(a, b, c);
        }
    }

}
