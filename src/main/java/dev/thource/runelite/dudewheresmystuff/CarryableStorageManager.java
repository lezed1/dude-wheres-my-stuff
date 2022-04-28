package dev.thource.runelite.dudewheresmystuff;

import com.google.inject.Inject;
import dev.thource.runelite.dudewheresmystuff.carryable.LootingBag;
import dev.thource.runelite.dudewheresmystuff.carryable.RunePouch;
import dev.thource.runelite.dudewheresmystuff.carryable.SeedBox;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.client.Notifier;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;

@Slf4j
public class CarryableStorageManager extends
    StorageManager<CarryableStorageType, CarryableStorage> {

  @Inject
  private CarryableStorageManager(Client client, ItemManager itemManager,
      ConfigManager configManager, DudeWheresMyStuffConfig config, Notifier notifier,
      DudeWheresMyStuffPlugin plugin) {
    super(client, itemManager, configManager, config, notifier, plugin);

    for (CarryableStorageType type : CarryableStorageType.values()) {
      if (type == CarryableStorageType.RUNE_POUCH || type == CarryableStorageType.LOOTING_BAG
          || type == CarryableStorageType.SEED_BOX) {
        continue;
      }

      storages.add(new CarryableStorage(type, client, itemManager));
    }

    storages.add(new RunePouch(client, itemManager));
    storages.add(new LootingBag(client, itemManager));
    storages.add(new SeedBox(client, itemManager));
  }

  @Override
  public String getConfigKey() {
    return "carryable";
  }

  @Override
  public Tab getTab() {
    return Tab.CARRYABLE_STORAGE;
  }
}
