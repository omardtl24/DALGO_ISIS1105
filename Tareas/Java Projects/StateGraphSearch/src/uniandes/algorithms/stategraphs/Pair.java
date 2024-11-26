package uniandes.algorithms.stategraphs;

import java.util.Objects;

public class Pair {
    public final String element1;
    public final String element2;

    public Pair(String p1, String p2) {
        element1 = p1;
        element2 = p2;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Pair pair = (Pair) obj;
        return (Objects.equals(element1, pair.element1) && Objects.equals(element2, pair.element2)) ||
               (Objects.equals(element1, pair.element2) && Objects.equals(element2, pair.element1));
    }

    @Override
    public int hashCode() {
        return Objects.hash(element1, element2) + Objects.hash(element2, element1);
    }
}
