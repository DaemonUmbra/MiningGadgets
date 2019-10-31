package com.direwolf20.mininggadgets.client.screens;

import com.direwolf20.mininggadgets.client.screens.widget.ToggleButton;
import com.direwolf20.mininggadgets.common.gadget.upgrade.Upgrade;
import com.direwolf20.mininggadgets.common.gadget.upgrade.UpgradeTools;
import com.direwolf20.mininggadgets.common.network.PacketHandler;
import com.direwolf20.mininggadgets.common.network.Packets.PacketUpdateUpgrade;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class MiningSettingScreen extends Screen {
    // The upgrades you're allowed to disable and enable
    private static final Collection<String> allowedDisables = Arrays.asList(
            Upgrade.FREEZING.getName(),
            Upgrade.SILK.getName(),
            Upgrade.VOID_JUNK.getName(),
            Upgrade.MAGNET.getName(),
            Upgrade.LIGHT_PLACER.getName()
    );

    private ItemStack gadget;
    private List<Upgrade> gadgetUpgrades = new ArrayList<>();

    public MiningSettingScreen(ItemStack gadget) {
        super(new StringTextComponent("title"));

        this.gadget = gadget;
        this.gadgetUpgrades.addAll(UpgradeTools.getUpgrades(this.gadget));
    }

    @Override
    protected void init() {
        int x = 0;
        for (Upgrade upgrade : this.gadgetUpgrades) {
            addButton(new ToggleButton((width / 2) - 50, ((height / 2) - 80) + (x * 23), 50, upgrade.getName(), (e) -> {
                if( e ) {
                    upgrade.setEnabled(!upgrade.isEnabled());
                    updateUpgrade(upgrade);
                }

                return upgrade.isEnabled();
            }));

            x += 1;
        }
    }

    private void updateUpgrade(Upgrade upgrade) {
        PacketHandler.sendToServer(new PacketUpdateUpgrade(upgrade.getName()));
    }

    @Override
    public void tick() {
//        if(!InputMappings.isKeyDown(getMinecraft().mainWindow.getHandle(), ClientSetup.gadgetMenu.getKey().getKeyCode())) {
//            onClose();
//        }

        super.tick();
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);


    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
