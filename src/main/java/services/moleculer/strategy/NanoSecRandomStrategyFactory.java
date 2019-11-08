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
package services.moleculer.strategy;

import services.moleculer.service.Endpoint;
import services.moleculer.service.Name;

/**
 * Factory of nanosec-based pseudorandom invocation strategy.
 *
 * @see RoundRobinStrategyFactory
 * @see SecureRandomStrategyFactory
 * @see XorShiftRandomStrategyFactory
 * @see CpuUsageStrategyFactory
 * @see NetworkLatencyStrategyFactory
 * @see ShardStrategyFactory
 */
@Name("Nanosecond-based Pseudorandom Strategy Factory")
public class NanoSecRandomStrategyFactory extends ArrayBasedStrategyFactory {

	// --- CONSTRUCTORS ---

	/**
	 * Constructor that uses local services when available.
	 */
	public NanoSecRandomStrategyFactory() {
		super();
	}

	/**
	 * Constructor that can be configured to use local services if possible.
	 * 
	 * @param preferLocal
	 *            invoke local actions if possible
	 */
	public NanoSecRandomStrategyFactory(boolean preferLocal) {
		super(preferLocal);
	}

	// --- FACTORY METHOD ---

	@Override
	public <T extends Endpoint> Strategy<T> create() {
		return new NanoSecRandomStrategy<T>(broker, preferLocal);
	}

}