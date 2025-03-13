package net.liven.common.core.callables;

import java.util.List;

@FunctionalInterface
public interface ListSupplier {
    List<String> run();
}
