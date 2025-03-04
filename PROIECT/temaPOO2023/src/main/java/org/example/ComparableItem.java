package org.example;

public interface ComparableItem extends Comparable<ComparableItem> {
    @Override
    default int compareTo(ComparableItem other) {
        return toString().compareTo(other.toString());
    }
}