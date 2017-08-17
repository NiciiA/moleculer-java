package services.moleculer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;

public class Test {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {

		ServiceBroker broker = new ServiceBroker();
		
		Service svc = broker.createService(new Service(broker, "test", null) {

			// --- CREATED ---

			@Override
			public void created() {

				// Created
				this.logger.debug("Service created!");
				processData();
			}

			// --- ACTIONS ---

			@Cache(true)
			public Action list = (ctx) -> {
				return this.processData();
			};

			@Cache(false)
			public Action add = (ctx) -> {
				
				
				return null;
			};

			// --- METHODS ---

			int processData() {
				this.logger.info("Process data invoked!");
				return 1;
			}

		});
		
		broker.start();

		// ---------

		HashMap<String, Action> map = new HashMap<>();

		Field[] fields = svc.getClass().getFields();
		for (Field field : fields) {
			if (Action.class.isAssignableFrom(field.getType())) {

				// "list"
				String name = field.getName();

				// Action instance
				Action action = (Action) field.get(svc);

				Annotation[] as = field.getAnnotations();
				for (Annotation a : as) {
					boolean cache = ((Cache) a).value();
				}

				map.put(name, action);

			}
		}

		Action action = map.get("list");

		Context ctx = null;
		Object result = action.handler(ctx);

		System.out.println("RESULT: " + result);

		// ------------------

		broker.on("tes?X", (payload) -> {
			
			System.out.println("RECEIVED: " + payload);
			
		});
		
		broker.emit("testX", "Hello EventBus1!");
				
	}

}
