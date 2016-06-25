package portablejim.ud.capabilities;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.items.IItemHandler;

/**
 * Created by james on 25/06/16.
 */
public class UsedCapabilities {
    @CapabilityInject(IItemHandler.class)
    public static Capability<IItemHandler> ITEM_HANDLER_CAPABILITY = null;
}
