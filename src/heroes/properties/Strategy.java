package heroes.properties;

import heroes.Hero;

public interface Strategy {
    int calculateDamageAgainstEnemy(Hero enemy);
}
