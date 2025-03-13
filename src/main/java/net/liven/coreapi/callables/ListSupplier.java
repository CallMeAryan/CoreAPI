package net.liven.coreapi.callables;

import java.util.List;

@FunctionalInterface
public interface ListSupplier {
    List<String> run();
}
