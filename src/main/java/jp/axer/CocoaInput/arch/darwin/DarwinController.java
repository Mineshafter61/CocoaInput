package jp.axer.cocoainput.arch.darwin;

import jp.axer.cocoainput.CocoaInput;
import jp.axer.cocoainput.plugin.CocoaInputController;
import jp.axer.cocoainput.plugin.IMEOperator;
import jp.axer.cocoainput.plugin.IMEReceiver;
import jp.axer.cocoainput.util.ModLogger;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class DarwinController implements CocoaInputController {
    public DarwinController() throws Exception {
        CocoaInput.copyLibrary("libcocoainput.dylib", "darwin/libcocoainput.dylib");
        MinecraftForge.EVENT_BUS.register(this);
        Handle.INSTANCE.initialize(CallbackFunction.Func_log, CallbackFunction.Func_error, CallbackFunction.Func_debug);
        ModLogger.log("DarwinController has been initialized.");
    }

    @Override
    public IMEOperator generateIMEOperator(IMEReceiver ime) {
        return new DarwinIMEOperator(ime);
    }

    @SubscribeEvent
    public void didChangeGui(GuiOpenEvent event) {
        if (!(event.getGui() instanceof IMEReceiver)) {
            Handle.INSTANCE.refreshInstance();//GUIの切り替えでIMの使用をoffにする
        }
    }
}
