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
package services.moleculer.service;

import static services.moleculer.transporter.Transporter.PACKET_REQUEST;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import io.datatree.Promise;
import io.datatree.Tree;
import services.moleculer.stream.PacketListener;
import services.moleculer.transporter.Transporter;

public class RemoteActionEndpoint extends ActionEndpoint {

	// --- CONSTRUCTOR ---

	public RemoteActionEndpoint(DefaultServiceRegistry registry, Transporter transporter, String nodeID, Tree config) {
		super(nodeID, config);

		// Handle remote timeout with a handler
		current = ctx -> {

			// Create new promise
			Promise promise = new Promise();

			// Set timeout
			long timeoutAt;
			if (ctx.opts != null && ctx.opts.timeout > 0) {
				timeoutAt = System.currentTimeMillis() + ctx.opts.timeout;
			} else {
				timeoutAt = 0;
			}

			// Register promise (timeout and response handling)
			registry.register(ctx.id, promise, timeoutAt);

			// Send request via transporter
			transporter.sendRequestPacket(nodeID, ctx);

			// Streamed content
			if (ctx.stream != null) {
				ctx.stream.onPacket(new PacketListener() {

					// Create sequence counter
					private final AtomicLong sequence = new AtomicLong();

					@Override
					public final void onPacket(byte[] bytes, Throwable cause, boolean close) throws IOException {
						if (bytes != null) {
							transporter.sendDataPacket(PACKET_REQUEST, nodeID, ctx, bytes, sequence.incrementAndGet());
						} else if (cause != null) {
							transporter.sendErrorPacket(PACKET_REQUEST, nodeID, ctx, cause, sequence.incrementAndGet());
						}
						if (close) {
							transporter.sendClosePacket(PACKET_REQUEST, nodeID, ctx, sequence.incrementAndGet());
						}
					}

				});
			}

			// Return promise
			return promise;
		};
	}

}