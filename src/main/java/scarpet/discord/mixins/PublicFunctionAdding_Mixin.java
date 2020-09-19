
package scarpet.discord.mixins;


import carpet.script.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import scarpet.discord.customLazyFunctionInteface;

import java.util.List;


//Lcarpet/script/Expression;addLazyFunction(Ljava/lang/String;ILcarpet/script/Fluff$TriFunction;)V


@Mixin(Expression.class)
public class PublicFunctionAdding_Mixin implements customLazyFunctionInteface {

	@Shadow
	void addLazyFunction(String name, int num_params, final Fluff.TriFunction<Context, Integer, List<LazyValue>, LazyValue> fun) {};
	@Override
	public void addCustomLazyFunction(String name, int num_params, final Fluff.TriFunction<Context, Integer, List<LazyValue>, LazyValue> fun) {
		addLazyFunction(name, num_params, fun);
	}
}
