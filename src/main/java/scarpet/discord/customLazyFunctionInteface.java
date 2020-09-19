package scarpet.discord;

import carpet.script.Context;
import carpet.script.Fluff;
import carpet.script.LazyValue;

import java.util.List;

public interface customLazyFunctionInteface {
	public abstract void addCustomLazyFunction(String name, int num_params, final Fluff.TriFunction<Context, Integer, List<LazyValue>, LazyValue> fun);
}