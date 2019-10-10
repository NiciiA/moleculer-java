/**
 * THIS SOFTWARE IS LICENSED UNDER MIT LICENSE.<br>
 * <br>
 * Copyright 2017 Andras Berkes [andras.berkes@programmer.net]<br>
 * Based on Moleculer Framework for NodeJS [https://moleculer.services].
 * <br><br>
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:<br>
 * <br>
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.<br>
 * <br>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package services.moleculer.eventbus;

import com.lambdaworks.redis.event.DefaultEventBus;

import io.datatree.Tree;
import services.moleculer.context.Context;
import services.moleculer.service.MoleculerComponent;
import services.moleculer.service.Name;
import services.moleculer.service.Service;

/**
 * Base superclass of all Event Bus implementations.
 *
 * @see DefaultEventBus
 */
@Name("Event Bus")
public abstract class Eventbus extends MoleculerComponent {

	// --- RECEIVE EVENT FROM REMOTE SERVICE ---

	public abstract void receiveEvent(Tree message);

	// --- ADD LISTENERS OF A LOCAL SERVICE ---

	public abstract void addListeners(String name, Service service);

	// --- ADD LISTENERS OF A REMOTE SERVICE ---

	public abstract void addListeners(String nodeID, Tree config);

	// --- REMOVE ALL LISTENERS OF A NODE ---

	public abstract void removeListeners(String nodeID);

	// --- SEND EVENT TO ONE LISTENER IN THE SPECIFIED GROUP ---

	public abstract void emit(Context ctx, Groups groups, boolean local);

	// --- SEND EVENT TO ALL LISTENERS IN THE SPECIFIED GROUP ---

	public abstract void broadcast(Context ctx, Groups groups, boolean local);

	// --- GENERATE LISTENER DESCRIPTOR ---

	public abstract Tree generateListenerDescriptor(String service);

}