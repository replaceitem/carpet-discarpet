package Discarpet.script;

import carpet.CarpetServer;
import carpet.script.CarpetContext;
import carpet.script.CarpetEventServer;
import carpet.script.CarpetScriptHost;
import carpet.script.Context;
import carpet.script.value.FunctionValue;
import carpet.script.value.Value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScriptFuture extends CarpetEventServer.Callback {
    private final CarpetContext ctx;
    public ScriptFuture(Context context, FunctionValue function) {
        super(context.host.getName(), (String) null, function, null);
        this.ctx = (CarpetContext) context;
    }
    public void execute(List<Value> args) {
        CarpetServer.scriptServer.runScheduledCall(this.ctx.origin, this.ctx.s, this.host, (CarpetScriptHost)this.ctx.host, this.function, args);
    }
    public void execute(Value arg) {
        List<Value> args = Arrays.asList(arg);
        execute(args);
    }
}
