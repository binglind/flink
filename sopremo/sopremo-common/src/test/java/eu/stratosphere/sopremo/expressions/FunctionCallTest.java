package eu.stratosphere.sopremo.expressions;
import static eu.stratosphere.sopremo.JsonUtil.*;

import junit.framework.Assert;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.NumericNode;
import org.junit.Before;
import org.junit.Test;

import eu.stratosphere.sopremo.EvaluationContext;
import eu.stratosphere.sopremo.JsonUtil;
import eu.stratosphere.sopremo.function.FunctionRegistry;

public class FunctionCallTest extends EvaluableExpressionTest<FunctionCall> {

	private FunctionRegistry registry;

	@Override
	protected FunctionCall createDefaultInstance(final int index) {
		return new FunctionCall(String.valueOf(index));
	}

	@Before
	public void setup() {
		this.context = new EvaluationContext();
		this.registry = this.context.getFunctionRegistry();
		this.registry.register(this.getClass());
	}

	@Test
	public void shouldCallFunction() {
		JsonNode result = new FunctionCall("sum", new ArrayAccess(0), new ArrayAccess(1)).evaluate(
			createArrayNode(1, 2), this.context);
		Assert.assertEquals(JsonUtil.NODE_FACTORY.numberNode(3), result);
	}
	
	@Test
	public void shouldGetIteratorOverAllParams(){
		
		FunctionCall func = new FunctionCall("sum");
		func.iterator();
		
	}

	public static JsonNode sum(final NumericNode... nodes) {

		int i = 0;
		for (final NumericNode node : nodes) {
			i += node.getValueAsInt();
		}
		return (JsonUtil.NODE_FACTORY.numberNode(i));

	}
}
