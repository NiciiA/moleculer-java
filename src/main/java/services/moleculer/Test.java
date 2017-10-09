package services.moleculer;

import io.datatree.Tree;
import services.moleculer.cachers.Cache;
import services.moleculer.cachers.MemoryCacher;
import services.moleculer.eventbus.Listener;
import services.moleculer.services.Action;
import services.moleculer.services.Name;
import services.moleculer.services.Service;
import services.moleculer.transporters.RedisTransporter;

public class Test {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {

		ServiceBroker broker = new ServiceBroker("server-2", new RedisTransporter(), new MemoryCacher());

		TestService service = new TestService();

		Service svc = broker.createService(service);

		broker.start();

		// ---------

		Tree t = new Tree().put("a", 5).put("b", 3);
		long start = System.currentTimeMillis();
		for (int i = 0; i < 3; i++) {
			// Object result = broker.call("v2.test.add", t, null);
			// System.out.println("RESULT: " + result);
		}
		System.out.println(System.currentTimeMillis() - start);

		// ------------------

		/*
		broker.on("user.create", (payload) -> {
			System.out.println("RECEIVED in 'user.create': " + payload);
		});
		broker.on("user.created", (payload) -> {
			System.out.println("RECEIVED in 'user.created': " + payload);
		});
		broker.on("user.*", (payload) -> {
			System.out.println("RECEIVED in 'user.*': " + payload);
		});
		broker.on("post.*", (payload) -> {
			System.out.println("RECEIVED in 'post.*': " + payload);
		});
		broker.on("*", (payload) -> {
			System.out.println("RECEIVED in '*': " + payload);
		});
		broker.on("**", (payload) -> {
			System.out.println("RECEIVED in '**': " + payload);
		});
		*/
	}

	@Name("v2.test")
	public static class TestService extends Service {

		// --- CREATED ---

		@Override
		public void created() {

			// Created
			logger.debug("Service created!");

		}

		// --- ACTIONS ---

		public Action list = (ctx) -> {
			return processData(ctx.params().get("a", -1), ctx.params().get("b", -1));
		};

		@Cache({ "a", "b" })
		public Action add = (ctx) -> {
			return ctx.call("v2.test.list", ctx.params(), null);
			// return 2;
		};

		// --- EVENT LISTENERS ---

		// Context, Tree, or Object????
		public Listener test = (input) -> {

		};

		// --- METHODS ---

		int processData(int a, int b) {
			// this.logger.info("Process data invoked: " + a + ", " + b);
			return a + b;
		}

	}

}
