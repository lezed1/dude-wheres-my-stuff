package dev.thource.runelite.dudewheresmystuff;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import net.runelite.api.Client;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.LineComponent.LineComponentBuilder;

public class PovertyPVMOverlay extends OverlayPanel {

  private final DudeWheresMyStuffConfig dudeWheresMyStuffConfig;
  private final StorageManagerManager storageManagerManager;
  private final Client client;

  public PovertyPVMOverlay(DudeWheresMyStuffPlugin dudeWheresMyStuffPlugin,
      DudeWheresMyStuffConfig dudeWheresMyStuffConfig, StorageManagerManager storageManagerManager,
      Client client) {
    super(dudeWheresMyStuffPlugin);
    setPosition(OverlayPosition.ABOVE_CHATBOX_RIGHT);
    this.dudeWheresMyStuffConfig = dudeWheresMyStuffConfig;
    this.storageManagerManager = storageManagerManager;
    this.client = client;
  }

  @Override
  public Dimension render(Graphics2D graphics) {
    long allowed = (long) client.getTotalLevel() * dudeWheresMyStuffConfig.caLevelModifier();
//    panelComponent.getChildren().add(
//        TitleComponent.builder().text("Allowed: " + allowed).color(Color.GREEN).build());
    panelComponent.getChildren().add(
        LineComponent.builder().left("Allowed:").right(String.valueOf(allowed)).build());

    long total_value = storageManagerManager.getItems().stream()
        .mapToLong(ItemStack::getTotalGePrice).sum();
    panelComponent.getChildren().add(
        LineComponent.builder().left("Current value:").right(String.valueOf(total_value)).build());

    long remaining_gp = allowed - total_value;
    LineComponentBuilder remainingGPLine = LineComponent.builder().left("Remaining GP allowed:")
        .right(String.valueOf(remaining_gp));
    if (remaining_gp == 0) {
      remainingGPLine.rightColor(Color.YELLOW);
    } else if (remaining_gp < 0) {
      remainingGPLine.rightColor(Color.RED);
    }
    panelComponent.getChildren().add(remainingGPLine.build());

    return super.render(graphics);
  }
}
