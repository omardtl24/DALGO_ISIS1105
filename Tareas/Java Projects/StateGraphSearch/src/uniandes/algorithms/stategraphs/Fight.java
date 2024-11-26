package uniandes.algorithms.stategraphs;

import java.util.Objects;

public class Fight {
    private final String person1;
    private final String person2;

    public Fight(String p1, String p2) {
        person1 = p1;
        person2 = p2;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Fight fight = (Fight) obj;
        return (Objects.equals(person1, fight.person1) && Objects.equals(person2, fight.person2)) ||
               (Objects.equals(person1, fight.person2) && Objects.equals(person2, fight.person1));
    }

    @Override
    public int hashCode() {
        return Objects.hash(person1, person2) + Objects.hash(person2, person1);
    }
}
