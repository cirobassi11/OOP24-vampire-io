package it.unibo.vampireio.view;

import java.util.List;
import it.unibo.vampireio.controller.PositionableDTO;

public interface GameView {
    void update(List<PositionableDTO> positionables);
}
